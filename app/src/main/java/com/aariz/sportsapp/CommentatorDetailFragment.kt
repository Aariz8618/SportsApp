package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.aariz.sportsapp.models.Commentator

class CommentatorDetailFragment : Fragment() {

    companion object {
        private const val ARG_COMMENTATOR_NAME = "commentator_name"
        private const val ARG_COMMENTATOR_COUNTRY = "commentator_country"
        private const val ARG_COMMENTATOR_IMAGE = "commentator_image"

        fun newInstance(commentator: Commentator): CommentatorDetailFragment {
            val fragment = CommentatorDetailFragment()
            val args = Bundle().apply {
                putString(ARG_COMMENTATOR_NAME, commentator.name)
                putString(ARG_COMMENTATOR_COUNTRY, commentator.country)
                putInt(ARG_COMMENTATOR_IMAGE, commentator.imageResId)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_commentator_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val name = it.getString(ARG_COMMENTATOR_NAME)
            val country = it.getString(ARG_COMMENTATOR_COUNTRY)
            val imageResId = it.getInt(ARG_COMMENTATOR_IMAGE)

            view.findViewById<TextView>(R.id.commentatorName)?.text = name
            view.findViewById<TextView>(R.id.commentatorCountry)?.text = country
            view.findViewById<ImageView>(R.id.commentatorImage)?.setImageResource(imageResId)

            when (name) {
                "Harsha Bhogle" -> {
                    view.findViewById<TextView>(R.id.commentatorBorn)?.text = "July 19, 1961 – Hyderabad, India"
                    view.findViewById<TextView>(R.id.commentatorEducation)?.text = "Osmania University, IIM Ahmedabad"
                    view.findViewById<TextView>(R.id.commentatorOccupation)?.text = "Cricket Commentator, Journalist"
                    view.findViewById<TextView>(R.id.commentatorHighlights)?.text = getString(R.string.bhogle_highlights)
                }
                "Ian Bishop" -> {
                    view.findViewById<TextView>(R.id.commentatorBorn)?.text = "October 24, 1967 – Trinidad and Tobago"
                    view.findViewById<TextView>(R.id.commentatorEducation)?.text = "University of the West Indies"
                    view.findViewById<TextView>(R.id.commentatorOccupation)?.text = "Cricket Commentator, Former Cricketer"
                    view.findViewById<TextView>(R.id.commentatorHighlights)?.text = getString(R.string.bishop_highlights)
                }
                "Nasser Hussain" -> {
                    view.findViewById<TextView>(R.id.commentatorBorn)?.text = "March 28, 1968 – Madras, India"
                    view.findViewById<TextView>(R.id.commentatorEducation)?.text = "Durham University"
                    view.findViewById<TextView>(R.id.commentatorOccupation)?.text = "Cricket Commentator, Former England Captain"
                    view.findViewById<TextView>(R.id.commentatorHighlights)?.text = getString(R.string.nasser_highlights)
                }
                "Michael Holding" -> {
                    view.findViewById<TextView>(R.id.commentatorBorn)?.text = "March 23, 1968 – Manchester, England"
                    view.findViewById<TextView>(R.id.commentatorEducation)?.text = "University of Cambridge"
                    view.findViewById<TextView>(R.id.commentatorOccupation)?.text = "Cricket Commentator, Journalist"
                    view.findViewById<TextView>(R.id.commentatorHighlights)?.text = getString(R.string.holding_highlights)
                }
                "Mark Nicholas" -> {
                    view.findViewById<TextView>(R.id.commentatorBorn)?.text = "September 29, 1957 – Westminster, London"
                    view.findViewById<TextView>(R.id.commentatorEducation)?.text = "Bradfield College"
                    view.findViewById<TextView>(R.id.commentatorOccupation)?.text = "Cricket Commentator, TV Presenter"
                    view.findViewById<TextView>(R.id.commentatorHighlights)?.text = getString(R.string.nicholas_highlights)
                }
                "Simon Doull" -> {
                    view.findViewById<TextView>(R.id.commentatorBorn)?.text = "August 6, 1969 – Pukekohe, New Zealand"
                    view.findViewById<TextView>(R.id.commentatorEducation)?.text = "Local New Zealand Schools"
                    view.findViewById<TextView>(R.id.commentatorOccupation)?.text = "Cricket Commentator, Former Cricketer"
                    view.findViewById<TextView>(R.id.commentatorHighlights)?.text = getString(R.string.doull_highlights)
                }
                "Sanjay Manjrekar" -> {
                    view.findViewById<TextView>(R.id.commentatorBorn)?.text = "July 12, 1965 – Mangalore, India"
                    view.findViewById<TextView>(R.id.commentatorEducation)?.text = "Mumbai University"
                    view.findViewById<TextView>(R.id.commentatorOccupation)?.text = "Cricket Commentator, Former Batsman"
                    view.findViewById<TextView>(R.id.commentatorHighlights)?.text = getString(R.string.manjrekar_highlights)
                }
                "Pommie Mbangwa" -> {
                    view.findViewById<TextView>(R.id.commentatorBorn)?.text = "June 26, 1976 – Bulawayo, Zimbabwe"
                    view.findViewById<TextView>(R.id.commentatorEducation)?.text = "Falcon College"
                    view.findViewById<TextView>(R.id.commentatorOccupation)?.text = "Cricket Commentator, Former Bowler"
                    view.findViewById<TextView>(R.id.commentatorHighlights)?.text = getString(R.string.pommie_highlights)
                }
            }
        }
    }
}
