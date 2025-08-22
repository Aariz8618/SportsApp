package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aariz.sportsapp.databinding.FragmentHowRankingsWorkBinding
import android.widget.LinearLayout
import android.widget.GridLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip

class HowRankingsWorkFragment : Fragment() {

    private var _binding: FragmentHowRankingsWorkBinding? = null
    private val binding get() = _binding!!

    private enum class Screen { TEAMS, PLAYERS }
    private var activeScreen: Screen = Screen.TEAMS
    private var selectedSectionKey: String = "overview"

    data class InfoItem(val heading: String, val description: String)
    data class Section(val title: String, val subtitle: String?, val content: List<InfoItem>)

    private val teamSections: Map<String, Section> by lazy {
        mapOf(
            "overview" to Section(
                title = "How Team Rankings Work",
                subtitle = "Understanding the ICC Team Ranking System",
                content = listOf(
                    InfoItem("Rating System", "Teams are rated using a points-based system where points are awarded based on match results and the strength of the opposition."),
                    InfoItem("Match Results Impact", "Wins against stronger teams earn more points. Losses against weaker teams result in larger point deductions."),
                    InfoItem("Time Weighting", "Recent matches carry more weight than older ones. Matches lose influence over time using a decay system.")
                )
            ),
            "calculation" to Section(
                title = "Rating Calculation",
                subtitle = "Understanding the ICC Team Ranking System",
                content = listOf(
                    InfoItem("Basic Formula", "Rating = (Points from matches ÷ Total matches) × 100. Each match result contributes points based on outcome and opposition strength."),
                    InfoItem("Opposition Strength", "Playing against top-ranked teams provides opportunities for bigger rating gains but also risks larger losses."),
                    InfoItem("Minimum Matches", "Teams need a minimum number of matches in each format to be eligible for rankings (typically 8-12 matches).")
                )
            ),
            "formats" to Section(
                title = "Format Differences",
                subtitle = "Understanding the ICC Team Ranking System",
                content = listOf(
                    InfoItem("Test Rankings", "Based on series results over 3-4 years. Each series is weighted equally regardless of number of matches."),
                    InfoItem("ODI Rankings", "Individual match results over 3 years. More recent matches have higher weightage in the calculation."),
                    InfoItem("T20I Rankings", "Based on individual T20I matches over 2-3 years with exponential decay for older matches.")
                )
            )
        )
    }

    private val playerSections: Map<String, Section> by lazy {
        mapOf(
            "overview" to Section(
                title = "How Player Rankings Work",
                subtitle = "Understanding the ICC Player Ranking System",
                content = listOf(
                    InfoItem("Performance Rating", "Players earn rating points based on their individual performance in each match, not just team results."),
                    InfoItem("Opposition Quality", "Better performances against stronger opposition bowling/batting attacks earn higher rating points."),
                    InfoItem("Consistency Matters", "Regular good performances are rewarded more than occasional brilliant performances with poor consistency.")
                )
            ),
            "batting" to Section(
                title = "Batting Rankings",
                subtitle = "Understanding the ICC Player Ranking System",
                content = listOf(
                    InfoItem("Run Scoring", "Points awarded based on runs scored, but adjusted for match situation, opposition strength, and conditions."),
                    InfoItem("Match Impact", "Innings that significantly contribute to team wins receive bonus points. Big scores in losing causes get fewer points."),
                    InfoItem("Format Adjustments", "Different scoring rates and match situations across Test, ODI, and T20I formats are accounted for.")
                )
            ),
            "bowling" to Section(
                title = "Bowling Rankings",
                subtitle = "Understanding the ICC Player Ranking System",
                content = listOf(
                    InfoItem("Wicket Taking", "Points for wickets taken, but quality of batsmen dismissed and match situation affect the points earned."),
                    InfoItem("Economy Rate", "Bowling economy and control also factor into ratings, not just wickets. Pressure bowling is rewarded."),
                    InfoItem("Match Context", "Taking wickets at crucial moments or defending small totals earns additional rating points.")
                )
            ),
            "allrounder" to Section(
                title = "All-rounder Rankings",
                subtitle = "Understanding the ICC Player Ranking System",
                content = listOf(
                    InfoItem("Combined Performance", "Ratings combine both batting and bowling performances, weighted based on their contribution to each discipline."),
                    InfoItem("Balance Requirement", "Players need meaningful contributions in both batting and bowling to qualify for all-rounder rankings."),
                    InfoItem("Versatility Bonus", "Players who can adapt their role based on match situations receive additional rating points.")
                )
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHowRankingsWorkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToggle()
        buildQuickFacts()
        render()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupToggle() {
        // Default selection
        binding.toggleGroup.check(binding.btnTeams.id)
        binding.btnTeams.setOnClickListener {
            activeScreen = Screen.TEAMS
            selectedSectionKey = "overview"
            render()
        }
        binding.btnPlayers.setOnClickListener {
            activeScreen = Screen.PLAYERS
            selectedSectionKey = "overview"
            render()
        }
    }

    private fun render() {
        // Build chips based on active screen
        val sections = if (activeScreen == Screen.TEAMS) teamSections else playerSections
        buildChips(sections.keys.toList())


        // Content list
        binding.containerContentList.removeAllViews()
        
        // Extras
        binding.containerExtras.removeAllViews()
        if (activeScreen == Screen.TEAMS && selectedSectionKey == "calculation") {
            binding.containerExtras.addView(createExampleCalculationCard())
        }
        if (activeScreen == Screen.TEAMS && selectedSectionKey == "overview") {
            binding.containerExtras.addView(createKeyFactorsRow())
        }
        if (activeScreen == Screen.PLAYERS && selectedSectionKey == "batting") {
            binding.containerExtras.addView(createKeyBattingCard())
        }
        if (activeScreen == Screen.PLAYERS && selectedSectionKey == "bowling") {
            binding.containerExtras.addView(createKeyBowlingCard())
        }
    }

    private fun buildChips(keys: List<String>) {
        binding.chipGroupSections.removeAllViews()
        keys.forEach { key ->
            val chip = Chip(requireContext()).apply {
                text = key.replaceFirstChar { it.uppercase() }
                isCheckable = true
                isClickable = true
                isChecked = key == selectedSectionKey
                setOnClickListener {
                    selectedSectionKey = key
                    render()
                }
            }
            binding.chipGroupSections.addView(chip)
        }
    }

    private fun createContentCard(item: InfoItem): View {
        val context = requireContext()
        val card = CardView(context)
        card.radius = 12f
        card.cardElevation = 2f
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        lp.bottomMargin = dp(8)
        card.layoutParams = lp

        val container = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(16), dp(16), dp(16), dp(16))
        }

        val tvTitle = TextView(context).apply {
            text = item.heading
            textSize = 16f
            setTextColor(requireContext().getColor(R.color.text_primary))
            setTypeface(typeface, android.graphics.Typeface.BOLD)
        }
        val tvDesc = TextView(context).apply {
            text = item.description
            textSize = 14f
            setTextColor(requireContext().getColor(R.color.text_secondary))
        }

        container.addView(tvTitle)
        container.addView(tvDesc)
        card.addView(container)
        return card
    }

    private fun createExampleCalculationCard(): View {
        val context = requireContext()
        val card = CardView(context)
        card.radius = 12f
        card.cardElevation = 2f
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        card.layoutParams = lp

        val container = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(16), dp(16), dp(16), dp(16))
        }

        val title = TextView(context).apply {
            text = "Example Calculation"
            textSize = 16f
            setTextColor(requireContext().getColor(R.color.text_primary))
            setTypeface(typeface, android.graphics.Typeface.BOLD)
        }
        container.addView(title)

        fun bullet(textStr: String): TextView = TextView(context).apply {
            text = "• $textStr"
            textSize = 13f
            setTextColor(requireContext().getColor(R.color.text_secondary))
        }

        val team = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            val h = TextView(context).apply {
                text = "Team A beats Team B"
                setTypeface(typeface, android.graphics.Typeface.BOLD)
                setTextColor(requireContext().getColor(R.color.text_primary))
            }
            addView(h)
            addView(bullet("Team A (Rating: 105) defeats Team B (Rating: 98)"))
            addView(bullet("Expected result based on ratings difference"))
            addView(bullet("Team A gains: +2 points, Team B loses: -2 points"))
            addView(bullet("If Team B had won: Team B gains +8, Team A loses -8"))
        }

        val player = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, dp(12), 0, 0)
            val h = TextView(context).apply {
                text = "Player Performance"
                setTypeface(typeface, android.graphics.Typeface.BOLD)
                setTextColor(requireContext().getColor(R.color.text_primary))
            }
            addView(h)
            addView(bullet("Batsman scores 85 runs vs strong bowling attack"))
            addView(bullet("Base points: 85 × 1.2 (opposition factor) = 102"))
            addView(bullet("Match impact: Team wins by 5 wickets = +15 bonus"))
            addView(bullet("Final rating points: 117 points for this innings"))
        }

        container.addView(team)
        container.addView(player)
        card.addView(container)
        return card
    }

    private fun createKeyFactorsRow(): View {
        val context = requireContext()
        val row = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
        }

        fun smallCard(title: String, desc: String): View {
            val card = CardView(context)
            card.radius = 12f
            card.cardElevation = 2f
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = dp(8)
            card.layoutParams = lp

            val container = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(dp(16), dp(16), dp(16), dp(16))
            }
            val t = TextView(context).apply {
                text = title
                setTypeface(typeface, android.graphics.Typeface.BOLD)
                setTextColor(requireContext().getColor(R.color.text_primary))
            }
            val d = TextView(context).apply {
                text = desc
                setTextColor(requireContext().getColor(R.color.text_secondary))
            }
            container.addView(t)
            container.addView(d)
            card.addView(container)
            return card
        }

        row.addView(smallCard("Time Decay", "Older matches gradually lose influence on ratings. Recent performances matter more than historical ones."))
        row.addView(smallCard("Home/Away", "Away victories are often worth more rating points due to the additional challenge of playing in foreign conditions."))
        row.addView(smallCard("Match Context", "World Cup and major tournament matches may carry additional weight in the ranking calculations."))
        return row
    }

    private fun createKeyBattingCard(): View {
        val context = requireContext()
        val card = CardView(context)
        card.radius = 12f
        card.cardElevation = 2f
        val container = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(16), dp(16), dp(16), dp(16))
        }
        val title = TextView(context).apply {
            text = "Key Batting Factors"
            setTypeface(typeface, android.graphics.Typeface.BOLD)
            setTextColor(requireContext().getColor(R.color.text_primary))
        }
        container.addView(title)

        fun p(t: String) = TextView(context).apply {
            text = t
            setTextColor(requireContext().getColor(R.color.text_secondary))
        }

        container.addView(p("High Impact: Match-winning innings, big scores under pressure"))
        container.addView(p("Medium Impact: Consistent 50+ scores, partnerships"))
        container.addView(p("Low Impact: Easy runs on flat pitches, stat-padding in dead matches"))
        container.addView(p("Bonus Points: Hundreds, series-deciding performances"))
        card.addView(container)
        return card
    }

    private fun createKeyBowlingCard(): View {
        val context = requireContext()
        val card = CardView(context)
        card.radius = 12f
        card.cardElevation = 2f
        val container = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(16), dp(16), dp(16), dp(16))
        }
        val title = TextView(context).apply {
            text = "Key Bowling Factors"
            setTypeface(typeface, android.graphics.Typeface.BOLD)
            setTextColor(requireContext().getColor(R.color.text_primary))
        }
        container.addView(title)

        fun p(t: String) = TextView(context).apply {
            text = t
            setTextColor(requireContext().getColor(R.color.text_secondary))
        }

        container.addView(p("Quality Wickets: Top-order batsmen, set batsmen"))
        container.addView(p("Match Situations: Breakthroughs, death bowling"))
        container.addView(p("Economy Control: Pressure bowling, dot ball percentage"))
        container.addView(p("Special Performances: 5-wicket hauls, match-winning spells"))
        card.addView(container)
        return card
    }

    private fun buildQuickFacts() {
        val facts = listOf(
            "Update Frequency: Rankings are updated after every international match",
            "Minimum Matches: Teams/players need minimum matches to be eligible for rankings",
            "Rating Range: Team ratings typically range from 0-150, player ratings from 0-1000",
            "Historical Data: Rankings consider performances over 2-4 years depending on format",
            "Separate Systems: Each format (Test, ODI, T20I) has completely separate rankings",
            "ICC Management: All rankings are officially maintained and published by the ICC"
        )

        val grid = binding.gridQuickFacts
        grid.removeAllViews()
        facts.forEach { f ->
            val card = CardView(requireContext()).apply {
                radius = 12f
                cardElevation = 1.5f
            }
            val lp = GridLayout.LayoutParams(
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            )
            lp.setMargins(dp(4), dp(4), dp(4), dp(4))
            card.layoutParams = lp

            val tv = TextView(requireContext()).apply {
                text = f
                setPadding(dp(12), dp(12), dp(12), dp(12))
                setTextColor(requireContext().getColor(R.color.text_secondary))
            }
            card.addView(tv)
            grid.addView(card)
        }
    }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()
}
