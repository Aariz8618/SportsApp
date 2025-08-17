package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.View
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
    private val apiKey = "d048d0c6-efeb-4bf5-99e2-88f44cb23b82"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get player ID from intent - try both possible key names
        val playerId = intent.getStringExtra("PLAYER_ID")
            ?: intent.getStringExtra("player_id")
            ?: run {
                Toast.makeText(this, "Player ID not found", Toast.LENGTH_SHORT).show()
                finish()
                return
            }

        Log.d("PlayerDetail", "Fetching details for player ID: $playerId")

        // Show loading state
        showLoadingState()
        fetchPlayerDetails(playerId)
    }

    private fun showLoadingState() {
        // Hide all stat cards initially
        binding.TestBatting.visibility = View.GONE
        binding.TestBowling.visibility = View.GONE
        binding.ODIBatting.visibility = View.GONE
        binding.ODIBowling.visibility = View.GONE
        binding.T20IBatting.visibility = View.GONE
        binding.T20IBowling.visibility = View.GONE
        binding.IPLBatting.visibility = View.GONE
        binding.IPLBowling.visibility = View.GONE
    }

    private fun fetchPlayerDetails(playerId: String) {
        CricApiClient.apiService.getPlayerInfo(apiKey, playerId)
            .enqueue(object : Callback<PlayerDetailResponse> {
                override fun onResponse(
                    call: Call<PlayerDetailResponse>,
                    response: Response<PlayerDetailResponse>
                ) {
                    Log.d("PlayerDetail", "Response code: ${response.code()}")

                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody?.data != null) {
                            val detail = responseBody.data
                            Log.d("PlayerDetail", "Player detail loaded successfully: ${detail.name}")

                            populatePlayerInfo(detail)
                            populateAllStats(detail)
                        } else {
                            Log.e("PlayerDetail", "Response body or data is null")
                            showError("No player data found")
                        }
                    } else {
                        Log.e("PlayerDetail", "Response not successful: ${response.code()} - ${response.message()}")
                        showError("Server error: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<PlayerDetailResponse>, t: Throwable) {
                    Log.e("PlayerDetail", "API call failed", t)
                    showError("Network error: ${t.message}")
                }
            })
    }

    private fun showError(message: String) {
        Toast.makeText(this@PlayerDetailActivity, message, Toast.LENGTH_SHORT).show()
        // You could also show a retry button or error state here
    }

    private fun populatePlayerInfo(detail: com.aariz.sportsapp.models.PlayerDetail) {
        // Basic player info
        binding.tvPlayerName.text = detail.name ?: "Unknown Player"
        binding.tvPlayerRole.text = detail.role ?: "N/A"
        binding.tvBattingStyle.text = detail.battingStyle ?: "N/A"
        binding.tvBowlingStyle.text = detail.bowlingStyle ?: "N/A"
        binding.tvCountry.text = detail.country ?: "N/A"
        binding.tvPlaceOfBirth.text = detail.placeOfBirth ?: "N/A"

        // Load player image with better error handling
        val imageUrl = detail.playerImg
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(this@PlayerDetailActivity)
                .load(imageUrl)
                .placeholder(com.aariz.sportsapp.R.drawable.ic_player_placeholder)
                .error(com.aariz.sportsapp.R.drawable.ic_player_placeholder)
                .centerCrop()
                .into(binding.ivPlayerImage)
        } else {
            binding.ivPlayerImage.setImageResource(com.aariz.sportsapp.R.drawable.ic_player_placeholder)
        }

        Log.d("PlayerDetail", "Basic player info populated")
    }

    private fun populateAllStats(detail: com.aariz.sportsapp.models.PlayerDetail) {
        // Show all format stats regardless of whether they have data or not
        populateTestBattingStats(detail)
        populateTestBowlingStats(detail)
        populateOdiBattingStats(detail)
        populateOdiBowlingStats(detail)
        populateT20BattingStats(detail)
        populateT20BowlingStats(detail)
        populateIplBattingStats(detail)
        populateIplBowlingStats(detail)
    }

    private fun populateTestBattingStats(detail: com.aariz.sportsapp.models.PlayerDetail) {
        val matches = detail.getMatches("test")
        val innings = detail.getInnings("test", "batting")
        val runs = detail.getRuns("test")
        val average = detail.getBattingAverage("test")
        val strikeRate = detail.getStrikeRate("test")
        val ballsFaced = detail.getBallsFaced("test")
        val hundreds = detail.getHundreds("test")
        val fifties = detail.getFifties("test")
        val fours = detail.getFours("test")
        val sixes = detail.getSixes("test")

        // Always show Test batting stats
        binding.TestBatting.visibility = View.VISIBLE

        binding.testmatchvalue.text = matches ?: "N/A"
        binding.testinnvalue.text = innings ?: "N/A"
        binding.testrunsvalue.text = runs ?: "N/A"
        binding.testavgvalue.text = average ?: "N/A"
        binding.testSRvalue.text = strikeRate ?: "N/A"
        binding.testBFvalue.text = ballsFaced ?: "N/A"
        binding.test100value.text = hundreds ?: "N/A"
        binding.test50value.text = fifties ?: "N/A"
        binding.test4s6svalue.text = "${fours ?: "N/A"}/${sixes ?: "N/A"}"

        Log.d("PlayerDetail", "Test batting stats populated")
    }

    private fun populateTestBowlingStats(detail: com.aariz.sportsapp.models.PlayerDetail) {
        val matches = detail.getMatches("test")
        val innings = detail.getInnings("test", "bowling")
        val wickets = detail.getWickets("test")
        val runs = detail.getBowlingRuns("test")
        val average = detail.getBowlingAverage("test")
        val economy = detail.getEconomy("test")
        val bestBowlingInnings = detail.getBestBowlingInnings("test")
        val bestBowlingMatch = detail.getBestBowlingMatch("test")
        val fourWickets = detail.getFourWickets("test")
        val fiveWickets = detail.getFiveWickets("test")
        val tenWickets = detail.getTenWickets("test")

        // Always show Test bowling stats
        binding.TestBowling.visibility = View.VISIBLE

        binding.testmatchbowlvalue.text = matches ?: "N/A"
        binding.testinnbowlvalue.text = innings ?: "N/A"
        binding.testwicketsvalue.text = wickets ?: "N/A"
        binding.testrunsbowlvalue.text = runs ?: "N/A"
        binding.testavgbowlvalue.text = average ?: "N/A"
        binding.testeconomyvalue.text = economy ?: "N/A"
        binding.testBBIvalue.text = bestBowlingInnings ?: "N/A"
        binding.testBBMvalue.text = bestBowlingMatch ?: "N/A"
        binding.test4w5w10wvalue.text = "${fourWickets ?: "N/A"}/${fiveWickets ?: "N/A"}/${tenWickets ?: "N/A"}"

        Log.d("PlayerDetail", "Test bowling stats populated")
    }

    private fun populateOdiBattingStats(detail: com.aariz.sportsapp.models.PlayerDetail) {
        val matches = detail.getMatches("odi")
        val innings = detail.getInnings("odi", "batting")
        val runs = detail.getRuns("odi")
        val average = detail.getBattingAverage("odi")
        val strikeRate = detail.getStrikeRate("odi")
        val ballsFaced = detail.getBallsFaced("odi")
        val hundreds = detail.getHundreds("odi")
        val fifties = detail.getFifties("odi")
        val fours = detail.getFours("odi")
        val sixes = detail.getSixes("odi")

        // Always show ODI batting stats
        binding.ODIBatting.visibility = View.VISIBLE

        binding.odimatchvalue.text = matches ?: "N/A"
        binding.odiinnvalue.text = innings ?: "N/A"
        binding.odirunsvalue.text = runs ?: "N/A"
        binding.odiavgvalue.text = average ?: "N/A"
        binding.odiSRvalue.text = strikeRate ?: "N/A"
        binding.odiBFvalue.text = ballsFaced ?: "N/A"
        binding.odi100value.text = hundreds ?: "N/A"
        binding.odi50value.text = fifties ?: "N/A"
        binding.odi4s6svalue.text = "${fours ?: "N/A"}/${sixes ?: "N/A"}"

        Log.d("PlayerDetail", "ODI batting stats populated")
    }

    private fun populateOdiBowlingStats(detail: com.aariz.sportsapp.models.PlayerDetail) {
        val matches = detail.getMatches("odi")
        val innings = detail.getInnings("odi", "bowling")
        val wickets = detail.getWickets("odi")
        val runs = detail.getBowlingRuns("odi")
        val average = detail.getBowlingAverage("odi")
        val economy = detail.getEconomy("odi")
        val bestBowlingInnings = detail.getBestBowlingInnings("odi")
        val bestBowlingMatch = detail.getBestBowlingMatch("odi")
        val fourWickets = detail.getFourWickets("odi")
        val fiveWickets = detail.getFiveWickets("odi")

        // Always show ODI bowling stats
        binding.ODIBowling.visibility = View.VISIBLE

        binding.odimatchbowlvalue.text = matches ?: "N/A"
        binding.odiinnbowlvalue.text = innings ?: "N/A"
        binding.odiwicketsvalue.text = wickets ?: "N/A"
        binding.odirunsbowlvalue.text = runs ?: "N/A"
        binding.odiavgbowlvalue.text = average ?: "N/A"
        binding.odieconomyvalue.text = economy ?: "N/A"
        binding.odiBBIvalue.text = bestBowlingInnings ?: "N/A"
        binding.odiBBMvalue.text = bestBowlingMatch ?: "N/A"
        binding.odi4w5w10wvalue.text = "${fourWickets ?: "N/A"}/${fiveWickets ?: "N/A"}"

        Log.d("PlayerDetail", "ODI bowling stats populated")
    }

    private fun populateT20BattingStats(detail: com.aariz.sportsapp.models.PlayerDetail) {
        val matches = detail.getMatches("t20")
        val runs = detail.getRuns("t20")
        val average = detail.getBattingAverage("t20")
        val strikeRate = detail.getStrikeRate("t20")
        val ballsFaced = detail.getBallsFaced("t20")
        val hundreds = detail.getHundreds("t20")
        val fifties = detail.getFifties("t20")
        val fours = detail.getFours("t20")
        val sixes = detail.getSixes("t20")

        // Always show T20 batting stats
        binding.T20IBatting.visibility = View.VISIBLE

        binding.T20matchvalue.text = matches ?: "N/A"
        binding.T20runsvalue.text = runs ?: "N/A"
        binding.T20avgvalue.text = average ?: "N/A"
        binding.T20srvalue.text = strikeRate ?: "N/A"
        binding.T20bfvalue.text = ballsFaced ?: "N/A"
        binding.T20100value.text = hundreds ?: "N/A"
        binding.T2050value.text = fifties ?: "N/A"
        binding.T204s6svalue.text = "${fours ?: "N/A"}/${sixes ?: "N/A"}"

        Log.d("PlayerDetail", "T20 batting stats populated")
    }

    private fun populateT20BowlingStats(detail: com.aariz.sportsapp.models.PlayerDetail) {
        val matches = detail.getMatches("t20")
        val innings = detail.getInnings("t20", "bowling")
        val wickets = detail.getWickets("t20")
        val runs = detail.getBowlingRuns("t20")
        val average = detail.getBowlingAverage("t20")
        val economy = detail.getEconomy("t20")
        val bestBowlingInnings = detail.getBestBowlingInnings("t20")
        val bestBowlingMatch = detail.getBestBowlingMatch("t20")
        val fourWickets = detail.getFourWickets("t20")
        val fiveWickets = detail.getFiveWickets("t20")

        // Always show T20 bowling stats
        binding.T20IBowling.visibility = View.VISIBLE

        binding.T20matchbowlvalue.text = matches ?: "N/A"
        binding.T20innbowlvalue.text = innings ?: "N/A"
        binding.T20wicketsvalue.text = wickets ?: "N/A"
        binding.T20runsbowlvalue.text = runs ?: "N/A"
        binding.T20avgbowlvalue.text = average ?: "N/A"
        binding.T20economyvalue.text = economy ?: "N/A"
        binding.T20BBIvalue.text = bestBowlingInnings ?: "N/A"
        binding.T20BBMvalue.text = bestBowlingMatch ?: "N/A"
        binding.T204w5w10wvalue.text = "${fourWickets ?: "N/A"}/${fiveWickets ?: "N/A"}"

        Log.d("PlayerDetail", "T20 bowling stats populated")
    }

    private fun populateIplBattingStats(detail: com.aariz.sportsapp.models.PlayerDetail) {
        val matches = detail.getMatches("ipl")
        val innings = detail.getInnings("ipl", "batting")
        val runs = detail.getRuns("ipl")
        val average = detail.getBattingAverage("ipl")
        val strikeRate = detail.getStrikeRate("ipl")
        val ballsFaced = detail.getBallsFaced("ipl")
        val hundreds = detail.getHundreds("ipl")
        val fifties = detail.getFifties("ipl")
        val fours = detail.getFours("ipl")
        val sixes = detail.getSixes("ipl")

        // Always show IPL batting stats
        binding.IPLBatting.visibility = View.VISIBLE

        binding.IPLmatchvalue.text = matches ?: "N/A"
        binding.IPLinnvalue.text = innings ?: "N/A"
        binding.IPLrunsvalue.text = runs ?: "N/A"
        binding.IPLavgvalue.text = average ?: "N/A"
        binding.IPLsrvalue.text = strikeRate ?: "N/A"
        binding.IPLbfvalue.text = ballsFaced ?: "N/A"
        binding.IPL100value.text = hundreds ?: "N/A"
        binding.IPL50value.text = fifties ?: "N/A"
        binding.IPL4s6svalue.text = "${fours ?: "N/A"}/${sixes ?: "N/A"}"

        Log.d("PlayerDetail", "IPL batting stats populated")
    }

    private fun populateIplBowlingStats(detail: com.aariz.sportsapp.models.PlayerDetail) {
        val matches = detail.getMatches("ipl")
        val innings = detail.getInnings("ipl", "bowling")
        val wickets = detail.getWickets("ipl")
        val runs = detail.getBowlingRuns("ipl")
        val average = detail.getBowlingAverage("ipl")
        val economy = detail.getEconomy("ipl")
        val bestBowlingInnings = detail.getBestBowlingInnings("ipl")
        val bestBowlingMatch = detail.getBestBowlingMatch("ipl")
        val fourWickets = detail.getFourWickets("ipl")
        val fiveWickets = detail.getFiveWickets("ipl")

        // Always show IPL bowling stats
        binding.IPLBowling.visibility = View.VISIBLE

        binding.IPLmatchbowlvalue.text = matches ?: "N/A"
        binding.IPLinnbowlvalue.text = innings ?: "N/A"
        binding.IPLwicketsvalue.text = wickets ?: "N/A"
        binding.IPLrunsbowlvalue.text = runs ?: "N/A"
        binding.IPLavgbowlvalue.text = average ?: "N/A"
        binding.IPLeconomyvalue.text = economy ?: "N/A"
        binding.IPLbbivalue.text = bestBowlingInnings ?: "N/A"
        binding.IPLbbmvalue.text = bestBowlingMatch ?: "N/A"
        binding.IPL4w5w10wvalue.text = "${fourWickets ?: "N/A"}/${fiveWickets ?: "N/A"}"

        Log.d("PlayerDetail", "IPL bowling stats populated")
    }
}