package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.graphics.Color
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.OnBackPressedCallback
import com.aariz.sportsapp.databinding.FragmentFieldingPositionsBinding
import android.webkit.JavascriptInterface
import android.widget.Toast

class FieldingPositionsFragment : Fragment() {

    private var _binding: FragmentFieldingPositionsBinding? = null
    private val binding get() = _binding!!
    private val backStackListener = FragmentManager.OnBackStackChangedListener {
        if (isResumed && isVisible) {
            setTitle()
        }
    }

    private val positionToPlayerId: Map<String, String> = emptyMap()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFieldingPositionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Keep title consistent when this fragment becomes visible again via back stack changes
        parentFragmentManager.addOnBackStackChangedListener(backStackListener)
        // Intercept device back press to set the title just before pop completes
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                setTitle()
                // Disable this callback and pass the event to the default handler
                isEnabled = false
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        })
        // Configure WebView
        binding.webFielding.apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.cacheMode = WebSettings.LOAD_DEFAULT
            settings.setSupportZoom(false)
            isVerticalScrollBarEnabled = false
            isHorizontalScrollBarEnabled = false
            setBackgroundColor(Color.TRANSPARENT)
            // Performance tweaks
            setLayerType(View.LAYER_TYPE_HARDWARE, null)
            overScrollMode = View.OVER_SCROLL_NEVER
            try { settings.setOffscreenPreRaster(true) } catch (_: Throwable) {}
            // Bridge for JS -> Android communication
            addJavascriptInterface(JSBridge(), "AndroidBridge")
            webChromeClient = object : WebChromeClient() {
                override fun onConsoleMessage(message: android.webkit.ConsoleMessage): Boolean {
                    Log.d("FieldingWebView", "${message.message()} @${message.lineNumber()} (${message.sourceId()})")
                    return super.onConsoleMessage(message)
                }
                // Prevent WebView page title from changing the app's toolbar title
                override fun onReceivedTitle(view: WebView?, title: String?) {
                    // Intentionally ignore WebView titles; keep toolbar managed by activity/fragment
                }
            }
            webViewClient = object : WebViewClient() {
                override fun onReceivedError(
                    view: WebView,
                    request: WebResourceRequest,
                    error: WebResourceError
                ) {
                    Log.e("FieldingWebView", "Error loading asset: ${error.description}")
                    val fallback = """
                        <html><body style='background:#111;color:#fff;font-family:sans-serif;display:flex;align-items:center;justify-content:center;height:100%;'>
                        <div>Failed to load field. Please try again.</div>
                        </body></html>
                    """.trimIndent()
                    loadDataWithBaseURL(null, fallback, "text/html", "UTF-8", null)
                }
            }
            loadUrl("file:///android_asset/fielding_positions.html")
        }
        // Set title now as well
        setTitle()
    }

    override fun onResume() {
        super.onResume()
        // Ensure toolbar title is always 'Fielding Positions' when this fragment is visible
        setTitle()
        // Post to UI queue as a safeguard if Activity updates title after back press
        view?.post { setTitle() }
    }

    override fun onStart() {
        super.onStart()
        setTitle()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) setTitle()
    }

    private fun setTitle() {
        activity?.title = "Fielding Positions"
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Fielding Positions"
    }

    private inner class JSBridge {
        @JavascriptInterface
        fun onFielderDoubleTap(positionName: String) {
            Log.d("FieldingWebView", "Double-tapped position: $positionName")
            // Resolve playerId for this position if mapping exists
            val playerId = positionToPlayerId[positionName]
            activity?.runOnUiThread {
                // Build the new fielder detail fragment and pass args
                val fragment = FielderDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(FielderDetailFragment.ARG_POSITION_NAME, positionName)
                        if (!playerId.isNullOrEmpty()) {
                            putString(FielderDetailFragment.ARG_PLAYER_ID, playerId)
                        }
                    }
                }

                // Prefer navigating via MainActivity to keep header/back behavior consistent
                val handledByActivity = (activity as? MainActivity)?.let { main ->
                    main.navigateToFragment(
                        fragment,
                        title = "Fielder: $positionName",
                        addToBackStack = true
                    )
                    true
                } ?: false

                if (!handledByActivity) {
                    // Fallback to direct fragment transaction
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        try { parentFragmentManager.removeOnBackStackChangedListener(backStackListener) } catch (_: Throwable) {}
        // Clean up WebView to avoid memory leaks
        try {
            binding.webFielding.apply {
                loadUrl("about:blank")
                clearHistory()
                removeAllViews()
                destroy()
            }
        } catch (ignored: Throwable) { }
        _binding = null
    }
}
