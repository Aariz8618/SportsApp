package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.aariz.sportsapp.models.Expert

class ExpertDetailFragment : Fragment() {

    companion object {
        private const val ARG_EXPERT_NAME = "expert_name"
        private const val ARG_EXPERT_SPECIALIZATION = "expert_specialization"
        private const val ARG_EXPERT_IMAGE = "expert_image"

        fun newInstance(expert: Expert): ExpertDetailFragment {
            val fragment = ExpertDetailFragment()
            val args = Bundle().apply {
                putString(ARG_EXPERT_NAME, expert.name)
                putString(ARG_EXPERT_SPECIALIZATION, expert.specialization)
                putInt(ARG_EXPERT_IMAGE, expert.imageResId)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_expert_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val name = it.getString(ARG_EXPERT_NAME)
            val specialization = it.getString(ARG_EXPERT_SPECIALIZATION)
            val imageResId = it.getInt(ARG_EXPERT_IMAGE)

            view.findViewById<TextView>(R.id.expertName)?.text = name
            view.findViewById<TextView>(R.id.expertSpecialization)?.text = specialization
            view.findViewById<ImageView>(R.id.expertImage)?.setImageResource(imageResId)

            when (name) {
                "Sachin Tendulkar" -> {
                    view.findViewById<TextView>(R.id.expertBorn)?.text = getString(R.string.sachin_born)
                    view.findViewById<TextView>(R.id.expertEducation)?.text = getString(R.string.sachin_education)
                    view.findViewById<TextView>(R.id.expertOccupation)?.text = getString(R.string.sachin_occupation)
                    view.findViewById<TextView>(R.id.expertHighlights)?.text = getString(R.string.sachin_highlights)
                }
                "Shane Warne" -> {
                    view.findViewById<TextView>(R.id.expertBorn)?.text = getString(R.string.shane_born)
                    view.findViewById<TextView>(R.id.expertEducation)?.text = getString(R.string.shane_education)
                    view.findViewById<TextView>(R.id.expertOccupation)?.text = getString(R.string.shane_occupation)
                    view.findViewById<TextView>(R.id.expertHighlights)?.text = getString(R.string.shane_highlights)
                }
                "Ricky Ponting" -> {
                    view.findViewById<TextView>(R.id.expertBorn)?.text = getString(R.string.ricky_born)
                    view.findViewById<TextView>(R.id.expertEducation)?.text = getString(R.string.ricky_education)
                    view.findViewById<TextView>(R.id.expertOccupation)?.text = getString(R.string.ricky_occupation)
                    view.findViewById<TextView>(R.id.expertHighlights)?.text = getString(R.string.ricky_highlights)
                }
                "Brian Lara" -> {
                    view.findViewById<TextView>(R.id.expertBorn)?.text = getString(R.string.brian_born)
                    view.findViewById<TextView>(R.id.expertEducation)?.text = getString(R.string.brian_education)
                    view.findViewById<TextView>(R.id.expertOccupation)?.text = getString(R.string.brian_occupation)
                    view.findViewById<TextView>(R.id.expertHighlights)?.text = getString(R.string.brian_highlights)
                }
                "Wasim Akram" -> {
                    view.findViewById<TextView>(R.id.expertBorn)?.text = getString(R.string.wasim_born)
                    view.findViewById<TextView>(R.id.expertEducation)?.text = getString(R.string.wasim_education)
                    view.findViewById<TextView>(R.id.expertOccupation)?.text = getString(R.string.wasim_occupation)
                    view.findViewById<TextView>(R.id.expertHighlights)?.text = getString(R.string.wasim_highlights)
                }
                "Jacques Kallis" -> {
                    view.findViewById<TextView>(R.id.expertBorn)?.text = getString(R.string.jacques_born)
                    view.findViewById<TextView>(R.id.expertEducation)?.text = getString(R.string.jacques_education)
                    view.findViewById<TextView>(R.id.expertOccupation)?.text = getString(R.string.jacques_occupation)
                    view.findViewById<TextView>(R.id.expertHighlights)?.text = getString(R.string.jacques_highlights)
                }
                "Adam Gilchrist" -> {
                    view.findViewById<TextView>(R.id.expertBorn)?.text = getString(R.string.adam_born)
                    view.findViewById<TextView>(R.id.expertEducation)?.text = getString(R.string.adam_education)
                    view.findViewById<TextView>(R.id.expertOccupation)?.text = getString(R.string.adam_occupation)
                    view.findViewById<TextView>(R.id.expertHighlights)?.text = getString(R.string.adam_highlights)
                }
                "Muttiah Muralitharan" -> {
                    view.findViewById<TextView>(R.id.expertBorn)?.text = getString(R.string.murali_born)
                    view.findViewById<TextView>(R.id.expertEducation)?.text = getString(R.string.murali_education)
                    view.findViewById<TextView>(R.id.expertOccupation)?.text = getString(R.string.murali_occupation)
                    view.findViewById<TextView>(R.id.expertHighlights)?.text = getString(R.string.murali_highlights)
                }
            }
        }
    }
}

