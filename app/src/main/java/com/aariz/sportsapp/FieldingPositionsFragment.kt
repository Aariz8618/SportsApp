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
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import androidx.appcompat.app.AppCompatActivity
import com.aariz.sportsapp.databinding.FragmentFieldingPositionsBinding
import android.webkit.JavascriptInterface
import android.widget.Toast

class FieldingPositionsFragment : Fragment() {

    private var _binding: FragmentFieldingPositionsBinding? = null
    private val binding get() = _binding!!

    private val backStackListener = FragmentManager.OnBackStackChangedListener {
        // Only set title if this fragment is actually visible and resumed
        if (isResumed && isVisible && !isDetached) {
            view?.post { setTitle() }
        }
    }

    // Optional: map field position name -> playerId if available
    private val positionToPlayerId: Map<String, String> = emptyMap()

    // Mode states
    private var compareMode: Boolean = false
    private var singlePositionMode: Boolean = false
    private var singlePositionName: String? = null
    private val selectedPositions = linkedSetOf<String>() // preserves selection order

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

        // Handle mode arguments
        compareMode = arguments?.getBoolean(ARG_COMPARE_MODE, false) == true
        singlePositionMode = arguments?.getBoolean(ARG_SINGLE_POSITION_MODE, false) == true
        singlePositionName = arguments?.getString(ARG_SINGLE_POSITION_NAME)

        if (compareMode) {
            val preselected = arguments?.getStringArrayList(ARG_PRESELECTED_POSITIONS) ?: arrayListOf()
            selectedPositions.clear()
            selectedPositions.addAll(preselected)
            val startMsg = if (preselected.isNotEmpty()) {
                "Comparing positions. Selected: ${preselected.joinToString()} (pick up to 3)."
            } else {
                "Compare mode: Tap any positions to select (max 3)."
            }
            Toast.makeText(requireContext(), startMsg, Toast.LENGTH_LONG).show()
        } else if (singlePositionMode) {
            Toast.makeText(requireContext(), "Showing position: $singlePositionName", Toast.LENGTH_SHORT).show()
        }

        // Configure WebView
        binding.webFielding.apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.cacheMode = WebSettings.LOAD_DEFAULT
            settings.setSupportZoom(false)
            isVerticalScrollBarEnabled = false
            isHorizontalScrollBarEnabled = false
            setBackgroundColor(Color.TRANSPARENT)
            setLayerType(View.LAYER_TYPE_HARDWARE, null)
            overScrollMode = View.OVER_SCROLL_NEVER
            try { settings.setOffscreenPreRaster(true) } catch (_: Throwable) {}

            addJavascriptInterface(JSBridge(), "AndroidBridge")
            webChromeClient = object : WebChromeClient() {
                override fun onConsoleMessage(message: android.webkit.ConsoleMessage): Boolean {
                    Log.d("FieldingWebView", "${message.message()} @${message.lineNumber()} (${message.sourceId()})")
                    return super.onConsoleMessage(message)
                }
                override fun onReceivedTitle(view: WebView?, title: String?) {
                    // Intentionally ignore WebView titles
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
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)

                    when {
                        singlePositionMode && !singlePositionName.isNullOrEmpty() -> {
                            // Hide all positions except the selected one
                            val js = buildString {
                                append("(function(){\n")
                                val safeName = singlePositionName!!.replace("'", "\\'")
                                append("  // Hide all positions first\n")
                                append("  var allPositions = document.querySelectorAll('.position');\n")
                                append("  allPositions.forEach(function(el){ el.style.display = 'none'; });\n")
                                append("  // Show only the selected position\n")
                                append("  var targetEls = Array.from(allPositions).filter(e => e.dataset.name === '$safeName');\n")
                                append("  targetEls.forEach(function(el){ \n")
                                append("    el.style.display = 'block';\n")
                                append("    el.classList.add('highlighted');\n")
                                append("    el.classList.add('name-visible');\n")
                                append("  });\n")
                                append("})();")
                            }
                            try { view?.evaluateJavascript(js, null) } catch (_: Throwable) {}
                        }
                        compareMode && selectedPositions.isNotEmpty() -> {
                            // Compare mode logic (existing)
                            val js = buildString {
                                append("(function(){\n")
                                selectedPositions.forEach { name ->
                                    val safe = name.replace("'", "\\'")
                                    append("  var els = Array.from(document.querySelectorAll('.position')).filter(e => e.dataset.name === '$safe');\n")
                                    append("  els.forEach(function(el){ el.classList.add('selected'); });\n")
                                }
                                val first = selectedPositions.firstOrNull()?.replace("'", "\\'")
                                if (first != null) {
                                    append("  var firstEls = Array.from(document.querySelectorAll('.position')).filter(e => e.dataset.name === '" + first + "');\n")
                                    append("  firstEls.forEach(function(el){ el.classList.add('name-visible'); });\n")
                                }
                                append("})();")
                            }
                            try { view?.evaluateJavascript(js, null) } catch (_: Throwable) {}
                        }
                    }
                    updateCompareButtonState()
                    // Set title after page loads - direct call, no delay
                    setTitle()
                }
            }
            loadUrl("file:///android_asset/fielding_positions.html")
        }

        setupCompareButton()
        setTitle()

        // Ensure system back pops to previous fragment from this screen
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                parentFragmentManager.popBackStack()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        setTitle()
        view?.post { setTitle() }
        view?.postDelayed({ setTitle() }, 200)
    }

    override fun onStart() {
        super.onStart()
        setTitle()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            setTitle()
            view?.postDelayed({ setTitle() }, 100)
        }
    }

    private fun setTitle() {
        try {
            val title = when {
                singlePositionMode && !singlePositionName.isNullOrEmpty() -> "Field View: $singlePositionName"
                compareMode -> "Compare Positions"
                else -> "Fielding Positions"
            }
            activity?.title = title
            (activity as? AppCompatActivity)?.supportActionBar?.title = title
        } catch (e: Exception) {
            Log.w("FieldingPositions", "Could not set title", e)
        }
    }

    private inner class JSBridge {
        @JavascriptInterface
        fun onFielderDoubleTap(positionName: String) {
            if (singlePositionMode) {
                // In single position mode, double-tap should go back to detail of the same position
                Log.d("FieldingWebView", "Double-tapped position in single mode: $positionName")
                activity?.runOnUiThread {
                    // Simply pop the back stack to return to the previous fragment
                    parentFragmentManager.popBackStack()
                }
                return
            }

            Log.d("FieldingWebView", "Double-tapped position: $positionName")
            val playerId = positionToPlayerId[positionName]
            activity?.runOnUiThread {
                (activity as? MainActivity)?.setPreviousFragment(this@FieldingPositionsFragment, getCurrentTitle())

                val fragment = FielderDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(FielderDetailFragment.ARG_POSITION_NAME, positionName)
                        if (!playerId.isNullOrEmpty()) {
                            putString(FielderDetailFragment.ARG_PLAYER_ID, playerId)
                        }
                    }
                }

                val handledByActivity = (activity as? MainActivity)?.let { main ->
                    main.navigateToFragment(
                        fragment,
                        title = "Fielder: $positionName",
                        addToBackStack = true
                    )
                    true
                } ?: false

                if (!handledByActivity) {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit()
                }
            }
        }

        @JavascriptInterface
        fun onFielderSingleTap(positionName: String) {
            if (!compareMode || singlePositionMode) return
            activity?.runOnUiThread {
                toggleSelection(positionName)
            }
        }
    }

    private fun getCurrentTitle(): String {
        return when {
            singlePositionMode && !singlePositionName.isNullOrEmpty() -> "Field View: $singlePositionName"
            compareMode -> "Compare Positions"
            else -> "Fielding Positions"
        }
    }

    private fun setupCompareButton() {
        if (compareMode && !singlePositionMode) {
            binding.btnCompare.visibility = View.VISIBLE
            binding.btnCompare.setOnClickListener {
                if (selectedPositions.size >= 2) {
                    val fragment = PositionsComparisonFragment().apply {
                        arguments = Bundle().apply {
                            putStringArrayList(PositionsComparisonFragment.ARG_POSITIONS, ArrayList(selectedPositions))
                        }
                    }
                    // Prefer activity navigation to keep header/back consistent
                    val handledByActivity = (activity as? MainActivity)?.let { main ->
                        main.navigateToFragment(
                            fragment,
                            title = "Compare Positions",
                            addToBackStack = true
                        )
                        true
                    } ?: false

                    if (!handledByActivity) {
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(null)
                            .commit()
                    }
                }
            }
        } else {
            binding.btnCompare.visibility = View.GONE
        }
        updateCompareButtonState()
    }

    private fun toggleSelection(name: String) {
        if (selectedPositions.contains(name)) {
            selectedPositions.remove(name)
            sendSelectionToWeb(name, false)
        } else {
            if (selectedPositions.size >= 3) {
                Toast.makeText(requireContext(), "You can compare up to 3 positions.", Toast.LENGTH_SHORT).show()
                return
            }
            selectedPositions.add(name)
            sendSelectionToWeb(name, true)
        }
        updateCompareButtonState()
    }

    private fun sendSelectionToWeb(name: String, select: Boolean) {
        val js = """
            (function(){
              var els = Array.from(document.querySelectorAll('.position')).filter(e => e.dataset.name === '${name.replace("'", "\\'")}');
              els.forEach(function(el){ el.classList.${if (select) "add" else "remove"}('selected'); });
            })();
        """.trimIndent()
        try { binding.webFielding.evaluateJavascript(js, null) } catch (_: Throwable) {}
    }

    private fun updateCompareButtonState() {
        if (!compareMode || singlePositionMode) return
        val count = selectedPositions.size
        binding.btnCompare.isEnabled = count >= 2
        binding.btnCompare.text = if (count >= 2) "Compare (${count})" else "Select at least 2 positions"
    }

    companion object {
        const val ARG_COMPARE_MODE = "compare_mode"
        const val ARG_PRESELECTED_POSITIONS = "preselected_positions"
        const val ARG_SINGLE_POSITION_MODE = "single_position_mode"
        const val ARG_SINGLE_POSITION_NAME = "single_position_name"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        try { parentFragmentManager.removeOnBackStackChangedListener(backStackListener) } catch (_: Throwable) {}
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