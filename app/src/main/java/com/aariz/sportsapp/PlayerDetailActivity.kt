package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.aariz.sportsapp.api.CricApiClient
import com.aariz.sportsapp.databinding.ActivityPlayerDetailBinding
import com.aariz.sportsapp.models.PlayerDetailResponse
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToLong

class PlayerDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityPlayerDetailBinding
    private val apiKey = "d048d0c6-efeb-4bf5-99e2-88f44cb23b82"
    private var currentFormat: String = "test"

    // Cache ratings per format once computed
    private val formatRatings: MutableMap<String, RatingBreakdown> = mutableMapOf()
    private var cachedTestRatings: TestRatings? = null
    private var cachedPlayerRole: String? = null
    var cachedDetail: com.aariz.sportsapp.models.PlayerDetail? = null

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

        // Clear rating placeholders
        binding.tvOverallRating?.text = "-"
        binding.tvFormatRating?.text = "-"
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
                            // Initialize caches BEFORE computing/binding ratings
                            cachedTestRatings = computeTestRatings(detail)
                            cachedPlayerRole = detail.role
                            cachedDetail = detail
                            computeAndBindRatings(detail)
                            setupTabs()
                            setupRatingClicks()
                            selectFormat("test")
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
        val notOuts = detail.getNotOuts("test")
        val highScore = detail.getHighScore("test")
        val doubleHundreds = detail.getDoubleHundreds("test")

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
        binding.testNotOutvalue.text = notOuts ?: "N/A"
        binding.testHSvalue.text = highScore ?: "N/A"
        binding.test200value.text = doubleHundreds ?: "N/A"

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
        val srBowl = detail.getBowlingStrikeRate("test")

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
        try { binding.test5wvalue.text = fiveWickets ?: "N/A" } catch (_: Throwable) {}
        try { binding.test10wvalue.text = tenWickets ?: "N/A" } catch (_: Throwable) {}
        try { binding.testSRbowlvalue.text = srBowl ?: "N/A" } catch (_: Throwable) {}
        try { binding.testballsvalue.text = computeBalls(srBowl, wickets) } catch (_: Throwable) {}
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
        val notOuts = detail.getNotOuts("odi")
        val highScore = detail.getHighScore("odi")
        val doubleHundreds = detail.getDoubleHundreds("odi")

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
        binding.ODI4s6svalue.text = "${fours ?: "N/A"}/${sixes ?: "N/A"}"
        binding.odi200value.text = doubleHundreds ?: "N/A"
        binding.ODINotOutvalue.text = notOuts ?: "N/A"
        binding.ODIHSvalue.text = highScore ?: "N/A"

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
        val tenWickets = detail.getTenWickets("odi")
        val srBowl = detail.getBowlingStrikeRate("odi")

        // Always show ODI bowling stats
        binding.ODIBowling.visibility = View.VISIBLE

        binding.odimatchbowlvalue.text = matches ?: "N/A"
        binding.odiinnbowlvalue.text = innings ?: "N/A"
        binding.odiwicketsvalue.text = wickets ?: "N/A"
        binding.odirunsbowlvalue.text = runs ?: "N/A"
        binding.odiavgbowlvalue.text = average ?: "N/A"
        binding.odieconomyvalue.text = economy ?: "N/A"
        try { binding.ODIBBIvalue.text = bestBowlingInnings ?: "N/A" } catch (_: Throwable) { binding.ODIBBIvalue.text = bestBowlingInnings ?: "N/A" }
        try { binding.ODIBBMvalue.text = bestBowlingMatch ?: "N/A" } catch (_: Throwable) { binding.ODIBBMvalue.text = bestBowlingMatch ?: "N/A" }
        try { binding.ODI5wvalue.text = fiveWickets ?: "N/A" } catch (_: Throwable) {}
        try { binding.ODI10wvalue.text = tenWickets ?: "N/A" } catch (_: Throwable) {}
        try { binding.ODISRbowlvalue.text = srBowl ?: "N/A" } catch (_: Throwable) {}
        try { binding.ODIballsvalue.text = computeBalls(srBowl, wickets) } catch (_: Throwable) {}
        Log.d("PlayerDetail", "ODI bowling stats populated")
    }

    private fun populateT20BattingStats(detail: com.aariz.sportsapp.models.PlayerDetail) {
        val matches = detail.getMatches("t20")
        val inning = detail.getInnings("t20", "batting")
        val runs = detail.getRuns("t20")
        val average = detail.getBattingAverage("t20")
        val strikeRate = detail.getStrikeRate("t20")
        val ballsFaced = detail.getBallsFaced("t20")
        val hundreds = detail.getHundreds("t20")
        val fifties = detail.getFifties("t20")
        val fours = detail.getFours("t20")
        val sixes = detail.getSixes("t20")
        val notOuts = detail.getNotOuts("t20")
        val highScore = detail.getHighScore("t20")
        val doubleHundreds = detail.getDoubleHundreds("t20")

        // Always show T20 batting stats
        binding.T20IBatting.visibility = View.VISIBLE

        binding.T20matchvalue.text = matches ?: "N/A"
        binding.T20inn.text =  inning ?: "N/A"
        binding.T20runsvalue.text = runs ?: "N/A"
        binding.T20avgvalue.text = average ?: "N/A"
        binding.T20srvalue.text = strikeRate ?: "N/A"
        binding.T20bfvalue.text = ballsFaced ?: "N/A"
        binding.T20100value.text = hundreds ?: "N/A"
        binding.T2050value.text = fifties ?: "N/A"
        binding.T204s6svalue.text = "${fours ?: "N/A"}/${sixes ?: "N/A"}"
        binding.T20NotOutvalue.text = notOuts ?: "N/A"
        binding.T20HSvalue.text = highScore ?: "N/A"
        binding.T20200value.text = doubleHundreds ?: "N/A"

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
        val srBowl = detail.getBowlingStrikeRate("t20")

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
        try { binding.T205wvalue.text = fiveWickets ?: "N/A" } catch (_: Throwable) {}
        try { binding.T2010wvalue.text = detail.getTenWickets("t20") ?: "N/A" } catch (_: Throwable) {}
        binding.T20SRbowlvalue.text = srBowl ?: "N/A"
        binding.T20ballsvalue.text = computeBalls(srBowl, wickets)

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
        val notOuts = detail.getNotOuts("ipl")
        val highScore = detail.getHighScore("ipl")
        val doubleHundreds = detail.getDoubleHundreds("ipl")

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
        binding.IPLNotOutvalue.text = notOuts ?: "N/A"
        binding.IPLHSvalue.text = highScore ?: "N/A"
        binding.IPL200value.text = doubleHundreds ?: "N/A"

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
        val srBowl = detail.getBowlingStrikeRate("ipl")

        // Always show IPL bowling stats
        binding.IPLBowling.visibility = View.VISIBLE

        binding.IPLmatchbowlvalue.text = matches ?: "N/A"
        binding.IPLinnbowlvalue.text = innings ?: "N/A"
        binding.IPLwicketsvalue.text = wickets ?: "N/A"
        binding.IPLrunsbowlvalue.text = runs ?: "N/A"
        binding.IPLavgbowlvalue.text = average ?: "N/A"
        binding.IPLeconomyvalue.text = economy ?: "N/A"
        binding.IPLBBIvalue.text = bestBowlingInnings ?: "N/A"
        binding.IPLBBMvalue.text = bestBowlingMatch ?: "N/A"
        binding.IPL5wvalue.text = fiveWickets ?: "N/A"
        binding.IPL10wvalue.text = detail.getTenWickets("ipl") ?: "N/A"
        binding.IPLSRbowlvalue.text = srBowl ?: "N/A"
        binding.IPLballsvalue.text = computeBalls(srBowl, wickets)

        Log.d("PlayerDetail", "IPL bowling stats populated")
    }

    private fun setupTabs() {
        binding.tabTest.setOnClickListener { selectFormat("test") }
        binding.tabOdi.setOnClickListener { selectFormat("odi") }
        binding.tabT20i.setOnClickListener { selectFormat("t20") }
        binding.tabIpl.setOnClickListener { selectFormat("ipl") }
    }

    private fun selectFormat(format: String) {
        currentFormat = format
        // Visual state
        binding.tabTest.isSelected = format == "test"
        binding.tabOdi.isSelected = format == "odi"
        binding.tabT20i.isSelected = format == "t20"
        binding.tabIpl.isSelected = format == "ipl"

        // Toggle stats visibility
        val showTest = format == "test"
        val showOdi = format == "odi"
        val showT20 = format == "t20"
        val showIpl = format == "ipl"

        binding.TestBatting.visibility = if (showTest) View.VISIBLE else View.GONE
        binding.TestBowling.visibility = if (showTest) View.VISIBLE else View.GONE

        binding.ODIBatting.visibility = if (showOdi) View.VISIBLE else View.GONE
        binding.ODIBowling.visibility = if (showOdi) View.VISIBLE else View.GONE

        binding.T20IBatting.visibility = if (showT20) View.VISIBLE else View.GONE
        binding.T20IBowling.visibility = if (showT20) View.VISIBLE else View.GONE

        binding.IPLBatting.visibility = if (showIpl) View.VISIBLE else View.GONE
        binding.IPLBowling.visibility = if (showIpl) View.VISIBLE else View.GONE

        // Update format rating
        if (format == "test") {
            val tr = cachedTestRatings ?: computeTestRatingsFromBindingsSafe()
            val mode = detectRoleMode(cachedPlayerRole)
            val headerSuffix = when (mode) {
                RoleMode.BOWLER -> " (Bowler Mode)"
                RoleMode.BATSMAN -> " (Batsman Mode)"
                RoleMode.ALL_ROUNDER -> " (All-Rounder Mode)"
            }
            binding.tvFormatHeader?.text = "Test Rating$headerSuffix"
            val score = when (mode) {
                RoleMode.BOWLER -> tr?.BowlingRating
                RoleMode.BATSMAN -> tr?.BattingRating
                RoleMode.ALL_ROUNDER -> tr?.Final_AllRounder_Mode
            }
            binding.tvFormatRating?.text = score?.toString()?.plus("/100") ?: "-"
        } else if (format == "t20") {
            val mode = detectRoleMode(cachedPlayerRole)
            val batting = computeT20BattingRating()
            val bowling = computeT20BowlingRating()
            val headerSuffix = when (mode) {
                RoleMode.BOWLER -> " (Bowler Mode)"
                RoleMode.BATSMAN -> " (Batsman Mode)"
                RoleMode.ALL_ROUNDER -> " (All-Rounder Mode)"
            }
            binding.tvFormatHeader?.text = "T20I Rating$headerSuffix"
            val score = when (mode) {
                RoleMode.BOWLER -> bowling
                RoleMode.BATSMAN -> batting
                RoleMode.ALL_ROUNDER -> ((batting + bowling) / 2.0).roundToLong().toInt()
            }
            binding.tvFormatRating?.text = score.toString().plus("/100")
        } else if (format == "ipl") {
            val mode = detectRoleMode(cachedPlayerRole)
            val batting = computeIplBattingRating()
            val bowling = computeIplBowlingRating()
            val headerSuffix = when (mode) {
                RoleMode.BOWLER -> " (Bowler Mode)"
                RoleMode.BATSMAN -> " (Batsman Mode)"
                RoleMode.ALL_ROUNDER -> " (All-Rounder Mode)"
            }
            binding.tvFormatHeader?.text = "IPL Rating$headerSuffix"
            val score = when (mode) {
                RoleMode.BOWLER -> bowling
                RoleMode.BATSMAN -> batting
                RoleMode.ALL_ROUNDER -> ((batting + bowling) / 2.0).roundToLong().toInt()
            }
            binding.tvFormatRating?.text = score.toString().plus("/100")
        } else if (format == "odi") {
            val mode = detectRoleMode(cachedPlayerRole)
            val batting = cachedDetail?.let { computeOdiBattingRatingFromDetail(it) } ?: 0
            val bowling = computeOdiBowlingRatingFromBindings()
            val headerSuffix = when (mode) {
                RoleMode.BOWLER -> " (Bowler Mode)"
                RoleMode.BATSMAN -> " (Batsman Mode)"
                RoleMode.ALL_ROUNDER -> " (All-Rounder Mode)"
            }
            binding.tvFormatHeader?.text = "ODI Rating$headerSuffix"
            val score = when (mode) {
                RoleMode.BOWLER -> bowling
                RoleMode.BATSMAN -> batting
                RoleMode.ALL_ROUNDER -> ((batting + bowling) / 2.0).roundToLong().toInt()
            }
            binding.tvFormatRating?.text = score.toString().plus("/100")
        } else {
            val breakdown = formatRatings[format]
            binding.tvFormatHeader?.text = when (format) {
                "t20" -> "T20I Rating"
                "ipl" -> "IPL Rating"
                else -> "Format Rating"
            }
            binding.tvFormatRating?.text = breakdown?.total?.toInt()?.toString()?.plus("/100") ?: "-"
        }
    }

    private fun setupRatingClicks() {
        binding.cardOverallRating?.setOnClickListener {
            // Show a simple, accurate breakdown of the overall score using the same logic
            val mode = detectRoleMode(cachedPlayerRole)

            // Test
            val tr = cachedTestRatings ?: cachedDetail?.let { computeTestRatings(it) }
            val testScore = when {
                tr == null -> 0
                mode == RoleMode.BOWLER -> tr.BowlingRating
                mode == RoleMode.BATSMAN -> tr.BattingRating
                else -> tr.Final_AllRounder_Mode
            }

            // ODI
            val d = cachedDetail
            val odiBat = d?.let { computeOdiBattingRatingFromDetail(it) } ?: 0
            val odiBowl = d?.let { computeOdiBowlingRatingFromDetail(it) } ?: 0
            val odiScore = when (mode) {
                RoleMode.BOWLER -> odiBowl
                RoleMode.BATSMAN -> odiBat
                RoleMode.ALL_ROUNDER -> ((odiBat + odiBowl) / 2.0).roundToLong().toInt()
            }

            // T20I
            val t20Bat = d?.let { computeT20BattingRating() } ?: 0
            val t20Bowl = d?.let { computeT20BowlingRating() } ?: 0
            val t20Score = when (mode) {
                RoleMode.BOWLER -> t20Bowl
                RoleMode.BATSMAN -> t20Bat
                RoleMode.ALL_ROUNDER -> ((t20Bat + t20Bowl) / 2.0).roundToLong().toInt()
            }

            // IPL
            val iplBat = d?.let { computeIplBattingRating() } ?: 0
            val iplBowl = d?.let { computeIplBowlingRating() } ?: 0
            val iplScore = when (mode) {
                RoleMode.BOWLER -> iplBowl
                RoleMode.BATSMAN -> iplBat
                RoleMode.ALL_ROUNDER -> ((iplBat + iplBowl) / 2.0).roundToLong().toInt()
            }

            val overall = (((testScore + odiScore + t20Score + iplScore) / 4.0).roundToLong()).toInt()

            val modeLabel = when (mode) {
                RoleMode.BOWLER -> "Bowler Mode"
                RoleMode.BATSMAN -> "Batsman Mode"
                RoleMode.ALL_ROUNDER -> "All-Rounder Mode"
            }

            val msg = buildString {
                appendLine("Mode: $modeLabel")
                appendLine()
                appendLine("Test: $testScore/100")
                appendLine("ODI:  $odiScore/100")
                appendLine("T20I: $t20Score/100")
                appendLine()
                appendLine("Overall (mean of 3): $overall/100")
            }

            AlertDialog.Builder(this@PlayerDetailActivity)
                .setTitle("Overall Career Rating")
                .setMessage(msg)
                .setPositiveButton("Close", null)
                .show()
        }

        binding.cardFormatRating?.setOnClickListener {
            if (currentFormat == "test") {
                val tr = cachedTestRatings ?: computeTestRatingsFromBindingsSafe()
                showTestRatingDialog(tr, detectRoleMode(cachedPlayerRole))
            } else if (currentFormat == "t20") {
                val mode = detectRoleMode(cachedPlayerRole)
                val batting = computeT20BattingRating()
                val bowling = computeT20BowlingRating()
                showT20RatingDialog(batting, bowling, mode)
            } else if (currentFormat == "ipl") {
                val mode = detectRoleMode(cachedPlayerRole)
                val batting = computeIplBattingRating()
                val bowling = computeIplBowlingRating()
                showIplRatingDialog(batting, bowling, mode)
            } else if (currentFormat == "odi") {
                val mode = detectRoleMode(cachedPlayerRole)
                val batting = cachedDetail?.let { computeOdiBattingRatingFromDetail(it) } ?: 0
                val bowling = computeOdiBowlingRatingFromBindings()
                showOdiRatingDialog(batting, bowling, mode)
            } else {
                val b = formatRatings[currentFormat]
                showBreakdownDialog(
                    title = "${currentFormat.uppercase()} Rating Breakdown",
                    formatLabel = currentFormat.uppercase(),
                    breakdown = b
                )
            }
        }
    }

    private fun computeAndBindRatings(detail: com.aariz.sportsapp.models.PlayerDetail) {
        // Compute per-format breakdowns (kept for breakdown dialogs and other uses)
        val formats = listOf("test", "odi", "t20", "ipl")
        formats.forEach { mt ->
            formatRatings[mt] = calculateFormatRating(detail, mt)
        }

        // Compute strict arithmetic mean of final per-format scores (role-based selection)
        val mode = detectRoleMode(detail.role)

        // Test final score
        val tr = cachedTestRatings ?: computeTestRatings(detail)
        val testScore = when (mode) {
            RoleMode.BOWLER -> tr.BowlingRating
            RoleMode.BATSMAN -> tr.BattingRating
            RoleMode.ALL_ROUNDER -> ((tr.BattingRating + tr.BowlingRating) / 2.0).roundToLong().toInt()
        }

        // ODI final score
        val odiBat = computeOdiBattingRatingFromDetail(detail)
        val odiBowl = computeOdiBowlingRatingFromDetail(detail)
        val odiScore = when (mode) {
            RoleMode.BOWLER -> odiBowl
            RoleMode.BATSMAN -> odiBat
            RoleMode.ALL_ROUNDER -> ((odiBat + odiBowl) / 2.0).roundToLong().toInt()
        }

        // T20I final score
        val t20Bat = computeT20BattingRating()
        val t20Bowl = computeT20BowlingRating()
        val t20Score = when (mode) {
            RoleMode.BOWLER -> t20Bowl
            RoleMode.BATSMAN -> t20Bat
            RoleMode.ALL_ROUNDER -> ((t20Bat + t20Bowl) / 2.0).roundToLong().toInt()
        }

        // IPL final score
        val iplBat = computeIplBattingRating()
        val iplBowl = computeIplBowlingRating()
        val iplScore = when (mode) {
            RoleMode.BOWLER -> iplBowl
            RoleMode.BATSMAN -> iplBat
            RoleMode.ALL_ROUNDER -> ((iplBat + iplBowl) / 2.0).roundToLong().toInt()
        }

        val overallFinal = (((testScore + odiScore + t20Score) / 3.0).roundToLong()).toInt()
        binding.tvOverallRating?.text = overallFinal.toString()
        binding.tvOverallSubtitle?.text = "out of 100 • Tap to see breakdown"

        // Set current format rating (leave as-is, updated in selectFormat also)
        val current = formatRatings[currentFormat]
        binding.tvFormatHeader?.text = "${currentFormat.uppercase()} Batting Rating"
        binding.tvFormatRating?.text = current?.total?.toInt()?.toString()?.plus("/100") ?: "-"
    }

    private fun calculateFormatRating(
        d: com.aariz.sportsapp.models.PlayerDetail,
        mt: String
    ): RatingBreakdown {
        // Parse numbers safely
        fun num(s: String?): Double = s?.toDoubleOrNull() ?: 0.0
        fun nInt(s: String?): Int = s?.toIntOrNull() ?: 0

        // Batting metrics
        val matches = nInt(d.getMatches(mt))
        val runs = nInt(d.getRuns(mt))
        val avg = num(d.getBattingAverage(mt))
        val sr = num(d.getStrikeRate(mt))
        val hundreds = nInt(d.getHundreds(mt))
        val fifties = nInt(d.getFifties(mt))

        // Bowling metrics
        val wkts = nInt(d.getWickets(mt))
        val bowlAvg = num(d.getBowlingAverage(mt))
        val econ = num(d.getEconomy(mt))
        val fiveW = nInt(d.getFiveWickets(mt))

        // Heuristic normalization caps per format
        val battingRunsCap = when (mt) {
            "test" -> 10000.0
            "odi" -> 14000.0
            else -> 5000.0 // t20/ipl
        }
        val battingAvgCap = when (mt) {
            "test" -> 60.0
            "odi" -> 55.0
            else -> 55.0
        }
        val battingSrCap = when (mt) {
            "test" -> 70.0
            "odi" -> 120.0
            else -> 160.0
        }

        val bowlingWktsCap = when (mt) {
            "test" -> 600.0
            "odi" -> 400.0
            else -> 250.0
        }
        val bowlingAvgBest = 20.0 // lower is better
        val bowlingAvgWorst = 50.0
        val econBest = when (mt) { "test" -> 2.0; "odi" -> 4.0; else -> 6.0 }
        val econWorst = when (mt) { "test" -> 4.0; "odi" -> 7.0; else -> 9.0 }

        // Batting score (weights run-heavy but balanced)
        val battingRunsScore = (runs / battingRunsCap * 40.0).coerceIn(0.0, 40.0)
        val battingAvgScore = (avg / battingAvgCap * 35.0).coerceIn(0.0, 35.0)
        val battingSrScore = (sr / battingSrCap * 15.0).coerceIn(0.0, 15.0)
        val milestonesScore = ((hundreds * 2 + fifties) / 100.0 * 10.0).coerceIn(0.0, 10.0)
        val battingScore = if (mt == "odi") {
            computeOdiBattingRatingFromDetail(d).toDouble()
        } else {
            battingRunsScore + battingAvgScore + battingSrScore + milestonesScore
        }

        // Bowling score (lower avg/econ is better)
        val wktsScore = (wkts / bowlingWktsCap * 40.0).coerceIn(0.0, 40.0)
        val bowlAvgNorm = 1.0 - ((bowlAvg - bowlingAvgBest) / (bowlingAvgWorst - bowlingAvgBest)).coerceIn(0.0, 1.0)
        val bowlAvgScore = bowlAvgNorm * 35.0
        val econNorm = 1.0 - ((econ - econBest) / (econWorst - econBest)).coerceIn(0.0, 1.0)
        val econScore = econNorm * 15.0
        val fifersScore = (fiveW / 20.0 * 10.0).coerceIn(0.0, 10.0)
        val bowlingScore = wktsScore + bowlAvgScore + econScore + fifersScore

        // Experience/fielding proxy using matches (kept light)
        val expCap = when (mt) { "test" -> 200.0; "odi" -> 400.0; else -> 300.0 }
        val experienceScore = (matches / expCap * 70.0).coerceIn(20.0, 70.0) // give baseline credit

        // Blend: give role-agnostic balanced weight
        val battingWeight = 0.45
        val bowlingWeight = 0.35
        val experienceWeight = 0.20
        val total = battingScore * battingWeight + bowlingScore * bowlingWeight + experienceScore * experienceWeight

        return RatingBreakdown(
            batting = battingScore,
            bowling = bowlingScore,
            experience = experienceScore,
            total = total
        )
    }

    private fun calculateOverall(
        map: Map<String, RatingBreakdown>,
        d: com.aariz.sportsapp.models.PlayerDetail
    ): RatingBreakdown {
        val formats = listOf("test", "odi", "t20", "ipl")
        // Consider only formats the player actually played (matches > 0) and that exist in the map
        val validFormats = formats.filter { f ->
            val matches = d.getMatches(f).toIntOrNull() ?: 0
            matches > 0 && map[f] != null
        }

        fun avgOver(formatsList: List<String>, selector: (RatingBreakdown) -> Double): Double {
            val items = formatsList.mapNotNull { f -> map[f]?.let(selector) }
            return if (items.isNotEmpty()) items.average() else 0.0
        }

        val baseFormats = if (validFormats.isNotEmpty()) validFormats else map.keys.toList()

        return RatingBreakdown(
            batting = avgOver(baseFormats) { it.batting },
            bowling = avgOver(baseFormats) { it.bowling },
            experience = avgOver(baseFormats) { it.experience },
            total = avgOver(baseFormats) { it.total }
        )
    }

    private fun combineBreakdowns(): RatingBreakdown? {
        if (formatRatings.isEmpty()) return null
        val values = formatRatings.values
        fun avg(selector: (RatingBreakdown) -> Double): Double =
            if (values.isEmpty()) 0.0 else values.map(selector).average()

        return RatingBreakdown(
            batting = avg { it.batting },
            bowling = avg { it.bowling },
            experience = avg { it.experience },
            total = avg { it.total }
        )
    }

    private fun showBreakdownDialog(title: String, formatLabel: String, breakdown: RatingBreakdown?) {
        if (breakdown == null) return
        val view = LayoutInflater.from(this).inflate(R.layout.layout_rating_breakdown, null, false)
        val tvTitle = view.findViewById<TextView>(R.id.tvBreakdownTitle)
        val tvFormat = view.findViewById<TextView>(R.id.tvBreakdownFormat)
        val tvBatting = view.findViewById<TextView>(R.id.tvBattingComponent)
        val tvBowling = view.findViewById<TextView>(R.id.tvBowlingComponent)
        val tvFielding = view.findViewById<TextView>(R.id.tvFieldingComponent)
        val btnClose = view.findViewById<TextView>(R.id.btnClose)

        tvTitle.text = title
        tvFormat.text = "Format: $formatLabel"
        tvBatting.text = "Batting: ${breakdown.batting.toInt()}"
        tvBowling.text = "Bowling: ${breakdown.bowling.toInt()}"
        tvFielding.text = "Experience: ${breakdown.experience.toInt()}"

        val dialog = AlertDialog.Builder(this)
            .setView(view)
            .create()
        btnClose.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    // Dialog for Test rating (strict rubric). Re-uses layout_rating_breakdown
    private fun showTestRatingDialog(tr: TestRatings?, mode: RoleMode) {
        if (tr == null) return
        val view = LayoutInflater.from(this).inflate(R.layout.layout_rating_breakdown, null, false)
        val titleSuffix = when (mode) {
            RoleMode.BOWLER -> " (Bowler Mode)"
            RoleMode.BATSMAN -> " (Batsman Mode)"
            RoleMode.ALL_ROUNDER -> " (All-Rounder Mode)"
        }
        view.findViewById<TextView>(R.id.tvBreakdownTitle).text = "Test Rating Breakdown$titleSuffix"
        view.findViewById<TextView>(R.id.tvBreakdownFormat).text = "Format: TEST"
        view.findViewById<TextView>(R.id.tvBattingComponent).text = "Batting: ${tr.BattingRating}"
        view.findViewById<TextView>(R.id.tvBowlingComponent).text = "Bowling: ${tr.BowlingRating}"
        val chosen = when (mode) {
            RoleMode.BOWLER -> tr.BowlingRating
            RoleMode.BATSMAN -> tr.BattingRating
            RoleMode.ALL_ROUNDER -> tr.Final_AllRounder_Mode
        }
        view.findViewById<TextView>(R.id.tvFieldingComponent).text =
            "Finals → Bowler: ${tr.BowlingRating}, Batsman: ${tr.BattingRating}, All-Rounder: ${tr.Final_AllRounder_Mode} | Using: $chosen"
        val dialog = AlertDialog.Builder(this)
            .setView(view)
            .create()
        view.findViewById<TextView>(R.id.btnClose).setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    // Compute Test ratings (using PlayerDetail getters)
    private fun computeTestRatings(d: com.aariz.sportsapp.models.PlayerDetail): TestRatings {
        val matches = d.getMatches("test").toDoubleOrNull() ?: 0.0
        val batInns = d.getInnings("test", "batting").toDoubleOrNull() ?: 0.0
        val bowlInns = d.getInnings("test", "bowling").toDoubleOrNull() ?: 0.0
        val runs = d.getRuns("test").toDoubleOrNull() ?: 0.0
        val batAvg = d.getBattingAverage("test").toDoubleOrNull() ?: 0.0
        val batSR = d.getStrikeRate("test").toDoubleOrNull() ?: 0.0
        val hundreds = d.getHundreds("test").toDoubleOrNull() ?: 0.0
        val fifties = d.getFifties("test").toDoubleOrNull() ?: 0.0

        val wkts = d.getWickets("test").toDoubleOrNull() ?: 0.0
        val bowlAvg = d.getBowlingAverage("test").toDoubleOrNull() ?: 0.0
        val bowlSR = d.getBowlingStrikeRate("test").toDoubleOrNull() ?: 0.0
        val econ = d.getEconomy("test").toDoubleOrNull() ?: 0.0
        val fiveW = d.getFiveWickets("test").toDoubleOrNull() ?: 0.0
        val tenW = d.getTenWickets("test").toDoubleOrNull() ?: 0.0

        val batting = scoreTestBatting(batAvg, hundreds, batInns, runs, matches, batSR, fifties)
        val bowling = scoreTestBowling(bowlAvg, bowlSR, econ, fiveW, tenW, wkts, matches, bowlInns)
        val finalAllRounder = ((batting + bowling) / 2.0).toInt().coerceIn(0, 100)

        return TestRatings(
            BattingRating = batting,
            BowlingRating = bowling,
            Final_AllRounder_Mode = finalAllRounder
        )
    }

    // Safe fallback if cache is null
    private fun computeTestRatingsFromBindingsSafe(): TestRatings? {
        return try { cachedTestRatings ?: computeTestRatings(
            com.aariz.sportsapp.models.PlayerDetail(
                id = "-", name = binding.tvPlayerName.text?.toString() ?: "-",
                role = null, battingStyle = null, bowlingStyle = null, placeOfBirth = null,
                country = null, playerImg = null, stats = null
            )
        ) } catch (_: Throwable) { null }
    }

    // Scoring helpers per the rubric
    private fun scoreTestBatting(
        avg: Double,
        hundreds: Double,
        inns: Double,
        runs: Double,
        matches: Double,
        sr: Double,
        fifties: Double
    ): Int {
        // A) Average (50)
        val a = when {
            avg < 5 -> 1
            avg < 10 -> 3
            avg < 15 -> 8
            avg < 20 -> 12
            avg < 25 -> 16
            avg < 30 -> 22
            avg < 35 -> 28
            avg < 40 -> 33
            avg < 45 -> 39
            avg < 50 -> 44
            avg < 55 -> 48
            else -> 50
        }

        // B) 100s frequency (20)
        val hpi = if (inns > 0) hundreds / inns else 0.0
        val b = when {
            hpi < 0.02 -> 4
            hpi < 0.04 -> 8
            hpi < 0.06 -> 12
            hpi < 0.08 -> 16
            else -> 20
        }

        // C) Longevity (15)
        val rpm = if (matches > 0) runs / matches else 0.0
        val rpi = if (inns > 0) runs / inns else 0.0
        val c1 = when {
            rpm < 10 -> 0
            rpm < 20 -> 1
            rpm < 30 -> 2
            rpm < 40 -> 3
            rpm < 50 -> 4
            rpm < 60 -> 5
            rpm < 70 -> 6
            rpm < 80 -> 7
            rpm < 90 -> 8
            rpm < 100 -> 9
            else -> 10
        }
        val c2 = when {
            rpi < 20 -> 0
            rpi < 25 -> 1
            rpi < 30 -> 2
            rpi < 35 -> 3
            rpi < 40 -> 4
            else -> 5
        }
        val c = c1 + c2 // out of 15

        // D) SR + 50s frequency (15)
        val d1 = when {
            sr < 30 -> 1
            sr <= 40 -> 2
            sr <= 50 -> 3
            sr <= 60 -> 4
            sr <= 70 -> 5
            sr <= 80 -> 6
            else -> 7
        }
        val fpi = if (inns > 0) fifties / inns else 0.0
        val d2 = when {
            fpi < 0.05 -> 0
            fpi < 0.08 -> 1
            fpi < 0.11 -> 2
            fpi < 0.14 -> 3
            fpi < 0.17 -> 4
            fpi < 0.20 -> 5
            fpi < 0.25 -> 6
            fpi < 0.30 -> 7
            else -> 8
        }
        val d = d1 + d2 // out of 15

        return (a + b + c + d).coerceIn(0, 100)
    }

    private fun scoreTestBowling(
        avg: Double,
        sr: Double,
        econ: Double,
        fiveW: Double,
        tenW: Double,
        wkts: Double,
        matches: Double,
        inns: Double
    ): Int {
        // A) Average (40)
        val a = when {
            avg < 20 -> 40
            avg < 25 -> 35
            avg < 30 -> 29
            avg < 35 -> 23
            avg < 40 -> 17
            avg < 45 -> 9
            else -> 2
        }

        // B) Strike rate (25)
        val b = when {
            sr <= 50 -> 25
            sr <= 55 -> 22
            sr <= 60 -> 20
            sr <= 65 -> 15
            sr <= 70 -> 10
            else -> 5
        }

        // C) Economy (10)
        val c = when {
            econ < 2.50 -> 10
            econ < 3.00 -> 8
            econ < 3.50 -> 6
            econ < 4.00 -> 4
            econ < 4.50 -> 2
            else -> 0
        }

        // D) Big wicket impact (15)
        val fivePerInn = if (inns > 0) fiveW / inns else 0.0
        val d1 = when {
            fivePerInn >= 0.05 -> 10
            fivePerInn >= 0.03 -> 8
            fivePerInn >= 0.02 -> 6
            fivePerInn >= 0.01 -> 4
            else -> 0
        }
        val tenPerMatch = if (matches > 0) tenW / matches else 0.0
        val d2 = when {
            tenPerMatch >= 0.02 -> 5
            tenPerMatch >= 0.01 -> 3
            else -> 0
        }

        // E) Longevity (10)
        val wpm = if (matches > 0) wkts / matches else 0.0
        val wpi = if (inns > 0) wkts / inns else 0.0
        val e1 = when {
            wpm >= 6 -> 7
            wpm >= 5 -> 6
            wpm >= 4 -> 5
            wpm >= 3 -> 3
            wpm >= 2 -> 1
            else -> 0
        }
        val e2 = when {
            wpi >= 3 -> 3
            wpi >= 2 -> 2
            wpi >= 1 -> 1
            else -> 0
        }

        return (a + b + c + d1 + d2 + e1 + e2).coerceIn(0, 100)
    }

    // Helper: compute balls bowled when API doesn't provide it
    private fun computeBalls(strikeRate: String?, wickets: String?): String {
        val sr = strikeRate?.toDoubleOrNull()
        val wk = wickets?.toDoubleOrNull()
        if (sr == null || wk == null) return "N/A"
        val balls = (sr * wk).roundToLong()
        return balls.toString()
    }
}

// Simple container for rating pieces
private data class RatingBreakdown(
    val batting: Double,
    val bowling: Double,
    val experience: Double,
    val total: Double
)

// Strict rubric outputs for Test
private data class TestRatings(
    val BattingRating: Int,
    val BowlingRating: Int,
    val Final_AllRounder_Mode: Int
)

private enum class RoleMode { BOWLER, BATSMAN, ALL_ROUNDER }

private fun detectRoleMode(role: String?): RoleMode {
    val r = role?.lowercase()?.trim() ?: return RoleMode.ALL_ROUNDER
    return when {
        // common API values: "bowler", "bowling allrounder", "batsman", "batting allrounder", "allrounder"
        "all" in r -> RoleMode.ALL_ROUNDER
        r.contains("bowl") -> RoleMode.BOWLER
        r.contains("bat") -> RoleMode.BATSMAN
        else -> RoleMode.ALL_ROUNDER
    }
}

// ================= ODI Bowling Rating (role-based for ODI tab) =================
// Uses the user-provided rubric (0-100) with weights:
// Ave 20, SR 25, Econ 25, WPI 15, 5WHPI 15
private fun PlayerDetailActivity.computeOdiBowlingRatingFromBindings(): Int {
    fun d(txt: CharSequence?): Double = txt?.toString()?.trim()?.toDoubleOrNull() ?: 0.0
    // Read ODI bowling fields from bound views
    val ave = d(binding.odiavgbowlvalue.text)
    val sr = d(binding.ODISRbowlvalue.text)
    val econ = d(binding.odieconomyvalue.text)
    val wkts = d(binding.odiwicketsvalue.text)
    val inns = d(binding.odiinnbowlvalue.text)
    val fiveW = d(binding.ODI5wvalue.text)

    val wpi = if (inns > 0) wkts / inns else 0.0
    val fiveHpi = if (inns > 0) fiveW / inns else 0.0

    val avePts = mapOdiAveToPts(ave)
    val srPts = mapOdiSrToPts(sr)
    val econPts = mapOdiEconToPts(econ)
    val wpiPts = mapOdiWpiToPts(wpi)
    val fivePts = mapOdi5WhpiToPts(fiveHpi)

    val total = (avePts + srPts + econPts + wpiPts + fivePts).coerceIn(0, 100)
    return total
}

private fun mapOdiAveToPts(ave: Double): Int = when {
    ave <= 20 -> 20
    ave <= 24 -> 18
    ave <= 27 -> 16
    ave <= 30 -> 14
    ave <= 33 -> 12
    ave <= 35 -> 10
    ave <= 40 -> 6
    ave <= 42 -> 4
    else -> 2
}

private fun mapOdiSrToPts(sr: Double): Int = when {
    sr <= 30 -> 25
    sr <= 33 -> 23
    sr <= 36 -> 20
    sr <= 39 -> 18
    sr <= 42 -> 15
    sr <= 45 -> 12
    sr <= 50 -> 10
    else -> 8
}

private fun mapOdiEconToPts(econ: Double): Int = when {
    econ <= 4.8 -> 25
    econ <= 5.0 -> 22
    econ <= 5.5 -> 20
    econ <= 6.0 -> 18
    econ <= 6.5 -> 15
    econ <= 7.0 -> 12
    econ <= 8.0 -> 10
    else -> 8
}

private fun mapOdiWpiToPts(wpi: Double): Int = when {
    wpi >= 2.5 -> 15
    wpi >= 2.0 -> 14
    wpi >= 1.5 -> 12
    wpi >= 1.0 -> 10
    wpi >= 0.90 -> 8
    else -> 6
}

private fun mapOdi5WhpiToPts(v: Double): Int = when {
    v >= 0.25 -> 15
    v >= 0.20 -> 14
    v >= 0.15 -> 13
    v >= 0.10 -> 12
    v >= 0.05 -> 11
    v >= 0.019 -> 10
    else -> 5
}

private fun PlayerDetailActivity.showOdiRatingDialog(batting: Int, bowling: Int, mode: RoleMode) {
    val view = LayoutInflater.from(this).inflate(R.layout.layout_rating_breakdown, null, false)
    val titleSuffix = when (mode) {
        RoleMode.BOWLER -> " (Bowler Mode)"
        RoleMode.BATSMAN -> " (Batsman Mode)"
        RoleMode.ALL_ROUNDER -> " (All-Rounder Mode)"
    }
    view.findViewById<TextView>(R.id.tvBreakdownTitle).text = "ODI Rating Breakdown$titleSuffix"
    view.findViewById<TextView>(R.id.tvBreakdownFormat).text = "Format: ODI"
    view.findViewById<TextView>(R.id.tvBattingComponent).text = "Batting: $batting"
    view.findViewById<TextView>(R.id.tvBowlingComponent).text = "Bowling: $bowling"
    val chosen = when (mode) {
        RoleMode.BOWLER -> bowling
        RoleMode.BATSMAN -> batting
        RoleMode.ALL_ROUNDER -> ((batting + bowling) / 2.0).roundToLong().toInt()
    }
    view.findViewById<TextView>(R.id.tvFieldingComponent).text =
        "Finals → Bowler: $bowling, Batsman: $batting, All-Rounder: ${((batting + bowling) / 2.0).roundToLong()} | Using: $chosen"
    val dialog = AlertDialog.Builder(this)
        .setView(view)
        .create()
    view.findViewById<TextView>(R.id.btnClose).setOnClickListener { dialog.dismiss() }
    dialog.show()
}

// ================= T20I & IPL Ratings (Batting and Bowling) =================
// T20I/IPL Bowling Weights: Ave 20, SR 30, Econ 30, WPI 20
// T20I/IPL Batting Weights: Avg 25, 50s+ per Inns 20, RPI 10, SR 35, Boundaries/Inns 10

// ---- T20I Bowling ----
private fun PlayerDetailActivity.computeT20BowlingRating(): Int {
    val d = cachedDetail ?: return 0
    fun num(s: String?) = s?.toDoubleOrNull() ?: 0.0
    val ave = num(d.getBowlingAverage("t20"))
    val sr = num(d.getBowlingStrikeRate("t20"))
    val econ = num(d.getEconomy("t20"))
    val wkts = num(d.getWickets("t20"))
    val inns = num(d.getInnings("t20", "bowling"))
    val wpi = if (inns > 0) wkts / inns else 0.0
    return (
        mapT20AveToPts(ave) +
        mapT20SrToPts(sr) +
        mapT20EconToPts(econ) +
        mapT20WpiToPts(wpi)
    ).coerceIn(0, 100)
}

// ---- T20I Batting ----
private fun PlayerDetailActivity.computeT20BattingRating(): Int {
    val d = cachedDetail ?: return 0
    fun num(s: String?) = s?.toDoubleOrNull() ?: 0.0
    val inns = num(d.getInnings("t20", "batting"))
    val runs = num(d.getRuns("t20"))
    val avg = num(d.getBattingAverage("t20"))
    val sr = num(d.getStrikeRate("t20"))
    val fifties = num(d.getFifties("t20"))
    val hundreds = num(d.getHundreds("t20"))
    val fours = num(d.getFours("t20"))
    val sixes = num(d.getSixes("t20"))
    val fiftyPlusPerInns = if (inns > 0) (fifties + hundreds) / inns else 0.0
    val rpi = if (inns > 0) runs / inns else 0.0
    val bfPerInns = if (inns > 0) (fours + sixes) / inns else 0.0
    return (
        mapT20BatAvgPts(avg) +
        mapT20FiftyPlusPerInnPts(fiftyPlusPerInns) +
        mapT20RpiPts(rpi) +
        mapT20BatSrPts(sr) +
        mapT20BoundariesPerInnPts(bfPerInns)
    ).coerceIn(0, 100)
}

// ---- IPL Bowling (same rubric as T20I) ----
private fun PlayerDetailActivity.computeIplBowlingRating(): Int {
    val d = cachedDetail ?: return 0
    fun num(s: String?) = s?.toDoubleOrNull() ?: 0.0
    val ave = num(d.getBowlingAverage("ipl"))
    val sr = num(d.getBowlingStrikeRate("ipl"))
    val econ = num(d.getEconomy("ipl"))
    val wkts = num(d.getWickets("ipl"))
    val inns = num(d.getInnings("ipl", "bowling"))
    val wpi = if (inns > 0) wkts / inns else 0.0
    return (
        mapT20AveToPts(ave) +
        mapT20SrToPts(sr) +
        mapT20EconToPts(econ) +
        mapT20WpiToPts(wpi)
    ).coerceIn(0, 100)
}

// ---- IPL Batting (same rubric as T20I) ----
private fun PlayerDetailActivity.computeIplBattingRating(): Int {
    val d = cachedDetail ?: return 0
    fun num(s: String?) = s?.toDoubleOrNull() ?: 0.0
    val inns = num(d.getInnings("ipl", "batting"))
    val runs = num(d.getRuns("ipl"))
    val avg = num(d.getBattingAverage("ipl"))
    val sr = num(d.getStrikeRate("ipl"))
    val fifties = num(d.getFifties("ipl"))
    val hundreds = num(d.getHundreds("ipl"))
    val fours = num(d.getFours("ipl"))
    val sixes = num(d.getSixes("ipl"))
    val fiftyPlusPerInns = if (inns > 0) (fifties + hundreds) / inns else 0.0
    val rpi = if (inns > 0) runs / inns else 0.0
    val bfPerInns = if (inns > 0) (fours + sixes) / inns else 0.0
    return (
        mapT20BatAvgPts(avg) +
        mapT20FiftyPlusPerInnPts(fiftyPlusPerInns) +
        mapT20RpiPts(rpi) +
        mapT20BatSrPts(sr) +
        mapT20BoundariesPerInnPts(bfPerInns)
    ).coerceIn(0, 100)
}

// ---- T20I/IPL Bowling mappers ----
private fun mapT20AveToPts(ave: Double): Int = when {
    ave <= 15 -> 20
    ave <= 18 -> 18
    ave <= 20 -> 16
    ave <= 22 -> 14
    ave <= 25 -> 12
    ave <= 26 -> 10
    ave <= 27 -> 8
    ave <= 29 -> 6
    ave <= 30 -> 4
    else -> 2
}

private fun mapT20SrToPts(sr: Double): Int = when {
    sr <= 15 -> 30
    sr <= 18 -> 27
    sr <= 19 -> 24
    sr <= 20 -> 21
    sr <= 22 -> 18
    sr <= 24 -> 15
    sr <= 30 -> 12
    else -> 8
}

private fun mapT20EconToPts(econ: Double): Int = when {
    econ <= 6.5 -> 30
    econ <= 7.0 -> 27
    econ <= 7.5 -> 24
    econ <= 8.0 -> 21
    econ <= 8.5 -> 18
    econ <= 9.0 -> 15
    econ <= 10.0 -> 12
    else -> 8
}

private fun mapT20WpiToPts(wpi: Double): Int = when {
    wpi >= 1.75 -> 20
    wpi >= 1.50 -> 18
    wpi >= 1.25 -> 16
    wpi >= 1.00 -> 14
    wpi >= 0.75 -> 12
    wpi >= 0.50 -> 10
    else -> 7
}

// ---- T20I/IPL Batting mappers ----
private fun mapT20BatAvgPts(avg: Double): Int = when {
    avg < 10 -> 2
    avg < 15 -> 5
    avg < 20 -> 8
    avg < 25 -> 12
    avg < 29 -> 17
    avg < 34 -> 21
    avg < 40 -> 23
    else -> 25
}

private fun mapT20FiftyPlusPerInnPts(v: Double): Int = when {
    v < 0.05 -> 1
    v < 0.10 -> 8
    v < 0.15 -> 15
    else -> 20
}

private fun mapT20RpiPts(v: Double): Int = when {
    v < 10 -> 0
    v < 15 -> 2
    v < 20 -> 4
    v < 25 -> 6
    v < 30 -> 8
    else -> 10
}

private fun mapT20BatSrPts(sr: Double): Int = when {
    sr < 110 -> 10
    sr < 120 -> 15
    sr < 130 -> 20
    sr < 140 -> 27
    sr < 150 -> 29
    sr < 170 -> 32
    else -> 35
}

private fun mapT20BoundariesPerInnPts(v: Double): Int = when {
    v < 1.0 -> 0
    v < 2.0 -> 2
    v < 3.0 -> 4
    v < 3.5 -> 6
    v < 4.0 -> 7
    v < 5.0 -> 8
    else -> 10
}

// ---- Dialogs for T20I/IPL ----
private fun PlayerDetailActivity.showT20RatingDialog(batting: Int, bowling: Int, mode: RoleMode) {
    val view = LayoutInflater.from(this).inflate(R.layout.layout_rating_breakdown, null, false)
    val titleSuffix = when (mode) {
        RoleMode.BOWLER -> " (Bowler Mode)"
        RoleMode.BATSMAN -> " (Batsman Mode)"
        RoleMode.ALL_ROUNDER -> " (All-Rounder Mode)"
    }
    view.findViewById<TextView>(R.id.tvBreakdownTitle).text = "T20I Rating Breakdown$titleSuffix"
    view.findViewById<TextView>(R.id.tvBreakdownFormat).text = "Format: T20I"
    view.findViewById<TextView>(R.id.tvBattingComponent).text = "Batting: $batting"
    view.findViewById<TextView>(R.id.tvBowlingComponent).text = "Bowling: $bowling"
    val chosen = when (mode) {
        RoleMode.BOWLER -> bowling
        RoleMode.BATSMAN -> batting
        RoleMode.ALL_ROUNDER -> ((batting + bowling) / 2.0).roundToLong().toInt()
    }
    view.findViewById<TextView>(R.id.tvFieldingComponent).text =
        "Finals → Bowler: $bowling, Batsman: $batting, All-Rounder: ${((batting + bowling) / 2.0).roundToLong()} | Using: $chosen"
    val dialog = AlertDialog.Builder(this)
        .setView(view)
        .create()
    view.findViewById<TextView>(R.id.btnClose).setOnClickListener { dialog.dismiss() }
    dialog.show()
}

private fun PlayerDetailActivity.showIplRatingDialog(batting: Int, bowling: Int, mode: RoleMode) {
    val view = LayoutInflater.from(this).inflate(R.layout.layout_rating_breakdown, null, false)
    val titleSuffix = when (mode) {
        RoleMode.BOWLER -> " (Bowler Mode)"
        RoleMode.BATSMAN -> " (Batsman Mode)"
        RoleMode.ALL_ROUNDER -> " (All-Rounder Mode)"
    }
    view.findViewById<TextView>(R.id.tvBreakdownTitle).text = "IPL Rating Breakdown$titleSuffix"
    view.findViewById<TextView>(R.id.tvBreakdownFormat).text = "Format: IPL"
    view.findViewById<TextView>(R.id.tvBattingComponent).text = "Batting: $batting"
    view.findViewById<TextView>(R.id.tvBowlingComponent).text = "Bowling: $bowling"
    val chosen = when (mode) {
        RoleMode.BOWLER -> bowling
        RoleMode.BATSMAN -> batting
        RoleMode.ALL_ROUNDER -> ((batting + bowling) / 2.0).roundToLong().toInt()
    }
    view.findViewById<TextView>(R.id.tvFieldingComponent).text =
        "Finals → Bowler: $bowling, Batsman: $batting, All-Rounder: ${((batting + bowling) / 2.0).roundToLong()} | Using: $chosen"
    val dialog = AlertDialog.Builder(this)
        .setView(view)
        .create()
    view.findViewById<TextView>(R.id.btnClose).setOnClickListener { dialog.dismiss() }
    dialog.show()
}

// ================= ODI Batting Rating (strict rubric 0–100) =================
// Inputs from PlayerDetail for ODI:
// I = innings (batting), R = runs, Avg = batting average, SR = strike rate,
// H = hundreds, D = double hundreds (counted in 100+), F4 = fours, F6 = sixes, Fifty = fifties
private fun computeOdiBattingRatingFromDetail(d: com.aariz.sportsapp.models.PlayerDetail): Int {
    fun num(s: String?) = s?.toDoubleOrNull() ?: 0.0
    val inns = num(d.getInnings("odi", "batting"))
    val runs = num(d.getRuns("odi"))
    val avg = num(d.getBattingAverage("odi"))
    val sr = num(d.getStrikeRate("odi"))
    val hundreds = num(d.getHundreds("odi"))
    val doubles = num(d.getDoubleHundreds("odi"))
    val fifties = num(d.getFifties("odi"))
    val fours = num(d.getFours("odi"))
    val sixes = num(d.getSixes("odi"))

    val c100 = hundreds + doubles
    val f100 = if (inns > 0) c100 / inns else 0.0
    val f50 = if (inns > 0) fifties / inns else 0.0
    val rpi = if (inns > 0) runs / inns else 0.0
    val fB = if (inns > 0) (fours + sixes) / inns else 0.0

    val pAvg = mapOdiBatAvgPts(avg)
    val p100 = mapOdiHundredsFreqPts(f100)
    val p50 = mapOdiFiftiesFreqPts(f50)
    val pRpi = mapOdiRpiPts(rpi)
    val pSr = mapOdiBatSrPts(sr)
    val pB = mapOdiBoundaryFreqPts(fB)

    return (pAvg + p100 + p50 + pRpi + pSr + pB).coerceIn(0, 100)
}

// Piecewise functions per user spec
private fun mapOdiBatAvgPts(avg: Double): Int = when {
    avg < 20 -> 0
    avg < 25 -> 6
    avg < 30 -> 12
    avg < 35 -> 18
    avg < 40 -> 23
    avg < 45 -> 26
    avg < 50 -> 28
    else -> 30
}

private fun mapOdiHundredsFreqPts(f100: Double): Int = when {
    f100 < 0.01 -> 3
    f100 < 0.04 -> 7
    f100 < 0.06 -> 11
    f100 < 0.08 -> 16
    else -> 20
}

private fun mapOdiFiftiesFreqPts(f50: Double): Int = when {
    f50 < 0.05 -> 0
    f50 < 0.08 -> 3
    f50 < 0.11 -> 6
    f50 < 0.15 -> 9
    f50 < 0.20 -> 12
    else -> 15
}

private fun mapOdiRpiPts(rpi: Double): Int = when {
    rpi < 15 -> 0
    rpi < 20 -> 2
    rpi < 25 -> 4
    rpi < 30 -> 6
    rpi < 35 -> 8
    else -> 10
}

private fun mapOdiBatSrPts(sr: Double): Int = when {
    sr < 60 -> 1
    sr < 70 -> 4
    sr < 80 -> 7
    sr < 90 -> 10
    sr < 100 -> 13
    else -> 15
}

private fun mapOdiBoundaryFreqPts(fB: Double): Int = when {
    fB < 1 -> 0
    fB < 2 -> 2
    fB < 3 -> 4
    fB < 4 -> 6
    fB < 5 -> 8
    else -> 10
}

// Detail-based ODI bowling calculator mirroring the binding-based rubric
private fun computeOdiBowlingRatingFromDetail(d: com.aariz.sportsapp.models.PlayerDetail): Int {
    fun num(s: String?) = s?.toDoubleOrNull() ?: 0.0
    val ave = num(d.getBowlingAverage("odi"))
    val sr = num(d.getBowlingStrikeRate("odi"))
    val econ = num(d.getEconomy("odi"))
    val wkts = num(d.getWickets("odi"))
    val inns = num(d.getInnings("odi", "bowling"))
    val fiveW = num(d.getFiveWickets("odi"))

    val wpi = if (inns > 0) wkts / inns else 0.0
    val fiveHpi = if (inns > 0) fiveW / inns else 0.0

    val avePts = mapOdiAveToPts(ave)
    val srPts = mapOdiSrToPts(sr)
    val econPts = mapOdiEconToPts(econ)
    val wpiPts = mapOdiWpiToPts(wpi)
    val fivePts = mapOdi5WhpiToPts(fiveHpi)

    return (avePts + srPts + econPts + wpiPts + fivePts).coerceIn(0, 100)
}