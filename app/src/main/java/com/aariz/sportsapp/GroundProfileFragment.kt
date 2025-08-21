package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class GroundProfileFragment : Fragment() {

    private var name: String? = null
    private var location: String? = null
    private var imageResId: Int = 0
    private var detail: GroundDetail? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            name = args.getString(ARG_NAME)
            location = args.getString(ARG_LOCATION)
            imageResId = args.getInt(ARG_IMAGE_RES)
            detail = args.getSerializable(ARG_DETAIL) as? GroundDetail
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ground_profile, container, false)

        // Header
        view.findViewById<TextView>(R.id.tvGroundName)?.text = name ?: ""
        view.findViewById<TextView>(R.id.tvLocation)?.text = location ?: ""
        view.findViewById<ImageView>(R.id.ivHeroImage)?.apply { if (imageResId != 0) setImageResource(imageResId) }

        // Quick stats
        view.findViewById<TextView>(R.id.tvCapacity)?.text = detail?.capacity ?: "-"
        view.findViewById<TextView>(R.id.tvEstablished)?.text = detail?.establishedYear ?: "-"
        view.findViewById<TextView>(R.id.tvMatches)?.text = detail?.matchesCount ?: "-"
        view.findViewById<TextView>(R.id.tvMatchesLabel)?.text = detail?.matchesLabel ?: "Matches"

        // Averages (match XML labels)
        view.findViewById<TextView>(R.id.tvAvgTestLabel)?.text = "Test:"
        view.findViewById<TextView>(R.id.tvAvgTestRightLine1)?.text =
            "1st inn ${detail?.avgTest1st ?: "-"} | 2nd inn ${detail?.avgTest2nd ?: "-"}"
        view.findViewById<TextView>(R.id.tvAvgTestRightLine2)?.text =
            "3rd inn ${detail?.avgTest3rd ?: "-"} | 4th inn ${detail?.avgTest4th ?: "-"}"
        view.findViewById<TextView>(R.id.tvAvgOdi)?.text =
            "ODI: 1st inn ${detail?.avgOdi1st ?: "-"} | 2nd inn ${detail?.avgOdi2nd ?: "-"}"
        view.findViewById<TextView>(R.id.tvAvgT20)?.text =
            "T20I: 1st inn ${detail?.avgT201st ?: "-"} | 2nd inn ${detail?.avgT202nd ?: "-"}"

        // Pitch
        view.findViewById<TextView>(R.id.tvPitchSummary)?.text = detail?.pitchSummary ?: "-"
        view.findViewById<TextView>(R.id.tagPitchPrimary)?.text = detail?.pitchTagPrimary ?: "-"
        view.findViewById<TextView>(R.id.tagConditionPrimary)?.text = detail?.conditionTagPrimary ?: "-"

        // Records
        view.findViewById<TextView>(R.id.tvRecord1Type)?.text = detail?.record1Type ?: "-"
        view.findViewById<TextView>(R.id.tvRecord1Value)?.text = detail?.record1Value ?: "-"
        view.findViewById<TextView>(R.id.tvRecord1Detail)?.text = detail?.record1Detail ?: "-"

        view.findViewById<TextView>(R.id.tvRecord2Type)?.text = detail?.record2Type ?: "-"
        view.findViewById<TextView>(R.id.tvRecord2Value)?.text = detail?.record2Value ?: "-"
        view.findViewById<TextView>(R.id.tvRecord2Detail)?.text = detail?.record2Detail ?: "-"

        view.findViewById<TextView>(R.id.tvRecord3Type)?.text = detail?.record3Type ?: "-"
        view.findViewById<TextView>(R.id.tvRecord3Value)?.text = detail?.record3Value ?: "-"
        view.findViewById<TextView>(R.id.tvRecord3Detail)?.text = detail?.record3Detail ?: "-"

        view.findViewById<TextView>(R.id.tvRecord4Type)?.text = detail?.record4Type ?: "-"
        view.findViewById<TextView>(R.id.tvRecord4Value)?.text = detail?.record4Value ?: "-"
        view.findViewById<TextView>(R.id.tvRecord4Detail)?.text = detail?.record4Detail ?: "-"

        // Infobox
        view.findViewById<TextView>(R.id.tvOwner)?.text = detail?.owner ?: "-"
        view.findViewById<TextView>(R.id.tvOpenedYear)?.text = detail?.openedYear ?: "-"
        view.findViewById<TextView>(R.id.tvFirstTest)?.text = detail?.firstTestDate ?: "-"
        view.findViewById<TextView>(R.id.tvCapacityInfo)?.text = detail?.capacityInfo ?: "-"

        // History
        view.findViewById<TextView>(R.id.tvHistory1)?.text = detail?.history1 ?: "-"
        view.findViewById<TextView>(R.id.tvHistory2)?.text = detail?.history2 ?: "-"
        view.findViewById<TextView>(R.id.tvHistory3)?.text = detail?.history3 ?: "-"
        view.findViewById<TextView>(R.id.tvReferences)?.text = detail?.referencesText ?: "-"

        // Trends removed in new UI (bindings deleted)

        // Ground Records by Format
        view.findViewById<TextView>(R.id.tvTestMostRuns)?.text = "Most runs: ${detail?.testMostRuns ?: "-"}"
        view.findViewById<TextView>(R.id.tvTestMostWkts)?.text = "Most wkts: ${detail?.testMostWkts ?: "-"}"
        view.findViewById<TextView>(R.id.tvOdiMostRuns)?.text = "Most runs: ${detail?.odiMostRuns ?: "-"}"
        view.findViewById<TextView>(R.id.tvOdiMostWkts)?.text = "Most wkts: ${detail?.odiMostWkts ?: "-"}"
        view.findViewById<TextView>(R.id.tvT20MostRuns)?.text = "Most runs: ${detail?.t20MostRuns ?: "-"}"
        view.findViewById<TextView>(R.id.tvT20MostWkts)?.text = "Most wkts: ${detail?.t20MostWkts ?: "-"}"

        return view
    }

    companion object {
        private const val ARG_NAME = "arg_name"
        private const val ARG_LOCATION = "arg_location"
        private const val ARG_IMAGE_RES = "arg_image_res"
        private const val ARG_DETAIL = "arg_detail"

        fun newInstance(ground: CricketGround, detail: GroundDetail): GroundProfileFragment = GroundProfileFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_NAME, ground.name)
                putString(ARG_LOCATION, ground.location)
                putInt(ARG_IMAGE_RES, ground.imageResId)
                putSerializable(ARG_DETAIL, detail)
            }
        }
    }
}
