package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aariz.sportsapp.databinding.FragmentNewsBinding
import android.widget.LinearLayout
import android.widget.TextView
import com.aariz.sportsapp.R

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    data class NewsItem(
        val id: Int,
        val heading: String,
        val category: String,
        val time: String,
        val location: String,
        val image: String,
        val content: String,

        val headerImageName: String
    )

    private val newsData by lazy {
        listOf(
            NewsItem(
                id = 1,
                heading = "Openers give Saint Lucia Kings dominant win",
                category = "CPL 2025",
                time = "2 hours ago",
                location = "Caribbean",
                image = "🏏",

                headerImageName = "news_kings",
                content = """Saint Lucia Kings blazed to victory over the St Kitts and Nevis Patriots in commanding fashion to go top of the table in the Caribbean Premier League, 2025. In the chase of 178, Tim Seifert (68 off 45) and Johnson Charles (47 off 17) gave the Kings the ideal foundation to complete the chase with ease. Earlier, it was Tabraiz Shamsi who ended with figures of 4-0-17-2, another star performer in Kings' win.

On a good pitch to bat on, the Patriots didn't get off to a flier but were circumspect in their approach with timely boundaries scored by Andre Fletcher. First, off Khary Pierre before hitting four in a row off Keon Gaston but his partner Evin Lewis fell for 18 in the last over of the PowerPlay. Usually, under fire for his strike-rate Mohammad Rizwan hit 10 runs off his first three deliveries in a clear signal of intent.

Fletcher found the stands after the PowerPlay but was dismissed off Shamsi's first ball of the night. The scoring rate slowed down as the troika of spinners - Shamsi, Roston Chase and Pierre - kept it very tight. But, Oshane Thomas' struggles in CPL 2025 continued as he conceded 22 runs in his first over which included two sixes, a four and four wides. Kyle Mayers, who had struggled all night up to this point, bludgeoned two sixes and scored a timely boundary to finally find some momentum. He didn't last long though as he fell in the next over to Shamsi but Rizwan moved to 50 off 35 balls.

Jason Holder powered two sixes while Rizwan added another before a leaky final over from Gaston yielded 15 runs for the Patriots. The Kings bowlers were once again at fault for bowling too many extras in a 15-run final over as the Patriots ended on 177 for 3.

The pitch got better to bat on as the night wore on which fit perfectly for Seifert and Charles. The openers were aggressive from ball one, especially Charles who smashed 18 runs off Mayers' second over. Seifert joined in the action against Navian Bidaisee in the fifth over going 6,4,6 before Charles rounded off the over with a boundary. Charles continued to motor on with boundaries as the Kings ended the PowerPlay on 72 for 0.

The Kings onslaught continued with Waqar Salamkheil hit for back-to-back sixes by Charles. The batter tried to muscle one six too many as he fell against the run of play for 47. Ackeem Auguste on the back of a 73 in the last game smashed his fifth delivery for six after the wicket. Seifert continued to pick up boundaries as the Kings moved past 100 with the Kiwi opener bringing up fifty. Auguste, who was playing the perfect foil for Seifert, hit back-to-back boundaries off Holder before adding a third off Bidaisee in the next over to bring up the 50-run stand between the two. Auguste and Seifert departed within two overs of each other but only after the equation was brought down to 22 off the last five overs.

Tim David and Chase then found the boundary twice to seal a comfortable win for the Kings. Meanwhile, the Patriots have now suffered their fifth loss of the season in seven games."""
            ),
            NewsItem(
                id = 2,
                heading = "PNG player charged with robbery during CWC Challenge League tournament",
                category = "International",
                time = "6 hours ago",
                location = "Jersey",
                image = "⚖️",
                headerImageName = "news_png",
                content = """Papua New Guinea player Kipling Doriga has been charged with robbery following an incident in the early hours of Monday morning (25th August) in St Heliers, the capital of Jersey. Doriga was a member of the PNG squad contesting the second round of the ongoing CWC Challenge League on the Island, a crowd dependency of the UK.

The 29 year-old keeper-batter has made 97 appearances for PNG, including at the 2021 and 2024 T20 World Cups.

Doriga appeared before the Magistate's court on Wednesday morning, and is understood to have pled guilty to the charges. Relief Magistrate Rebecca Morley-Kirk judged the charge too serious for Magistrate's court and referred the case to Royal Court, where Doriga is set to appear on the 28th of November. Bail was declined and Doriga will be remanded in custody on the island until that date."""
            ),
            NewsItem(
                id = 3,
                heading = "Injured Ervine ruled out of Sri Lanka ODIs",
                category = "International",
                time = "8 hours ago",
                location = "Harare",
                image = "🏥",
                headerImageName = "news_ervine",
                content = """Zimbabwe captain Craig Ervine has been ruled out of the two-match ODI series against Sri Lanka due to a calf injury. In Ervine's absence, Sean Williams will lead Zimbabwe in the ODI series which begins today. Zimbabwe are yet to announce any replacement.

Ervine suffered a grade 2 strain in his left calf along with a chronic and resolving Grade I strain in his right calf on the eve of the match at the Harare Sports Club. It was confirmed by an MRI scan on Thursday (August 28).

Zimbabwe and Sri Lanka are slated to play two ODIs and three T20Is from August 29 to September 7. All five games will be played in Harare."""
            ),
            NewsItem(
                id = 4,
                heading = "Hasaranga included in SL squad for Asia Cup",
                category = "Asia Cup",
                time = "12 hours ago",
                location = "Sri Lanka",
                image = "🏆",
                headerImageName = "news_hasaranga",
                content = """Sri Lanka have named Wanindu Hasaranga in their 16-member squad for Asia Cup T20 2025, though the leg-spinning allrounder's participation is subject to fitness clearance. Hasaranga has been sidelined since picking up a hamstring injury during the ODI series against Bangladesh in July, which subsequently ruled him out of the T20I series against them and both the ODI and T20I squads for the tour of Zimbabwe."""
            ),
            NewsItem(
                id = 5,
                heading = "It's a long way from Breetzke to Stubbs",
                category = "Analysis",
                time = "1 day ago",
                location = "South Africa",
                image = "📊",
                headerImageName = "news_breetzke_stubbs",
                content = """Matthew Breetzke scored more runs in his last five innings than Tristan Stubbs did in his last 15. These things happen; players slide into and out of form throughout their careers.

But here's a stat to startle - Stubbs, who is among the most athletic and electrifying fielders in world cricket, has dropped six catches in his last five games.

What's up with him?

"These guys play a lot of cricket, especially the ones who play all three formats," Ashwell Prince, South Africa's batting coach, told a press conference on Thursday (August 28). "Sometimes you can get a little bit clouded in terms of your approach and how to go about things.

"Stubbs first came onto the domestic scene as predominantly a white-ball player. We've always felt that he's a good batter, not just a good white-ball batter, and that he can play all the formats.

"When you're dipping in between formats and you have different approaches, sometimes you're in a white-ball series where you want to play a more natural game and maybe your mindset is not as free as you would like it to be.

"I think Tristan is probably in that mental space at the moment. We're having the conversations to try and encourage him to be a little bit freer, to be a little bit more positive, to be the guy who burst onto the scene."

Doubtless Prince's conversations with Breetzke are starkly different.

"When you have a bit of form, your confidence is up. There's a lot more clarity in what you want to do.

"I've been impressed with the intensity of his training. We were joking with him in training before the ODI series that he's got to put that bat on ice because it was smashing it. It's great to see a player transform that sort of training form into how he plays in matches. and go and play."""
            )
        )
    }

    private fun applyAlpha(color: Int, alpha: Float): Int {
        val a = (android.graphics.Color.alpha(color) * alpha).toInt()
        val r = android.graphics.Color.red(color)
        val g = android.graphics.Color.green(color)
        val b = android.graphics.Color.blue(color)
        return android.graphics.Color.argb(a, r, g, b)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        setupBack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupList() {
        val container: LinearLayout = binding.newsListContainer
        container.removeAllViews()

        val inflater = LayoutInflater.from(requireContext())
        newsData.forEach { item ->
            val card = inflater.inflate(R.layout.item_news_card, container, false)

            val header = card.findViewById<View>(R.id.card_header)
            val headerImage = card.findViewById<android.widget.ImageView>(R.id.card_header_image)
            val headerScrim = card.findViewById<View>(R.id.card_header_scrim)
            val emoji = card.findViewById<TextView>(R.id.card_emoji)
            val category = card.findViewById<TextView>(R.id.card_category)
            val heading = card.findViewById<TextView>(R.id.card_heading)
            val time = card.findViewById<TextView>(R.id.card_time)
            val location = card.findViewById<TextView>(R.id.card_location)
            val excerpt = card.findViewById<TextView>(R.id.card_excerpt)
            val readMore = card.findViewById<TextView>(R.id.card_read_more)

            // Load header image by name; fallback to placeholder
            val resId = resources.getIdentifier(item.headerImageName, "drawable", requireContext().packageName)
            if (resId != 0) headerImage.setImageResource(resId) else headerImage.setImageResource(R.drawable.news_header_placeholder)
            // Tint scrim with item color at ~60% alpha


            emoji.text = item.image
            category.text = item.category
            heading.text = item.heading
            time.text = item.time
            location.text = item.location
            val short = if (item.content.length > 120) item.content.substring(0, 120) + "..." else item.content
            excerpt.text = short

            val onOpen = View.OnClickListener { showDetail(item) }
            card.setOnClickListener(onOpen)
            readMore.setOnClickListener(onOpen)

            container.addView(card)
        }
        binding.newsListContainer.visibility = View.VISIBLE
        binding.newsDetailContainer.visibility = View.GONE
    }

    private fun setupBack() {
        binding.btnBack.setOnClickListener {
            binding.newsDetailContainer.visibility = View.GONE
            binding.newsListContainer.visibility = View.VISIBLE
        }
    }

    private fun showDetail(item: NewsItem) {
        // Header
        binding.detailEmoji.text = item.image
        binding.detailCategory.text = item.category
        binding.detailHeading.text = item.heading
        binding.detailMeta.text = "${item.time} • ${item.location}"
        // Load header image in detail
        run {
            val resId = resources.getIdentifier(item.headerImageName, "drawable", requireContext().packageName)
            if (resId != 0) binding.detailHeaderImage.setImageResource(resId)
            else binding.detailHeaderImage.setImageResource(R.drawable.news_header_placeholder)
        }

        // Content
        binding.detailContent.text = item.content

        // Toggle
        binding.newsListContainer.visibility = View.GONE
        binding.newsDetailContainer.visibility = View.VISIBLE
    }
}