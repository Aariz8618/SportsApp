package com.aariz.sportsapp

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aariz.sportsapp.adapters.HorizontalCardAdapter
import com.aariz.sportsapp.databinding.ActivityHighlightPlayerBinding
import com.aariz.sportsapp.model.SimpleShow
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.UploadCallback
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import kotlin.Int


class HighlightPlayerActivity : ComponentActivity() {

    companion object {
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_SUBTITLE = "extra_subtitle"
        const val EXTRA_VIDEO_URL = "extra_video_url"
    }

    private lateinit var binding: ActivityHighlightPlayerBinding
    private var player: ExoPlayer? = null

    private var isImmersive = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val config = HashMap<String, String>()
        config["cloud_name"] = "dvqhrnvc4"
        config["api_key"] = "251656799157299"
        config["api_secret"] = "0iEj7vGIV5b1e4dVWnYcPz86Nk4"
        MediaManager.init(this, config)

        // Now you can call MediaManager.get()
        val filePath = """C:\Users\Aariz Kazi\Downloads\Australia v South Africa 2025-26  Second ODI -.mp4"""
//        val signatureFromServer = "1c4fa8abfcc6a4b8e084a16d846a273e6536f576"
//        val timestampFromServer = 1756019224
//        MediaManager.get().upload(filePath)
//            .option("resource_type", "video")
//            .option("upload_preset", "ml_default")
//            .option("signature", signatureFromServer)
//            .option("timestamp", timestampFromServer)
//            .callback(object : UploadCallback {
//                override fun onStart(requestId: String) {Toast.makeText(this@HighlightPlayerActivity, "Upload started...", Toast.LENGTH_SHORT).show()
//                }
//                override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {}
//                override fun onSuccess(requestId: String, resultData: Map<*, *>) {Toast.makeText(this@HighlightPlayerActivity, "File uploaded successfully!", Toast.LENGTH_SHORT).show()
//                }
//                override fun onError(requestId: String, error: com.cloudinary.android.callback.ErrorInfo) {Toast.makeText(this@HighlightPlayerActivity, "Upload failed: ${error.description}", Toast.LENGTH_LONG).show()
//                }
//                override fun onReschedule(requestId: String, error: com.cloudinary.android.callback.ErrorInfo) {}
//            })
//            .dispatch()

//        val filePath = """C:\Users\Aariz Kazi\Downloads\Australia v South Africa 2025-26  Second ODI -.mp4"""
        uploadVideo(filePath)


        binding = ActivityHighlightPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Title row
        binding.txtTitle.text = intent.getStringExtra(EXTRA_TITLE) ?: "Highlight"
        binding.txtSubtitle.text = intent.getStringExtra(EXTRA_SUBTITLE) ?: ""

        binding.btnBack.setOnClickListener { finish() }
        binding.btnFullscreen.setOnClickListener { toggleFullscreen() }

        // Setup suggestions list (simple demo like first image)
        setupSuggestions(
            binding.rvSuggestions)

        initializePlayer()
    }

    interface CloudinaryApi {
        @GET("get-signature")
        suspend fun getSignature(): SignatureResponse
    }

    data class SignatureResponse(val signature: String, val timestamp: Long)
    private fun uploadVideo(filePath: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:3000/")  // your Node server
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(CloudinaryApi::class.java)

        lifecycleScope.launch {
            try {
                val response = api.getSignature()
                MediaManager.get().upload(filePath)
                    .option("resource_type", "video")
                    .option("upload_preset", "ml_default")
                    .option("signature", response.signature)
                    .option("timestamp", response.timestamp)
                    .callback(object : UploadCallback {
                        override fun onStart(requestId: String) {
                            Toast.makeText(
                                this@HighlightPlayerActivity,
                                "Upload started...",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {}
                        override fun onSuccess(requestId: String, resultData: Map<*, *>) {
                            Toast.makeText(
                                this@HighlightPlayerActivity,
                                "File uploaded successfully!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onError(
                            requestId: String,
                            error: com.cloudinary.android.callback.ErrorInfo
                        ) {
                            Toast.makeText(
                                this@HighlightPlayerActivity,
                                "Upload failed: ${error.description}",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        override fun onReschedule(
                            requestId: String,
                            error: com.cloudinary.android.callback.ErrorInfo
                        ) {
                        }
                    })
                    .dispatch()
            } catch (e: Exception) {
                Toast.makeText(
                    this@HighlightPlayerActivity,
                    "Error fetching signature",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupSuggestions(rv: RecyclerView?) {
        rv?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val items = listOf(
            SimpleShow("Handpicked 1", "https://images.unsplash.com/photo-1526948128573-703ee1aeb6fa?w=400"),
            SimpleShow("Handpicked 2", "https://images.unsplash.com/photo-1495567720989-cebdbdd97913?w=400"),
            SimpleShow("Handpicked 3", "https://images.unsplash.com/photo-1517602302552-471fe67acf66?w=400")
        )
        rv?.adapter = HorizontalCardAdapter(items) { /* no-op for now */ }
    }

    @OptIn(UnstableApi::class)
    private fun initializePlayer() {
        val url = intent.getStringExtra(EXTRA_VIDEO_URL)
        if (url.isNullOrEmpty()) {
            Toast.makeText(this, "Video URL missing", Toast.LENGTH_SHORT).show()
            return
        }
        player = ExoPlayer.Builder(this).build()
        binding.playerView.player = player
        binding.playerView.controllerAutoShow = true
        val item = MediaItem.fromUri(url)
        player?.setMediaItem(item)
        player?.prepare()
        player?.playWhenReady = true
    }

    private fun toggleFullscreen() {
        if (!isImmersive) {
            // Enter fullscreen landscape
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
            enterImmersiveMode(true)
        } else {
            // Exit fullscreen to portrait
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            enterImmersiveMode(false)
        }
        isImmersive = !isImmersive
    }

    private fun enterImmersiveMode(enable: Boolean) {
        val decor = window.decorView
        if (enable) {
            if (Build.VERSION.SDK_INT >= 30) {
                window.insetsController?.let { controller ->
                    controller.hide(android.view.WindowInsets.Type.systemBars())
                    controller.systemBarsBehavior =
                        android.view.WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }
            } else {
                @Suppress("DEPRECATION")
                decor.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    )
            }
            binding.appBar.visibility = View.GONE
        } else {
            if (Build.VERSION.SDK_INT >= 30) {
                window.insetsController?.show(android.view.WindowInsets.Type.systemBars())
            } else {
                @Suppress("DEPRECATION")
                decor.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            }
            binding.appBar.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        val isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        enterImmersiveMode(isLandscape)
        isImmersive = isLandscape
        // Keep the fullscreen icon in sync with current state
        binding.btnFullscreen.setImageResource(
            if (isLandscape) R.drawable.ic_fullscreen_exit else R.drawable.ic_fullscreen
        )
    }

    override fun onStart() {
        super.onStart()
        if (player == null) initializePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    private fun releasePlayer() {
        binding.playerView.player = null
        player?.release()
        player = null
    }
}
