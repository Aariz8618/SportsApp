package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class UmpireDetailFragment : Fragment() {

    companion object {
        private const val ARG_UMPIRE_NAME = "umpire_name"
        private const val ARG_UMPIRE_COUNTRY = "umpire_country"
        private const val ARG_UMPIRE_IMAGE = "umpire_image"

        fun newInstance(umpire: Umpire): UmpireDetailFragment {
            val fragment = UmpireDetailFragment()
            val args = Bundle().apply {
                putString(ARG_UMPIRE_NAME, umpire.name)
                putString(ARG_UMPIRE_COUNTRY, umpire.country)
                putInt(ARG_UMPIRE_IMAGE, umpire.imageResId)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_umpire_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val name = it.getString(ARG_UMPIRE_NAME)
            val country = it.getString(ARG_UMPIRE_COUNTRY)
            val imageResId = it.getInt(ARG_UMPIRE_IMAGE)

            view.findViewById<TextView>(R.id.umpireName)?.text = name
            view.findViewById<TextView>(R.id.umpireCountry)?.text = country
            view.findViewById<ImageView>(R.id.umpireImage)?.setImageResource(imageResId)

            when (name) {
                "Chris Gaffaney" -> {
                    view.findViewById<TextView>(R.id.umpireBorn)?.text = "December 30, 1975 – Christchurch, New Zealand"
                    view.findViewById<TextView>(R.id.umpireEducation)?.text = "Canterbury University"
                    view.findViewById<TextView>(R.id.umpireOccupation)?.text = "International Cricket Umpire"
                    view.findViewById<TextView>(R.id.umpireHighlights)?.text = "ICC Elite Panel member since 2014. Officiated in multiple World Cups and T20 World Cups."
                }
                "Michael Gough" -> {
                    view.findViewById<TextView>(R.id.umpireBorn)?.text = "December 18, 1979 – Hartlepool, England"
                    view.findViewById<TextView>(R.id.umpireEducation)?.text = "Durham County Cricket"
                    view.findViewById<TextView>(R.id.umpireOccupation)?.text = "International Cricket Umpire"
                    view.findViewById<TextView>(R.id.umpireHighlights)?.text = "ICC Elite Panel member. Known for consistent decision-making in Test and ODI matches."
                }
                "Richard Illingworth" -> {
                    view.findViewById<TextView>(R.id.umpireBorn)?.text = "August 23, 1963 – Bradford, England"
                    view.findViewById<TextView>(R.id.umpireEducation)?.text = "Yorkshire County Cricket"
                    view.findViewById<TextView>(R.id.umpireOccupation)?.text = "International Cricket Umpire, Former Cricketer"
                    view.findViewById<TextView>(R.id.umpireHighlights)?.text = "Former England spinner turned elite umpire. ICC Elite Panel member since 2013."
                }
                "Richard Kettleborough" -> {
                    view.findViewById<TextView>(R.id.umpireBorn)?.text = "March 15, 1973 – Sheffield, England"
                    view.findViewById<TextView>(R.id.umpireEducation)?.text = "Yorkshire County Cricket"
                    view.findViewById<TextView>(R.id.umpireOccupation)?.text = "International Cricket Umpire"
                    view.findViewById<TextView>(R.id.umpireHighlights)?.text = "ICC Umpire of the Year multiple times. Known for accuracy and calm demeanor."
                }
                "Nitin Menon" -> {
                    view.findViewById<TextView>(R.id.umpireBorn)?.text = "November 2, 1983 – Indore, India"
                    view.findViewById<TextView>(R.id.umpireEducation)?.text = "Madhya Pradesh Cricket Association"
                    view.findViewById<TextView>(R.id.umpireOccupation)?.text = "International Cricket Umpire"
                    view.findViewById<TextView>(R.id.umpireHighlights)?.text = "Youngest Indian to join ICC Elite Panel in 2019. Rising star in international umpiring."
                }
                "Ahsan Raza" -> {
                    view.findViewById<TextView>(R.id.umpireBorn)?.text = "March 31, 1971 – Lahore, Pakistan"
                    view.findViewById<TextView>(R.id.umpireEducation)?.text = "Pakistan Cricket Board"
                    view.findViewById<TextView>(R.id.umpireOccupation)?.text = "International Cricket Umpire"
                    view.findViewById<TextView>(R.id.umpireHighlights)?.text = "ICC Elite Panel member. Officiated in Asia Cup and World Cup matches."
                }
                "Paul Reiffel" -> {
                    view.findViewById<TextView>(R.id.umpireBorn)?.text = "April 19, 1966 – Box Hill, Australia"
                    view.findViewById<TextView>(R.id.umpireEducation)?.text = "Cricket Australia"
                    view.findViewById<TextView>(R.id.umpireOccupation)?.text = "International Cricket Umpire, Former Cricketer"
                    view.findViewById<TextView>(R.id.umpireHighlights)?.text = "Former Australian fast bowler. ICC Elite Panel member with extensive experience."
                }
                "Adrian Holdstock" -> {
                    view.findViewById<TextView>(R.id.umpireBorn)?.text = "November 3, 1972 – Durban, South Africa"
                    view.findViewById<TextView>(R.id.umpireEducation)?.text = "Cricket South Africa"
                    view.findViewById<TextView>(R.id.umpireOccupation)?.text = "International Cricket Umpire"
                    view.findViewById<TextView>(R.id.umpireHighlights)?.text = "ICC International Panel member. Experienced in domestic and international cricket."
                }
                "Kumar Dharmasena" -> {
                    view.findViewById<TextView>(R.id.umpireBorn)?.text = "April 24, 1971 – Colombo, Sri Lanka"
                    view.findViewById<TextView>(R.id.umpireEducation)?.text = "Sri Lanka Cricket"
                    view.findViewById<TextView>(R.id.umpireOccupation)?.text = "International Cricket Umpire, Former Cricketer"
                    view.findViewById<TextView>(R.id.umpireHighlights)?.text = "Former Sri Lankan all-rounder. ICC Elite Panel member and World Cup final umpire."
                }
                "Sharfuddoula" -> {
                    view.findViewById<TextView>(R.id.umpireBorn)?.text = "January 15, 1983 – Dhaka, Bangladesh"
                    view.findViewById<TextView>(R.id.umpireEducation)?.text = "Bangladesh Cricket Board"
                    view.findViewById<TextView>(R.id.umpireOccupation)?.text = "International Cricket Umpire"
                    view.findViewById<TextView>(R.id.umpireHighlights)?.text = "ICC International Panel member. First Bangladeshi umpire to officiate in Test matches."
                }
                "Rod Tucker" -> {
                    view.findViewById<TextView>(R.id.umpireBorn)?.text = "February 28, 1964 – Sydney, Australia"
                    view.findViewById<TextView>(R.id.umpireEducation)?.text = "Cricket Australia"
                    view.findViewById<TextView>(R.id.umpireOccupation)?.text = "International Cricket Umpire"
                    view.findViewById<TextView>(R.id.umpireHighlights)?.text = "ICC Elite Panel member since 2010. Officiated in multiple World Cups and Champions Trophy."
                }
                "Joel Wilson" -> {
                    view.findViewById<TextView>(R.id.umpireBorn)?.text = "August 5, 1966 – Trinidad and Tobago"
                    view.findViewById<TextView>(R.id.umpireEducation)?.text = "West Indies Cricket Board"
                    view.findViewById<TextView>(R.id.umpireOccupation)?.text = "International Cricket Umpire"
                    view.findViewById<TextView>(R.id.umpireHighlights)?.text = "ICC Elite Panel member. Experienced in Caribbean and international cricket."
                }
                "Anil Chaudhary" -> {
                    view.findViewById<TextView>(R.id.umpireBorn)?.text = "May 7, 1966 – Delhi, India"
                    view.findViewById<TextView>(R.id.umpireEducation)?.text = "Delhi District Cricket Association"
                    view.findViewById<TextView>(R.id.umpireOccupation)?.text = "International Cricket Umpire"
                    view.findViewById<TextView>(R.id.umpireHighlights)?.text = "ICC International Panel member. Extensive experience in Indian domestic cricket."
                }
                "Simon Taufel" -> {
                    view.findViewById<TextView>(R.id.umpireBorn)?.text = "January 21, 1971 – St Albans, Australia"
                    view.findViewById<TextView>(R.id.umpireEducation)?.text = "Cricket Australia"
                    view.findViewById<TextView>(R.id.umpireOccupation)?.text = "Former International Cricket Umpire, ICC Umpire Performance Manager"
                    view.findViewById<TextView>(R.id.umpireHighlights)?.text = "ICC Umpire of the Year 5 consecutive times (2004-2008). Legendary umpire and mentor."
                }
                "Aleem Dar" -> {
                    view.findViewById<TextView>(R.id.umpireBorn)?.text = "June 6, 1968 – Jhang, Pakistan"
                    view.findViewById<TextView>(R.id.umpireEducation)?.text = "Pakistan Cricket Board"
                    view.findViewById<TextView>(R.id.umpireOccupation)?.text = "International Cricket Umpire"
                    view.findViewById<TextView>(R.id.umpireHighlights)?.text = "ICC Elite Panel member since 2003. Most experienced umpire with 400+ international matches."
                }
                "Steve Bucknor" -> {
                    view.findViewById<TextView>(R.id.umpireBorn)?.text = "May 31, 1946 – Montego Bay, Jamaica"
                    view.findViewById<TextView>(R.id.umpireEducation)?.text = "University of the West Indies"
                    view.findViewById<TextView>(R.id.umpireOccupation)?.text = "Retired International Cricket Umpire"
                    view.findViewById<TextView>(R.id.umpireHighlights)?.text = "Legendary umpire with 128 Tests and 181 ODIs. Known for distinctive style and longevity."
                }
                "Brian Jerling" -> {
                    view.findViewById<TextView>(R.id.umpireBorn)?.text = "September 12, 1963 – Cape Town, South Africa"
                    view.findViewById<TextView>(R.id.umpireEducation)?.text = "Cricket South Africa"
                    view.findViewById<TextView>(R.id.umpireOccupation)?.text = "International Cricket Umpire"
                    view.findViewById<TextView>(R.id.umpireHighlights)?.text = "ICC International Panel member. Experienced in South African domestic and international cricket."
                }
                "Bruce Oxenford" -> {
                    view.findViewById<TextView>(R.id.umpireBorn)?.text = "June 5, 1960 – Queensland, Australia"
                    view.findViewById<TextView>(R.id.umpireEducation)?.text = "Cricket Australia"
                    view.findViewById<TextView>(R.id.umpireOccupation)?.text = "Retired International Cricket Umpire"
                    view.findViewById<TextView>(R.id.umpireHighlights)?.text = "Former ICC Elite Panel member. Known for his distinctive hat and calm decision-making."
                }
            }
        }
    }
}
