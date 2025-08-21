package com.aariz.sportsapp.data

import com.aariz.sportsapp.CricketGround
import com.aariz.sportsapp.GroundDetail

object GroundRepo {
    fun detailFor(ground: CricketGround): GroundDetail = when (ground.name.trim()) {
        //sydney
        "Sydney Cricket Ground" -> GroundDetail(
            capacity = "48,000",
            establishedYear = "1848",
            matchesCount = "125",
            matchesLabel = "Tests",
            avgTest1st = "285", avgTest2nd = "220", avgTest3rd = "210", avgTest4th = "180",
            avgOdi1st = "265", avgOdi2nd = "240",
            avgT201st = "165", avgT202nd = "155",
            pitchSummary = "Traditionally spin‑friendly; pitch built on Bulli soil from Bulli, NSW. One of only two major Test venues in Australia (with the Gabba) not to use a drop‑in pitch.",
            pitchTagPrimary = "Spin-friendly",
            conditionTagPrimary = "Natural (no drop-in)",
            record1Type = "Highest Test total", record1Value = "705/7d", record1Detail = "AUS vs PAK, 2016",
            record2Type = "Lowest Test total",  record2Value = "42",      record2Detail = "NZW vs AUS, 1888",
            record3Type = "Best BBI", record3Value = "8/35", record3Detail = "GA Lohmann vs AUS, 1987",
            record4Type = "Highest Score", record4Value = "329*", record4Detail = "MJ.Clark vs IND, 2012",
            owner = "SCG Trust",
            openedYear = "1848",
            firstTestDate = "1882",
            capacityInfo = "~48k",
            history1 = "Cricket at the SCG dates to the mid‑1800s. The first recorded match on the then Garrison Ground was in 1854. NSW and Victoria used it for practice in 1860–61 before inter‑colonial games. After the Albert Ground closed in the late 1870s, the NSWCA moved to the Association Ground; the first first‑class match there was NSW vs Victoria, Feb 1878 (NSW won by 1 wicket).",
            history2 = "During Lord Harris’ 1878–79 tour, a riot erupted at the SCG in 1879 after an umpiring decision by George Coulthard. By the first Test at the SCG (17–21 Feb 1882), curator Ned Gregory had the ground in fine condition; Australia chased 197 and 169/5 to win. Reg ‘Tip’ Foster’s 287 (1903) was the SCG’s Test high for over a century until Michael Clarke’s 329* in the 100th SCG Test (Jan 2012).",
            history3 = "The ground has hosted many notable moments: Don Bradman watched his first SCG Test in 1920–21 and later made 452* in 1928–29 (NSW vs Qld). Night cricket debuted 28 Nov 1978 (World Series Cricket). Shane Warne played his first (1992) and final (2007) Tests here. The venue hosts the annual New Year ‘Pink Test’. On 25 Nov 2014, Phillip Hughes was struck by a bouncer at the SCG and later died on 27 Nov. In 2019, Australia’s win over India at the SCG marked their 1,000th international victory.",
            referencesText = "Sources: Wikipedia (Sydney Cricket Ground)",
            testMostRuns = "RT Ponting (AUS) -1480 , Avg: 67.27",
            testMostWkts = "SK Warne- 64 , Inn-26 ,Avg: 28.12",
            odiMostRuns = "AR Border (AUS) -1561 , Avg: 28.90",
            odiMostWkts = "GD McGrath -50 , Avg: 18.30 , Eco:3.74",
            t20MostRuns = "V Kohli -298, Avg:99.43 ,SR: 144.60",
            t20MostWkts = "A Zampa-11 , Avg:21.09 , Eco:7.03",
        )

        // Melbourne
        "Melbourne Cricket Ground" -> GroundDetail(
            capacity = "100,024",
            establishedYear = "1853",
            matchesCount = "-",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "-",
            pitchTagPrimary = "Balanced",
            conditionTagPrimary = "-",
            owner = "-", openedYear = "1853", firstTestDate = "-", capacityInfo = "-",
            history1 = "-", referencesText = "-"
        )
// lords
        "Lord's" -> GroundDetail(
            capacity = "31,100",
            establishedYear = "1814",
            matchesCount = "143",
            matchesLabel = "Tests",
            avgTest1st = "310", avgTest2nd = "290", avgTest3rd = "270", avgTest4th = "220",
            avgOdi1st = "270", avgOdi2nd = "250",
            avgT201st = "160", avgT202nd = "150",
            pitchSummary = "Early seam movement; evens out later",
            pitchTagPrimary = "Seam",
            conditionTagPrimary = "Overcast",
            record1Type = "Highest Test total", record1Value = "729/6d", record1Detail = "AUS vs ENG, 1930",
            record2Type = "Lowest Test total",  record2Value = "38",       record2Detail = "IRE vs ENG, 2019",
            owner = "Marylebone Cricket Club",
            openedYear = "1814",
            firstTestDate = "1884",
            capacityInfo = "~31k for cricket",
            history1 = "Known as the Home of Cricket.",
            referencesText = "Wikipedia; ESPNcricinfo"
        )


        // Kennington Oval (The Oval)
        "Kennington Oval" -> GroundDetail(
            capacity = "27,500",
            establishedYear = "1845",
            matchesCount = "-",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "-",
            pitchTagPrimary = "-",
            conditionTagPrimary = "-",
            owner = "Surrey CCC", openedYear = "1845", firstTestDate = "1880", capacityInfo = "-",
            history1 = "-", referencesText = "-"
        )

        // Adelaide Oval
        "Adelaide Oval" -> GroundDetail(
            capacity = "53,500",
            establishedYear = "1871",
            matchesCount = "-",
            matchesLabel = "Tests",
            avgTest1st = "285", avgTest2nd = "220", avgTest3rd = "210", avgTest4th = "180",
            avgOdi1st = "265", avgOdi2nd = "240",
            avgT201st = "165", avgT202nd = "155",
            pitchSummary = "Balanced; aids batting early, spin later",
            pitchTagPrimary = "Balanced",
            conditionTagPrimary = "Dry",
            owner = "SACA", openedYear = "1871", firstTestDate = "1884", capacityInfo = "~53.5k",
            history1 = "Historic day-night Test venue.", referencesText = "Wikipedia; ESPNcricinfo"
        )
          // wankhede
        "Wankhede Stadium" -> GroundDetail(
            capacity = "33,108",
            establishedYear = "1974",
            matchesCount = "42",
            matchesLabel = "Tests",
            avgTest1st = "320", avgTest2nd = "290", avgTest3rd = "260", avgTest4th = "210",
            avgOdi1st = "275", avgOdi2nd = "250",
            avgT201st = "170", avgT202nd = "160",
            pitchSummary = "Flat with bounce; assists spin later",
            pitchTagPrimary = "Batting",
            conditionTagPrimary = "Humid Coastal",
            record1Type = "Highest Test total", record1Value = "631", record1Detail = "IND vs ENG, 2016",
            record2Type = "Lowest Test total",  record2Value = "88",  record2Detail = "ENG vs IND, 1981",
            owner = "Mumbai Cricket Association",
            openedYear = "1974",
            firstTestDate = "1975",
            capacityInfo = "~33k for cricket",
            history1 = "Hosted 2011 ODI World Cup Final.",
            referencesText = "Wikipedia; ESPNcricinfo"
        )

        // Eden Gardens
        "Eden Gardens" -> GroundDetail(
            capacity = "68,000",
            establishedYear = "1864",
            matchesCount = "-",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "-",
            pitchTagPrimary = "-",
            conditionTagPrimary = "-",
            owner = "CAB", openedYear = "1864", firstTestDate = "1934", capacityInfo = "-",
            history1 = "-", referencesText = "-"
        )

        // M. Chinnaswamy Stadium
        "Chinnaswamy Stadium" -> GroundDetail(
            capacity = "35,000",
            establishedYear = "1969",
            matchesCount = "-",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "-",
            pitchTagPrimary = "-",
            conditionTagPrimary = "-",
            owner = "KSCA", openedYear = "1969", firstTestDate = "1974", capacityInfo = "-",
            history1 = "-", referencesText = "-"
        )

        // Narendra Modi Stadium
        "Narendra Modi Stadium" -> GroundDetail(
            capacity = "132,000",
            establishedYear = "1982",
            matchesCount = "-",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "-",
            pitchTagPrimary = "-",
            conditionTagPrimary = "-",
            owner = "GCA", openedYear = "1982", firstTestDate = "1983", capacityInfo = "-",
            history1 = "-", referencesText = "-"
        )

        // HPCA Stadium, Dharamshala
        "Himachal Pradesh Cricket Association Stadium" -> GroundDetail(
            capacity = "23,000",
            establishedYear = "2003",
            matchesCount = "-",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "-",
            pitchTagPrimary = "-",
            conditionTagPrimary = "-",
            owner = "HPCA", openedYear = "2003", firstTestDate = "2017", capacityInfo = "-",
            history1 = "-", referencesText = "-"
        )

        // Headingley
        "Headingley" -> GroundDetail(
            capacity = "18,350",
            establishedYear = "1890",
            matchesCount = "-",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "-",
            pitchTagPrimary = "-",
            conditionTagPrimary = "-",
            owner = "Yorkshire CCC", openedYear = "1890", firstTestDate = "1899", capacityInfo = "-",
            history1 = "-", referencesText = "-"
        )

        // Old Trafford
        "Old Trafford" -> GroundDetail(
            capacity = "26,000",
            establishedYear = "1857",
            matchesCount = "-",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "-",
            pitchTagPrimary = "-",
            conditionTagPrimary = "-",
            owner = "Lancashire CCC", openedYear = "1857", firstTestDate = "1884", capacityInfo = "-",
            history1 = "-", referencesText = "-"
        )

        // Edgbaston
        "Edgbaston" -> GroundDetail(
            capacity = "25,000",
            establishedYear = "1882",
            matchesCount = "-",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "-",
            pitchTagPrimary = "-",
            conditionTagPrimary = "-",
            owner = "Warwickshire CCC", openedYear = "1882", firstTestDate = "1902", capacityInfo = "-",
            history1 = "-", referencesText = "-"
        )

        // Perth Stadium (Optus)
        "Perth Stadium" -> GroundDetail(
            capacity = "61,266",
            establishedYear = "2018",
            matchesCount = "-",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "-",
            pitchTagPrimary = "-",
            conditionTagPrimary = "-",
            owner = "-", openedYear = "2018", firstTestDate = "2018", capacityInfo = "-",
            history1 = "-", referencesText = "-"
        )

        // Brisbane Cricket Ground (The Gabba)
        "Brisbane Cricket Stadium" -> GroundDetail(
            capacity = "42,000",
            establishedYear = "1895",
            matchesCount = "-",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "-",
            pitchTagPrimary = "-",
            conditionTagPrimary = "-",
            owner = "-", openedYear = "1895", firstTestDate = "1931", capacityInfo = "-",
            history1 = "-", referencesText = "-"
        )

        // Wanderers, Johannesburg
        "The Wanderers Stadium" -> GroundDetail(
            capacity = "34,000",
            establishedYear = "1956",
            matchesCount = "-",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "-",
            pitchTagPrimary = "-",
            conditionTagPrimary = "-",
            owner = "-", openedYear = "1956", firstTestDate = "1956", capacityInfo = "-",
            history1 = "-", referencesText = "-"
        )

        // SuperSport Park, Centurion
        "SuperSport Park" -> GroundDetail(
            capacity = "22,000",
            establishedYear = "1986",
            matchesCount = "-",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "-",
            pitchTagPrimary = "-",
            conditionTagPrimary = "-",
            owner = "-", openedYear = "1986", firstTestDate = "1995", capacityInfo = "-",
            history1 = "-", referencesText = "-"
        )

        // Kingsmead, Durban
        "Kingsmead" -> GroundDetail(
            capacity = "25,000",
            establishedYear = "1923",
            matchesCount = "-",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "-",
            pitchTagPrimary = "-",
            conditionTagPrimary = "-",
            owner = "-", openedYear = "1923", firstTestDate = "1923", capacityInfo = "-",
            history1 = "-", referencesText = "-"
        )

        // Newlands, Cape Town
        "Newlands" -> GroundDetail(
            capacity = "25,000",
            establishedYear = "1888",
            matchesCount = "-",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "-",
            pitchTagPrimary = "-",
            conditionTagPrimary = "-",
            owner = "-", openedYear = "1888", firstTestDate = "1889", capacityInfo = "-",
            history1 = "-", referencesText = "-"
        )

        // Boland Park, Paarl
        "Boland Park" -> GroundDetail(
            capacity = "10,000",
            establishedYear = "1996",
            matchesCount = "-",
            matchesLabel = "ODIs",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "-",
            pitchTagPrimary = "-",
            conditionTagPrimary = "-",
            owner = "-", openedYear = "1996", firstTestDate = "-", capacityInfo = "-",
            history1 = "-", referencesText = "-"
        )

        // Hagley Oval, Christchurch
        "Hagley Oval" -> GroundDetail(
            capacity = "18,000",
            establishedYear = "1867",
            matchesCount = "-",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "-",
            pitchTagPrimary = "-",
            conditionTagPrimary = "-",
            owner = "-", openedYear = "1867", firstTestDate = "2014", capacityInfo = "-",
            history1 = "-", referencesText = "-"
        )

        // Eden Park, Auckland
        "Eden Park" -> GroundDetail(
            capacity = "50,000",
            establishedYear = "1900",
            matchesCount = "-",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "-",
            pitchTagPrimary = "-",
            conditionTagPrimary = "-",
            owner = "-", openedYear = "1900", firstTestDate = "1930", capacityInfo = "-",
            history1 = "-", referencesText = "-"
        )

        // Basin Reserve, Wellington
        "Basin Reserve" -> GroundDetail(
            capacity = "11,600",
            establishedYear = "1868",
            matchesCount = "-",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "-",
            pitchTagPrimary = "-",
            conditionTagPrimary = "-",
            owner = "-", openedYear = "1868", firstTestDate = "1930", capacityInfo = "-",
            history1 = "-", referencesText = "-"
        )

        // Bay Oval, Mount Maunganui
        "Bay Oval" -> GroundDetail(
            capacity = "10,000",
            establishedYear = "2005",
            matchesCount = "-",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "-",
            pitchTagPrimary = "-",
            conditionTagPrimary = "-",
            owner = "-", openedYear = "2005", firstTestDate = "2019", capacityInfo = "-",
            history1 = "-", referencesText = "-"
        )

        // Seddon Park, Hamilton
        "Seddon Park" -> GroundDetail(
            capacity = "10,000",
            establishedYear = "1950",
            matchesCount = "-",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "-",
            pitchTagPrimary = "-",
            conditionTagPrimary = "-",
            owner = "-", openedYear = "1950", firstTestDate = "1991", capacityInfo = "-",
            history1 = "-", referencesText = "-"
        )

        else -> GroundDetail.placeholderFor(ground)
    }
}
