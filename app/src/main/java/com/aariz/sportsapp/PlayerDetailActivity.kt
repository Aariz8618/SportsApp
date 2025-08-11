package com.aariz.sportsapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aariz.sportsapp.api.CricApiClient
import com.aariz.sportsapp.databinding.ActivityPlayerDetailBinding
import com.aariz.sportsapp.models.PlayerDetailResponse
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlayerDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerDetailBinding
    private val apiKey = "YOUR_API_KEY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val playerId = intent.getStringExtra("PLAYER_ID") ?: return

        fetchPlayerDetails(playerId)
    }

    private fun fetchPlayerDetails(playerId: String) {
        CricApiClient.apiService.getPlayerInfo(apiKey, playerId)
            .enqueue(object : Callback<PlayerDetailResponse> {
                override fun onResponse(
                    call: Call<PlayerDetailResponse>,
                    response: Response<PlayerDetailResponse>
                ) {
                    if (response.isSuccessful && response.body()?.data != null) {
                        val detail = response.body()!!.data

                        binding.tvPlayerName.text = detail.name
                        binding.tvPlayerRole.text = detail.role ?: "N/A"
                        binding.tvBattingStyle.text = detail.battingStyle ?: "N/A"
                        binding.tvBowlingStyle.text = detail.bowlingStyle ?: "N/A"
                        binding.tvCountry.text = detail.country ?: "N/A"

                        Glide.with(this@PlayerDetailActivity)
                            .load(detail.playerImg)
                            .placeholder(com.aariz.sportsapp.R.drawable.ic_player_placeholder)
                            .into(binding.ivPlayerImage)

                        detail.stats?.let {
                            binding.tvMatches.text = "Matches: ${it.matches ?: "N/A"}"
                            binding.tvRuns.text = "Runs: ${it.runs ?: "N/A"}"
                            binding.tvWickets.text = "Wickets: ${it.wickets ?: "N/A"}"
                            binding.tvAverage.text = "Average: ${it.average ?: "N/A"}"
                        }
                    } else {
                        Toast.makeText(this@PlayerDetailActivity, "Details not found", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<PlayerDetailResponse>, t: Throwable) {
                    Toast.makeText(this@PlayerDetailActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
