package com.aariz.sportsapp

import java.io.Serializable

/**
 * Expert-style detail model for binding to fragment_ground_profile.xml
 * Fill this with real data and pass to GroundProfileFragment.newInstance().
 */
data class GroundDetail(
    // Header / quick stats
    val badgeText: String? = null,
    val capacity: String = "",
    val establishedYear: String = "-",
    val matchesCount: String = "-",
    val matchesLabel: String = "Matches",

    // Averages
    val avgTest1st: String = "-",
    val avgTest2nd: String = "-",
    val avgTest3rd: String = "-",
    val avgTest4th: String = "-",
    val avgOdi1st: String = "-",
    val avgOdi2nd: String = "-",
    val avgT201st: String = "-",
    val avgT202nd: String = "-",

    // Pitch / conditions
    val pitchSummary: String = "-",
    val pitchTagPrimary: String = "-",
    val conditionTagPrimary: String = "-",

    // Records (2x2)
    val record1Type: String = "-",
    val record1Value: String = "-",
    val record1Detail: String = "-",

    val record2Type: String = "-",
    val record2Value: String = "-",
    val record2Detail: String = "-",

    val record3Type: String = "-",
    val record3Value: String = "-",
    val record3Detail: String = "-",

    val record4Type: String = "-",
    val record4Value: String = "-",
    val record4Detail: String = "-",

    // Wiki / infobox
    val owner: String = "-",
    val openedYear: String = "-",
    val firstTestDate: String = "-",
    val capacityInfo: String = "-",

    // History paragraphs
    val history1: String = "-",
    val history2: String = "-",
    val history3: String = "-",

    // References footer
    val referencesText: String = "-",

    // Trends
    val trend1: String = "-",
    val trend2: String = "-",
    val trend3: String = "-",
    val trend4: String = "-",

    // Ground records by format
    val testMostRuns: String = "-",
    val testMostWkts: String = "-",
    val odiMostRuns: String = "-",
    val odiMostWkts: String = "-",
    val t20MostRuns: String = "-",
    val t20MostWkts: String = "-",
) : Serializable {
    companion object {
        fun placeholderFor(ground: CricketGround): GroundDetail = GroundDetail(
            capacity = "-",
            establishedYear = "-",
            matchesCount = "-",
            matchesLabel = "Matches",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "-",
            pitchTagPrimary = "-",
            conditionTagPrimary = "-",
            record1Type = "-", record1Value = "-", record1Detail = "-",
            record2Type = "-", record2Value = "-", record2Detail = "-",
            record3Type = "-", record3Value = "-", record3Detail = "-",
            record4Type = "-", record4Value = "-", record4Detail = "-",
            owner = "-", openedYear = "-", firstTestDate = "-", capacityInfo = "-",
            history1 = "-", history2 = "-", history3 = "-",
            referencesText = "-",
            trend1 = "-", trend2 = "-", trend3 = "-", trend4 = "-",
            testMostRuns = "-", testMostWkts = "-",
            odiMostRuns = "-", odiMostWkts = "-",
            t20MostRuns = "-", t20MostWkts = "-",
        )
    }
}
