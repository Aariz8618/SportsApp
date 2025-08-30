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
            record3Type = "Best BBI Test", record3Value = "8/35", record3Detail = "GA Lohmann vs AUS, 1987",
            record4Type = "Highest Score Test", record4Value = "329*", record4Detail = "MJ.Clark vs IND, 2012",
            owner = "SCG Trust",
            openedYear = "1848",
            firstTestDate = "1882",
            capacityInfo = "~48,000",
            history1 = "Cricket at the SCG dates to the mid‑1800s. The first recorded match on the then Garrison Ground was in 1854. NSW and Victoria used it for practice in 1860–61 before inter‑colonial games. After the Albert Ground closed in the late 1870s, the NSWCA moved to the Association Ground; the first first‑class match there was NSW vs Victoria, Feb 1878 (NSW won by 1 wicket).",
            history2 = "During Lord Harris’ 1878–79 tour, a riot erupted at the SCG in 1879 after an umpiring decision by George Coulthard. By the first Test at the SCG (17–21 Feb 1882), curator Ned Gregory had the ground in fine condition; Australia chased 197 and 169/5 to win. Reg ‘Tip’ Foster’s 287 (1903) was the SCG’s Test high for over a century until Michael Clarke’s 329* in the 100th SCG Test (Jan 2012).",
            history3 = "The ground has hosted many notable moments: Don Bradman watched his first SCG Test in 1920–21 and later made 452* in 1928–29 (NSW vs Qld). Night cricket debuted 28 Nov 1978 (World Series Cricket). Shane Warne played his first (1992) and final (2007) Tests here. The venue hosts the annual New Year ‘Pink Test’. On 25 Nov 2014, Phillip Hughes was struck by a bouncer at the SCG and later died on 27 Nov. In 2019, Australia’s win over India at the SCG marked their 1,000th international victory.",
            referencesText = "Sources: Wikipedia (Sydney Cricket Ground)",
            testMostRuns = "RT Ponting-1480 , Inn- 27 , Avg: 67.81",
            testMostWkts = "SK Warne- 64 , Inn-26 ,Avg: 28.12",
            odiMostRuns = "AR Border-1561 , Inn-62 , Avg: 28.90",
            odiMostWkts = "GD McGrath -50 , Avg: 18.30 , Eco:3.74",
            t20MostRuns = "V Kohli -298, Avg:99.43 ,SR: 144.60",
            t20MostWkts = "A Zampa-11 , Avg:21.09 , Eco:7.03",
        )

        // Melbourne
        "Melbourne Cricket Ground" -> GroundDetail(
            capacity = "100,024",
            establishedYear = "1853",
            matchesCount = "117",
            matchesLabel = "Tests",
            avgTest1st = "307", avgTest2nd = "314", avgTest3rd = "251", avgTest4th = "171",
            avgOdi1st = "231", avgOdi2nd = "194",
            avgT201st = "141", avgT202nd = "124",
            pitchSummary = "Drop-in surface with good pace and bounce; can flatten for batting. Often assists seam early, spin late.",
            pitchTagPrimary = "Pace-and-bounce",
            conditionTagPrimary = "Drop-in",
            record1Type = "Highest Test total", record1Value = "624/8d", record1Detail = "AUS vs PAK 2016",
            record2Type = "Lowest Test total",  record2Value = "36", record2Detail = "SA vs AUS 1932",
            record3Type = "Best BBI Test", record3Value = "9/86", record3Detail = "Sarfraz Nawaz vs AUS 1979",
            record4Type = "Highest Score Test", record4Value = "307", record4Detail = "RM Cowper vs ENG 1966",
            owner = "MCG Trust", openedYear = "1853", firstTestDate = "1877", capacityInfo = "100,024",
            history1 = "Opened in 1853 in Melbourne’s Yarra Park and managed by the Melbourne Cricket Club, the MCG quickly became Australia’s premier cricket venue. In March 1877 it staged the first‑ever Test match, laying the foundation for international cricket in Australia. The ground’s central location, rail and tram access, and adjacency to Melbourne Park cemented it as a national sporting hub. Through successive redevelopments, its bowl and stands have expanded and modernised while preserving heritage elements. The Members Reserve and long traditions of MCC membership remain integral to its identity. The venue’s acoustics and sheer scale contribute to a distinctive atmosphere unmatched in Australian cricket. Its history intertwines with Melbourne’s growth as a global sporting city.",
            history2 = "The MCG is synonymous with the annual Boxing Day Test, which consistently draws huge holiday crowds and global attention. It has also hosted seminal limited‑overs moments, including the first recognised One‑Day International in 1971. Across ICC tournaments it has staged marquee matches and finals, notably in 1992 and the 2015 Cricket World Cup, showcasing its ability to host mega‑events. The drop‑in pitch, introduced to accommodate multi‑sport use, has evolved to offer pace and bounce early and truer batting conditions later. Beyond cricket, it has held Australian rules football Grand Finals, Olympic events, and concerts, reinforcing its multi‑purpose stature. Iconic performances by Australian and visiting greats have created a rich on‑field tapestry. The ground’s museum and tours preserve this legacy for visitors year‑round.",
            history3 = "With a capacity of around 100,000, the MCG is the largest stadium in the Southern Hemisphere and among the world’s biggest cricket venues. Its skyline‑defining light towers and sweeping stands make it an instantly recognisable landmark. Modern facilities sit alongside MCC traditions, blending accessibility with heritage. Public transport links at Jolimont and Richmond stations and multiple tram routes make match‑day access straightforward. The stadium’s role in national ceremonies and milestone matches has made the ’G a cultural touchstone. Continual upgrades have enhanced fan experience while maintaining international hosting standards. The MCG remains central to Australia’s cricket narrative in the modern era.",
            referencesText = "Sources: Wikipedia (Melbourne Cricket Ground)"
            , testMostRuns = "DG Bradman-1671 , Inn-17, Avg: 58.74",
            testMostWkts = "DK Lillee-82 , Inn- 26 , Avg:21.92 ",
            odiMostRuns = "RT Ponting-2108 , Inn-41 , Avg:85.45 ",
            odiMostWkts = "SK Warne- 46 , Avg:22.60, Eco: 3.29 ",
            t20MostRuns = "GJ Maxwell-1590 , Avg:40.76 , SR:153.32 ",
            t20MostWkts = "KW Richardson-9 , Avg:14.44 , Eco: 8.29 "
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
            record3Type = "Best BBI Test", record3Value = "8/34", record3Detail = "IT Botham vs PAK 1978",
            record4Type = "Highest Score Test", record4Value = "333", record4Detail = "GA Gooch vs IND 1990",
            owner = "Marylebone Cricket Club",
            openedYear = "1814",
            firstTestDate = "1884",
            capacityInfo = "~31,000",
            history1 = "Founded by Thomas Lord, the present ground at St John’s Wood opened in 1814 and soon became the spiritual home of the game. Owned by the Marylebone Cricket Club (MCC), Lord’s has shaped cricket’s laws and customs for over two centuries. Its Grade II*‑listed Pavilion and the Long Room symbolise tradition and ceremony on match days. The ground’s pronounced slope, falling roughly 2.5 metres from one side to the other, creates unique playing nuances. Lord’s has hosted international cricket since 1884, as well as major domestic finals and global tournaments. The on‑site MCC Museum is among the oldest sporting museums in the world. Together these elements underpin its ‘Home of Cricket’ epithet.",
            history2 = "Through eras of change, Lord’s has balanced innovation with heritage. The futuristic Media Centre, unveiled in 1999, contrasts strikingly with Victorian architecture yet has become iconic in its own right. The ground has staged World Cup showpieces, including the 2019 final remembered for its dramatic finish, reinforcing Lord’s as a stage for history. Its stands have been periodically rebuilt to improve sightlines and capacity while retaining the ground’s intimate character. The slope and often helpful overheads can influence swing and seam, rewarding skill and discipline. Tradition endures in the walk through the Long Room and the applause of MCC members in their egg‑and‑bacon ties. The venue remains central to Middlesex CCC and the ECB’s operations.",
            history3 = "Cultural resonance at Lord’s extends beyond the boundary ropes. The museum’s collections, including the celebrated Ashes urn, tell cricket’s global story. Community and charitable initiatives run alongside elite events, embedding the ground within London’s sporting fabric. Accessibility via St John’s Wood underground and central London links supports large crowds on major days. The wicket square is renowned for high standards of preparation across multi‑format schedules. While maintaining tradition, Lord’s continues to modernise facilities and sustainability practices. Its legacy as cricket’s most storied address continues to evolve with each passing season.",
            referencesText = "Sources: Wikipedia (Lord’s Cricket Ground)"
            , testMostRuns = "JE Root-2166 , Inn-42 , Avg:55.53",
            testMostWkts = "JM Anderson-123 , Inn- 56, Avg:24.81 ",
            odiMostRuns = "ME Trescothick-595 ,Inn-13 , Avg:49.58 ",
            odiMostWkts = "D Gough -27 , Avg: 22.70, Eco: 4.47 ",
            t20MostRuns = "DPMD Jayawardene-98 , Avg:32.66 , SR:130.66 ",
            t20MostWkts = "Shahid Afridi -8 , Avg: 11 , Eco: 5.50"
        )


        // Kennington Oval (The Oval)
        "Kennington Oval" -> GroundDetail(
            capacity = "27,500",
            establishedYear = "1845",
            matchesCount = "107",
            matchesLabel = "Tests",
            avgTest1st = "337", avgTest2nd = "300", avgTest3rd = "238", avgTest4th = "159",
            avgOdi1st = "246", avgOdi2nd = "210",
            avgT201st = "152", avgT202nd = "138",
            pitchSummary = "Traditionally good for batting with true bounce; takes spin as the match wears on.",
            pitchTagPrimary = "Balanced",
            conditionTagPrimary = "Overcast",
            record1Type = "Highest Test total", record1Value = "903/7d", record1Detail = "ENG vs AUS 1938",
            record2Type = "Lowest Test total",  record2Value = "44/10", record2Detail = "AUS vs ENG 1896",
            record3Type = "Best BBI Test", record3Value = "9/57", record3Detail = "DE Malcolm vs SA",
            record4Type = "Highest Score Test", record4Value = "364", record4Detail = "L Hutton vs AUS  ",
            owner = "Surrey CCC", openedYear = "1845", firstTestDate = "1880", capacityInfo = "27,500",
            history1 = "Opened in 1845 on leased land in Kennington, The Oval quickly became a cradle of organised sport in England. Home to Surrey County Cricket Club since inception, it hosted the first Test match in England in September 1880 against Australia. In the 19th century it also staged early football and rugby internationals, underscoring its multi‑sport heritage. The gasometer rising beyond the stands became an enduring part of its skyline. Through Victorian and modern eras, the ground adapted to changing safety and comfort standards. Its wicket blocks have long produced true bounce and, later in matches, assistance for spin. The Oval’s history mirrors the growth of English cricket itself.",
            history2 = "The ground traditionally hosts the final Test of the English summer, a stage for many decisive Ashes moments. Landmark performances, memorable chases and emotional farewells have unfolded on its turf. Commercial naming as the Kia Oval reflects modern sponsorship while the venue maintains deep roots with Surrey members. Successive stand redevelopments have improved capacity and amenities without losing the ground’s character. The playing surface often rewards strokeplay, with overhead conditions adding typical London variability. It remains a favourite for domestic finals days and international white‑ball fixtures. Its central London location ensures strong attendances and vibrant atmospheres.",
            history3 = "Excellent transport links via Vauxhall and Oval stations make access straightforward for large crowds. The venue’s community programmes and outreach keep it embedded in south London life. Pitch preparation balances early pace and bounce with deterioration that can bring spin into play late. The Oval’s archives and tours preserve stories from 19th‑century origins to modern multi‑format cricket. Corporate and hospitality areas have been modernised alongside improved spectator facilities. The ground continues to evolve architecturally while retaining its familiar oval shape and intimate sightlines. It stands as one of cricket’s most recognisable venues worldwide.",
            referencesText = "Sources: Wikipedia (The Oval)"
            , testMostRuns = "L Hutton-1521, Inn-19 , Avg:89.47 ",
            testMostWkts = "IT Botham-52 , Inn-20 , Avg:26.51",
            odiMostRuns = "EJG Morgan-705 ,Inn-15 , Avg:64.09 ",
            odiMostWkts = "JM Anderson-30 , Avg:19.26, Eco:4.52 ",
            t20MostRuns = "CH Gayle-237 , Avg:47.40 , SR:147.20 ",
            t20MostWkts = "Umar Gul -9 , Avg:6.88 , Eco: 5.63"
        )

        // Adelaide Oval
        "Adelaide Oval" -> GroundDetail(
            capacity = "53,500",
            establishedYear = "1871",
            matchesCount = "84",
            matchesLabel = "Tests",
            avgTest1st = "285", avgTest2nd = "220", avgTest3rd = "210", avgTest4th = "180",
            avgOdi1st = "265", avgOdi2nd = "240",
            avgT201st = "165", avgT202nd = "155",
            pitchSummary = "Balanced wicket: true bounce for batting; pink-ball Tests can aid seam under lights; spin late.",
            pitchTagPrimary = "Balanced",
            conditionTagPrimary = "Dry",
            record1Type = "Highest Test total", record1Value = "674", record1Detail = "AUS vs IND 1948",
            record2Type = "Lowest Test total",  record2Value = "36", record2Detail = "IND vs AUS 2021",
            record3Type = "Best BBI", record3Value = "8/83", record3Detail = "AE Trott vs AUS 83/84",
            record4Type = "Highest Score", record4Value = "335*", record4Detail = "DA Warner vs PAK 2019",
            owner = "SACA", openedYear = "1871", firstTestDate = "1884", capacityInfo = "~53,500",
            history1 = "Established in 1871 within Adelaide’s parklands, Adelaide Oval is celebrated for its blend of heritage and modern design. The grassy hill and heritage scoreboard long defined its character before contemporary redevelopments. It hosted its first Test in 1884 and has remained a mainstay of Australian and international cricket. Distinctive sightlines across the River Torrens and city skyline contribute to its setting. Through the 20th century it also served as a key venue for Australian rules football. The ground’s playing surface is typically true, rewarding strokeplay with consistent bounce. Its precinct integrates parklands with stadium infrastructure in a uniquely Adelaide way.",
            history2 = "In the modern era Adelaide Oval pioneered day‑night Test cricket in Australia, drawing capacity crowds under lights. Pink‑ball conditions can assist seam and swing in the evening while remaining good for batting overall. Redevelopment projects have sensitively integrated heritage elements with new stands and facilities. The venue hosts major domestic and international cricket across formats, plus football, rugby and concerts. Memorable international contests and personal milestones by Australian greats feature prominently in its lore. The scoreboard and hill remain symbolic meeting points for spectators. Continued investment has expanded capacity and comfort while preserving atmosphere.",
            history3 = "Transport connectivity and riverbank activation have made match days a broader city experience. The stadium’s management by the SACA and stadium authority ensures high preparation standards. Curators aim for balanced pitches that evolve, bringing spin into play later in matches. Sustainability and technology upgrades support broadcast and fan engagement requirements. Adelaide Oval’s pink‑ball Tests have become a much‑anticipated fixture on the calendar. Its combination of history, scenery and competitive cricket keeps it among Australia’s favourite grounds. The venue continues to set benchmarks for multi‑use stadiums in the region.",
            referencesText = "Sources: Wikipedia (Adelaide Oval)"
            , testMostRuns = "RT Ponting-1743, Inn-31 , Avg:60.10 ",
            testMostWkts = "NM Lyon-63 , Inn- 26, Avg:25.36 ",
            odiMostRuns = "MJ Clarke -626, Inn-15, Avg: 52.16 ",
            odiMostWkts = "B Lee-23 , Avg: 20.43, Eco: 4.31",
            t20MostRuns = "GJ Maxwell-236 , Avg:236 , SR:205.21 ",
            t20MostWkts = "A Zampa-9 , Avg:11.11 , Eco: 6.25 "
        )
          // wankhede
        "Wankhede Stadium" -> GroundDetail(
            capacity = "33,108",
            establishedYear = "1974",
            matchesCount = "29",
            matchesLabel = "Tests",
            avgTest1st = "320", avgTest2nd = "290", avgTest3rd = "260", avgTest4th = "210",
            avgOdi1st = "275", avgOdi2nd = "250",
            avgT201st = "170", avgT202nd = "160",
            pitchSummary = "Flat with bounce; assists spin later",
            pitchTagPrimary = "Batting",
            conditionTagPrimary = "Humid Coastal",
            record1Type = "Highest Test total", record1Value = "631", record1Detail = "IND vs ENG, 2016",
            record2Type = "Lowest Test total",  record2Value = "88",  record2Detail = "ENG vs IND, 1981",
            record3Type = "Best BBI Test", record3Value = "10/119", record3Detail = "AY Patel vs IND,2021",
            record4Type = "Highest Score Test", record4Value = "242*", record4Detail = "CH Lloyd vs IND,1975",
            owner = "Mumbai Cricket Association",
            openedYear = "1974",
            firstTestDate = "1975",
            capacityInfo = "~33,108",
            history1 = "Opened in 1974 to relieve demand from the Brabourne Stadium, Wankhede rapidly became Mumbai’s primary international venue. It is owned and operated by the Mumbai Cricket Association and sits near the Arabian Sea, lending humid coastal conditions. The intimate, steep stands generate a vibrant, noisy atmosphere emblematic of Mumbai crowds. The ground hosted India’s victory in the 2011 ICC Cricket World Cup final, a landmark in national sporting history. Wankhede has witnessed numerous IPL and international classics featuring high scores and dramatic chases. Renovations for the 2011 event modernised facilities and improved spectator comfort. Its pitch often begins true and develops turn later in Tests.",
            history2 = "Memories at Wankhede include Sachin Tendulkar’s farewell Test in 2013, drawing emotional scenes and global attention. White‑ball cricket here tends to favour strokeplay, with evening dew occasionally aiding chasing sides. Sea breeze and surface preparation can offer early movement before conditions settle. The venue’s compact playing area and lively outfield keep the scoring rate high. Wankhede regularly hosts marquee IPL fixtures as home of the Mumbai Indians. Accessibility via Churchgate and Marine Lines stations supports large match‑day turnouts. The ground’s blend of history and modernity keeps it central to Mumbai’s cricket identity.",
            history3 = "Wankhede’s stands have been upgraded multiple times to meet contemporary safety and comfort benchmarks. The stadium’s location in South Mumbai integrates sport with the city’s cultural and coastal fabric. Broadcast‑friendly lighting and facilities enable seamless global coverage of major events. The surface usually offers bounce for spinners and grip as matches progress, bringing all disciplines into play. Hosting duties in the 2023 ICC Men’s Cricket World Cup, including a semi‑final, reaffirmed its status. The venue remains a favourite stage for India’s limited‑overs blockbusters. Its legacy continues to grow with each season and tournament.",
            referencesText = "Sources: Wikipedia (Wankhede Stadium)"
            , testMostRuns = "SM Gavaskar -1122 ,Inn-20, Avg:56.10 ",
            testMostWkts = "R Ashwin-41, Inn-12 , Avg:19.75 ",
            odiMostRuns = "V Kohli- 474 ,Inn-8 ,Avg:67.71",
            odiMostWkts = "Mohammed Shami -15 , Avg:10 , Eco:5.26",
            t20MostRuns = "V Kohli -197 , Avg:197 , SR:205.20 ",
            t20MostWkts = "Mohammed Shami -5 , Avg:10 , Eco:7.69"
        )

        // Eden Gardens
        "Eden Gardens" -> GroundDetail(
            capacity = "68,000",
            establishedYear = "1864",
            matchesCount = "42",
            matchesLabel = "Tests",
            avgTest1st = "323", avgTest2nd = "314", avgTest3rd = "255", avgTest4th = "143",
            avgOdi1st = "243", avgOdi2nd = "200",
            avgT201st = "153", avgT202nd = "137",
            pitchSummary = "Generally slowish and can assist spin; true batting surface when hard; dew may influence white-ball games.",
            pitchTagPrimary = "Spin-friendly",
            conditionTagPrimary = "Humid Coastal",
            record1Type = "Highest Test total", record1Value = "657/7d", record1Detail = "IND vs AUS,2001",
            record2Type = "Lowest Test total",  record2Value = "90", record2Detail = "IND vs WI,1983",
            record3Type = "Best BBI Test", record3Value = "8/64", record3Detail = "L Klusener vs IND,1996",
            record4Type = "Highest Score Test", record4Value = "281", record4Detail = "VVS Laxman vs AUS,2001",
            owner = "CAB", openedYear = "1864", firstTestDate = "1934", capacityInfo = "68,000",
            history1 = "Established in 1864, Eden Gardens is India’s oldest major cricket ground and among the largest in the world. Operated by the Cricket Association of Bengal, it has long been Kolkata’s sporting heartbeat. The amphitheatre‑like stands create one of cricket’s most intense atmospheres. Historic Tests and ODIs have unfolded here, drawing vast, knowledgeable crowds. Its urban setting near the Maidan places it at the centre of city life. The venue’s early growth paralleled the expansion of organised cricket in eastern India. Over time its facilities and seating have been repeatedly modernised to meet global standards.",
            history2 = "Eden Gardens hosted the 1987 Cricket World Cup final, marking Asia’s first staging of the tournament decider. It also staged the dramatic 2016 ICC Men’s T20 World Cup final, underlining its big‑event pedigree. The ground is etched into Indian cricket memory for the 2001 Test where VVS Laxman and Rahul Dravid led a famous fightback against Australia. Its pitch typically offers a good batting surface that can take turn as matches progress. The stadium has served as home to Bengal in domestic cricket and the Kolkata Knight Riders in the IPL. Floodlights and broadcast upgrades have enhanced night cricket spectacles. Renovations have balanced capacity with safety and sightlines.",
            history3 = "Access via central Kolkata routes and public transport brings huge match‑day crowds. The venue’s museum corners and honour boards celebrate milestones by Indian and visiting legends. Curatorial focus aims for a fair contest between bat and ball with conditions influenced by humidity and dew. Eden Gardens’ roars remain a rite of passage for players debuting here. As a civic space, it hosts community events alongside elite cricket. Continued maintenance ensures readiness for ICC and BCCI events. Its legacy as India’s theatre of cricket endures across generations.",
            referencesText = "Sources: Wikipedia (Eden Gardens)"
            , testMostRuns = "VVS Laxman-1217,Inn-15 , Avg: 110.63",
            testMostWkts = "- , Inn- , Avg: ",
            odiMostRuns = "SR Tendulkar-496, Inn-12 , Avg:49.60 ",
            odiMostWkts = "- , Avg: , Eco: ",
            t20MostRuns = "N Pooran-184 , Avg:61.33 , SR:140.45 ",
            t20MostWkts = "HV Patel -7 , Avg:18.71 , Eco:8.73 "
        )

        // M. Chinnaswamy Stadium
        "Chinnaswamy Stadium" -> GroundDetail(
            capacity = "38,000",
            establishedYear = "1969",
            matchesCount = "25",
            matchesLabel = "Tests",
            avgTest1st = "342", avgTest2nd = "308", avgTest3rd = "217", avgTest4th = "170",
            avgOdi1st = "241", avgOdi2nd = "216",
            avgT201st = "141", avgT202nd = "136",
            pitchSummary = "Surface composition varies (red/black soil); can be spin-friendly on dry tracks, otherwise balanced.",
            pitchTagPrimary = "Balanced",
            conditionTagPrimary = "Dry",
            record1Type = "Highest Test total", record1Value = "626", record1Detail = "IND vs PAK,2007",
            record2Type = "Lowest Test total",  record2Value = "46", record2Detail = "IND vs NZ,2024",
            record3Type = "Best BBI Test", record3Value = "8/50", record3Detail = "NM Lyon vs IND,2017",
            record4Type = "Highest Score Test ", record4Value = "267", record4Detail = "Younis Khan vs IND,2005",
            owner = "Karnataka State Cricket Association (KSCA)",
            openedYear = "1969",
            firstTestDate = "1974",
            capacityInfo = "~38,000",
            history1 = "Opened in 1969 in the heart of Bengaluru, the stadium is operated by the Karnataka State Cricket Association. Named after former KSCA president M. Chinnaswamy, it developed into a key Test and limited‑overs venue from the 1970s. Urban surroundings and relatively short boundaries foster high‑scoring contests and vibrant atmospheres. The ground has hosted numerous domestic finals and IPL fixtures for the city’s franchise. Its central location near Cubbon Park and MG Road makes it easily accessible. The surface has alternated between red and black soil preparations to tune pace and turn. Over time stands and facilities have been upgraded to modern standards.",
            history2 = "Chinnaswamy has been an early adopter of sustainability initiatives, including significant on‑site solar power generation. Night cricket here is renowned for electric crowds and quick outfields. The pitch typically offers trueness for strokeplay while providing grip and spin as matches age. Visiting teams often prepare for high‑tempo limited‑overs cricket at this venue. The stadium has hosted memorable India victories and individual milestones across formats. Training and academy infrastructure around the ground supports Karnataka’s strong domestic pipeline. Its role in nurturing talent complements its status as an international stage.",
            history3 = "Broadcast facilities and lighting have been enhanced to meet global coverage requirements. Hospitality and seating refurbishments have balanced capacity with spectator comfort. The venue’s microclimate and evening dew can shape T20 strategies, especially for chasing sides. Curators alternate soil mixes to manage bounce and wear across busy schedules. Strong public transport and central city access sustain high attendance. As home to Karnataka cricket and Royal Challengers Bengaluru, it remains central to the city’s sporting identity. The ground’s evolution mirrors Bengaluru’s rise as a modern, tech‑savvy metropolis.",
            referencesText = "Sources: Wikipedia (M. Chinnaswamy Stadium)"
            , testMostRuns = "SR Tendulkar-869, Inn-16, Avg: 62.07",
            testMostWkts = "A Kumble-41 , Inn-16 , Avg: 34.53 ",
            odiMostRuns = "SR Tendulkar-534, Inn-11, Avg: 48.54",
            odiMostWkts = "Z Khan-14 , Avg:25.85 , Eco:5.24 ",
            t20MostRuns = "RG Sharma-150 , Avg:50.00 , SR: 157.89",
            t20MostWkts = "YS Chahal-6 , Avg:12 , Eco: 9.00 "
        )

        // Narendra Modi Stadium
        "Narendra Modi Stadium" -> GroundDetail(
            capacity = "132,000",
            establishedYear = "1982",
            matchesCount = "14",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "Surface composition varies (red/black soil); can be spin-friendly on dry tracks, otherwise balanced.",
            pitchTagPrimary = "Balanced",
            conditionTagPrimary = "Dry",
            record1Type = "Highest Test total", record1Value = "-", record1Detail = "-",
            record2Type = "Lowest Test total",  record2Value = "-", record2Detail = "-",
            record3Type = "Best BBI Test", record3Value = "-", record3Detail = "-",
            record4Type = "Highest Score Test ", record4Value = "-", record4Detail = "-",
            owner = "GCA", openedYear = "1982", firstTestDate = "1983", capacityInfo = "132,000",
            history1 = "Located in Motera, Ahmedabad, the stadium was extensively rebuilt and reopened with a record capacity of around 132,000. Owned by the Gujarat Cricket Association, it is currently the world’s largest cricket venue. The modern bowl design improves sightlines, circulation and safety for massive crowds. Multiple practice facilities and dressing rooms support back‑to‑back match scheduling. Its scale and amenities enable hosting of ceremonies and non‑cricket events as well. The venue has rapidly become a focal point for major international fixtures in India. Red and black soil pitches provide curators with distinct pace and spin characteristics.",
            history2 = "Post‑reconstruction, the ground has hosted high‑profile Test, ODI and T20I series featuring India and visiting sides. The pink‑ball Test against England underscored its adaptability to multi‑format demands. Extensive lighting and broadcast infrastructure ensure global coverage befitting marquee events. Spectator facilities emphasise accessibility and ease of movement across levels and concourses. The stadium’s position within a larger sports complex supports training and community activity. Its scale has redefined expectations for cricket attendance and in‑stadium experience. Ongoing refinements continue to streamline event operations at full capacity.",
            history3 = "The stadium staged the 2023 ICC Men’s Cricket World Cup final, reinforcing its global profile. Its flexible pitch square, with contrasting soil types, allows variation across a series. Transport links and parking capacity are designed for exceptionally large match‑day flows. Corporate boxes and hospitality areas cater to international standards for mega‑events. The ground’s rapid ascent as a flagship venue has reshaped India’s major‑event map. Sustainability and maintenance programmes aim to keep the facility tournament‑ready. As a modern colossus, it stands alongside the world’s most ambitious sporting arenas.",
            referencesText = "Sources: Wikipedia (Narendra Modi Stadium)"
            , testMostRuns = "- , Avg: ",
            testMostWkts = "- , Inn- , Avg: ",
            odiMostRuns = "- , Avg: ",
            odiMostWkts = "- , Avg: , Eco: ",
            t20MostRuns = "- , Avg: , SR: ",
            t20MostWkts = "- , Avg: , Eco: "
        )

        // HPCA Stadium, Dharamshala
        "Himachal Pradesh Cricket Association Stadium" -> GroundDetail(
            capacity = "23,000",
            establishedYear = "2003",
            matchesCount = "2",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "Altitude venue with true bounce and carry; fresh grass can aid pace and seam.",
            pitchTagPrimary = "Pace-and-bounce",
            conditionTagPrimary = "Altitude",
            record1Type = "Highest Test total", record1Value = "-", record1Detail = "-",
            record2Type = "Lowest Test total",  record2Value = "-", record2Detail = "-",
            record3Type = "Best BBI Test", record3Value = "-", record3Detail = "-",
            record4Type = "Highest Score Test", record4Value = "-", record4Detail = "-",
            owner = "HPCA", openedYear = "2003", firstTestDate = "2017", capacityInfo = "23,000",
            history1 = "Situated in Dharamshala in the Himalayan foothills, the HPCA Stadium is one of world cricket’s most picturesque venues. Opened in 2003, its setting at altitude offers cool conditions and dramatic mountain vistas. The stands are low‑slung to preserve views, giving the ground a distinctive open feel. International fixtures here have showcased India and visiting teams in front of enthusiastic crowds. The venue’s compact bowl and sharp light make for striking television images. The climate can shift quickly, adding a tactical layer to day‑night scheduling. Its emergence broadened India’s map of international cricket beyond traditional metros.",
            history2 = "Pitches in Dharamshala generally provide true bounce and carry, especially when fresh grass is present. The combination of altitude and breeze can aid seam bowlers early before conditions level out. White‑ball matches often see brisk scoring thanks to a quick outfield. The ground has hosted high‑profile domestic games and IPL fixtures when scheduled, boosting regional interest. Facilities have been developed to meet ICC standards without compromising the venue’s natural aesthetics. Crowd engagement is notably familial and tourist‑friendly due to the location. The setting has helped brand Himachal Pradesh as a cricketing destination.",
            history3 = "Access to the stadium has improved with better regional transport and hospitality infrastructure. Stadium operations emphasise maintenance suited to variable mountain weather. Practice areas and player amenities have expanded alongside international match commitments. The HPCA’s stewardship has integrated community programmes with elite events. The venue’s sustainability focus aims to protect its sensitive environment. As fixtures increase, Dharamshala continues to refine spectator services and broadcast readiness. The ground’s unique identity rests on a balance of modern standards and natural beauty.",
            referencesText = "Sources: Wikipedia (Himachal Pradesh Cricket Association Stadium)"
            , testMostRuns = "- , Avg: ",
            testMostWkts = "- , Inn- , Avg: ",
            odiMostRuns = "- , Avg: ",
            odiMostWkts = "- , Avg: , Eco: ",
            t20MostRuns = "- , Avg: , SR: ",
            t20MostWkts = "- , Avg: , Eco: "
        )

        // Headingley
        "Headingley" -> GroundDetail(
            capacity = "18,350",
            establishedYear = "1890",
            matchesCount = "82",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "Helpful to seam and swing, especially under cloud; can flatten later for batting.",
            pitchTagPrimary = "Seam-friendly",
            conditionTagPrimary = "Overcast",
            record1Type = "Highest Test total", record1Value = "-", record1Detail = "-",
            record2Type = "Lowest Test total",  record2Value = "-", record2Detail = "-",
            record3Type = "Best BBI Test", record3Value = "-", record3Detail = "-",
            record4Type = "Highest Score Test", record4Value = "-", record4Detail = "-",
            owner = "Yorkshire CCC", openedYear = "1890", firstTestDate = "1899", capacityInfo = "18,350",
            history1 = "Set within the Headingley Stadium complex in Leeds, the cricket ground adjoins the rugby venue via a shared stand. It has hosted Test cricket since 1899 and is home to Yorkshire County Cricket Club. The main entrance on Kirkstall Lane opens into a venue renowned for both atmosphere and drama. Conditions often reflect northern English weather patterns, with cloud cover influencing swing. Over time, stands and player facilities have been modernised to enhance capacity and comfort. The ground’s layout maintains a clear sightline from most seats despite redevelopment. Its character blends traditional county cricket heritage with a modern event footprint.",
            history2 = "Headingley has staged memorable international contests across formats, building a reputation for resilience and comebacks. Pitches traditionally offer pace and seam early before flattening for batting later on. Domestic matches draw passionate Yorkshire support, rooted in one of county cricket’s strongest nurseries. The ground is a regular stop for limited‑overs internationals and marquee domestic fixtures. Upgrades to media and broadcast capabilities have kept it aligned with ICC requirements. The shared complex enables efficient logistics for multi‑sport events. Its role in English cricket remains central both historically and operationally.",
            history3 = "Transport connectivity around Leeds supports substantial match‑day attendance. Weather variability can shape captains’ decisions at the toss and inform bowling selections. The playing square is curated to balance competitiveness and durability across busy calendars. Community outreach and coaching pathways link Yorkshire’s grassroots to elite competition at Headingley. Renovated hospitality and concourse areas improve spectator experience without losing local flavour. The ground’s cadence at season’s height captures Yorkshire’s cricketing culture. It continues to evolve while preserving the essence of Headingley.",
            referencesText = "Sources: Wikipedia (Headingley Cricket Ground)"
            , testMostRuns = "- , Avg: ",
            testMostWkts = "- , Inn- , Avg: ",
            odiMostRuns = "- , Avg: ",
            odiMostWkts = "- , Avg: , Eco: ",
            t20MostRuns = "- , Avg: , SR: ",
            t20MostWkts = "- , Avg: , Eco: "
        )

        // Old Trafford
        "Old Trafford" -> GroundDetail(
            capacity = "26,000",
            establishedYear = "1857",
            matchesCount = "82",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "Offers bounce; can assist spin later in the game with wear.",
            pitchTagPrimary = "Balanced",
            conditionTagPrimary = "Overcast",
            record1Type = "Highest Test total", record1Value = "-", record1Detail = "-",
            record2Type = "Lowest Test total",  record2Value = "-", record2Detail = "-",
            record3Type = "Best BBI Test", record3Value = "-", record3Detail = "-",
            record4Type = "Highest Score Test ", record4Value = "-", record4Detail = "-",
            owner = "Lancashire CCC", openedYear = "1857", firstTestDate = "1884", capacityInfo = "26,000",
            history1 = "Opened in 1857 in Old Trafford, Greater Manchester, the ground predates many other major English venues. Initially home to Manchester Cricket Club, it became the home of Lancashire County Cricket Club in 1864. The venue has hosted Test cricket since 1884 and is a fixture on England’s international circuit. Architectural updates have balanced heritage buildings with striking modern stands. The widely spaced layout and breeze can occasionally aid swing, especially under cloud. Crowd sightlines and broadcast infrastructure have been significantly upgraded in recent decades. The ground’s longevity has made it a repository of English cricketing memory.",
            history2 = "In 2013 the venue adopted the commercial name Emirates Old Trafford following a sponsorship agreement. Extensive redevelopment included the pavilion restoration, new media and hospitality facilities, and improved spectator amenities. The pitch often offers bounce and carries well to the cordon, with spin entering later in the game. Domestic and international fixtures alike draw strong north‑west support. The venue regularly stages ICC tournament matches and high‑profile limited‑overs games. Its flexible spaces accommodate conferences and community events outside match days. Old Trafford’s evolution illustrates how classic grounds adapt to modern demands.",
            history3 = "Transport links via tram and rail facilitate large event days in Greater Manchester. The ground’s curators prepare surfaces to sustain multi‑day Tests amidst intensive scheduling. Lighting and DLS‑era drainage systems support day‑night and white‑ball cricket. Engagement with local schools and clubs helps maintain Lancashire’s talent pipeline. Heritage displays preserve the club’s achievements and visiting milestones. The balance between corporate facilities and public areas aims to keep the venue accessible. Old Trafford remains a pillar of English cricket’s past and present.",
            referencesText = "Sources: Wikipedia (Old Trafford Cricket Ground)"
            , testMostRuns = "- , Avg: ",
            testMostWkts = "- , Inn- , Avg: ",
            odiMostRuns = "- , Avg: ",
            odiMostWkts = "- , Avg: , Eco: ",
            t20MostRuns = "- , Avg: , SR: ",
            t20MostWkts = "- , Avg: , Eco: "
        )

        // Edgbaston
        "Edgbaston" -> GroundDetail(
            capacity = "25,000",
            establishedYear = "1882",
            matchesCount = "57",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "Generally good for batting with pace on; overhead conditions can bring swing and seam.",
            pitchTagPrimary = "Balanced",
            conditionTagPrimary = "Overcast",
            record1Type = "Highest Test total", record1Value = "-", record1Detail = "-",
            record2Type = "Lowest Test total",  record2Value = "-", record2Detail = "-",
            record3Type = "Best BBI Test", record3Value = "-", record3Detail = "-",
            record4Type = "Highest Score Test", record4Value = "-", record4Detail = "-",
            owner = "Warwickshire CCC", openedYear = "1882", firstTestDate = "1902", capacityInfo = "25,000",
            history1 = "Located in the Edgbaston area of Birmingham, the ground is home to Warwickshire CCC and the Birmingham Bears. It is one of England’s key international venues, staging Tests, ODIs and T20Is for decades. Redevelopment has produced modern seating bowls and hospitality balanced against traditional pavilions. The ground’s acoustics help generate a renowned atmosphere for big games. Pitch conditions are usually fair for batting with movement under overcast skies. The venue participates regularly in ICC tournaments and national finals. Its operations underpin a vibrant cricket culture across the West Midlands.",
            history2 = "Edgbaston has hosted T20 Finals Day more than any other ground, becoming synonymous with the event. The venue has also welcomed The Hundred’s Birmingham Phoenix as a principal home. Upgraded lighting and broadcast facilities support prime‑time white‑ball cricket. Sightlines and concourses were re‑engineered to improve accessibility and capacity. Traditional strengths in medium‑pace and swing bowling often come to the fore when conditions assist. The ground remains a hub for community cricket programmes and club pathways. Its reputation as a fortress for England in certain series has added to its allure.",
            history3 = "Transport links and parking around the south‑west of Birmingham are geared to large event days. Drainage and outfield improvements allow rapid turnarounds during busy summers. Curators aim for pitches that evolve to keep all disciplines in play. Enhanced museum and heritage displays celebrate Warwickshire and England achievements. Corporate and fan experiences coexist across tiered hospitality and public stands. As redevelopment continues in phases, the venue preserves its identity while modernising. Edgbaston’s status as a marquee venue remains firmly established.",
            referencesText = "Sources: Wikipedia (Edgbaston Cricket Ground)"
            , testMostRuns = "- , Avg: ",
            testMostWkts = "- , Inn- , Avg: ",
            odiMostRuns = "- , Avg: ",
            odiMostWkts = "- , Avg: , Eco: ",
            t20MostRuns = "- , Avg: , SR: ",
            t20MostWkts = "- , Avg: , Eco: "
        )

        // Perth Stadium (Optus)
        "Perth Stadium" -> GroundDetail(
            capacity = "61,266",
            establishedYear = "2018",
            matchesCount = "4",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "Fast surface with steepling bounce, similar to the WACA characteristics.",
            pitchTagPrimary = "Pace-and-bounce",
            conditionTagPrimary = "Dry",
            record1Type = "Highest Test total", record1Value = "-", record1Detail = "-",
            record2Type = "Lowest Test total",  record2Value = "-", record2Detail = "-",
            record3Type = "Best BBI Test", record3Value = "-", record3Detail = "-",
            record4Type = "Highest Score Test", record4Value = "-", record4Detail = "-",
            owner = "-", openedYear = "2018", firstTestDate = "2018", capacityInfo = "61,266",
            history1 = "Completed in late 2017 and opened in January 2018, Perth Stadium sits in the Burswood precinct on the Swan River. Commercially known as Optus Stadium, it is a multi‑purpose venue designed for code‑switching. With a capacity of 61,266 for oval sports, it ranks among Australia’s largest stadiums. Its architecture emphasises circulation, accessibility and fan amenities across wide concourses. For cricket, the surface has showcased pace and steep bounce reminiscent of Perth’s traditional WACA traits. Lighting and audio systems were engineered for major night events and broadcast spectacle. The stadium’s riverfront location has become a focal point for city events.",
            history2 = "Cricket fixtures at Perth Stadium span all formats, including marquee international series. The venue also hosts Australian rules football, concerts and community events, maximising year‑round utilisation. Modular seating allows adjustments for rectangular codes to lift capacity. Player and practice facilities were purpose‑built to ICC and AFL standards. Transport connectivity via rail, bus and pedestrian bridges enables large, safe egress. The stadium’s design prioritises shade and airflow for spectator comfort in Perth’s climate. It has rapidly established a reputation as a fast, lively cricketing surface.",
            history3 = "Operationally, the ground benefits from state‑of‑the‑art turf management and drainage systems. Technology integration supports advanced replay, analytics and in‑stadium engagement. Hospitality tiers and community spaces balance premium experiences with public accessibility. The precinct planning links parks, paths and river nodes into the match‑day journey. As a modern venue, it adapts quickly to evolving event requirements and sustainability goals. International tournaments and bilateral series have reinforced its status on the global circuit. Perth Stadium continues to complement and extend Western Australia’s cricket heritage.",
            referencesText = "Sources: Wikipedia (Perth Stadium)"
            , testMostRuns = "- , Avg: ",
            testMostWkts = "- , Inn- , Avg: ",
            odiMostRuns = "- , Avg: ",
            odiMostWkts = "- , Avg: , Eco: ",
            t20MostRuns = "- , Avg: , SR: ",
            t20MostWkts = "- , Avg: , Eco: "
        )

        // Brisbane Cricket Ground (The Gabba)
        "Brisbane Cricket Stadium" -> GroundDetail(
            capacity = "42,000",
            establishedYear = "1895",
            matchesCount = "69",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "Known for pace and bounce with consistent carry; rewards disciplined fast bowling.",
            pitchTagPrimary = "Pace-and-bounce",
            conditionTagPrimary = "Humid Coastal",
            record1Type = "Highest Test total", record1Value = "-", record1Detail = "-",
            record2Type = "Lowest Test total",  record2Value = "-", record2Detail = "-",
            record3Type = "Best BBI Test", record3Value = "-", record3Detail = "-",
            record4Type = "Highest Score Test", record4Value = "-", record4Detail = "-",
            owner = "Queensland Government", openedYear = "1895", firstTestDate = "1931", capacityInfo = "42,000",
            history1 = "Formally the Brisbane Cricket Ground but universally known as the Gabba, the venue takes its nickname from Woolloongabba. Established in 1895, it has evolved into a major multi‑sport stadium for cricket and Australian rules football. Over the decades it has also hosted rugby codes, football, athletics and concerts. The circular bowl and steep seating create a cauldron‑like atmosphere for big matches. Its pitch is famed for pace and bounce with consistent carry to the keeper and slips. International cricket arrived in 1931 and the ground remains a staple of Australian home summers. Recent refurbishments have improved amenities while keeping its intimidating character.",
            history2 = "The Gabba serves as home to Queensland Bulls and the Brisbane Heat, alongside the Brisbane Lions in the AFL. Its reputation as a stronghold for Australia in Tests stems from conditions that reward disciplined fast bowling. White‑ball cricket here can be brisk, with value for shots on a true surface. The subtropical climate and summer storms demand robust drainage and ground operations. Broadcast and lighting upgrades have kept pace with global standards. Large crowds are supported by transport links and event‑day planning around Woolloongabba. The stadium has anchored Brisbane’s bid to host major international events.",
            history3 = "Curatorial approaches aim to retain the pitch’s identity while managing wear across busy schedules. The venue’s concourses and facilities have been progressively modernised for accessibility and comfort. Community programmes and school initiatives connect the ground to Queensland’s cricket pathway. Naming conventions and heritage displays preserve the venue’s history for visitors. The Gabba’s distinctive feel makes it a proving ground for debutants and visiting quicks alike. Continued investment is aligned with Brisbane’s broader sporting infrastructure plans. Its role as a season‑opening Test venue has often set the tone for Australian summers.",
            referencesText = "Sources: Wikipedia (The Gabba)"
            , testMostRuns = "- , Avg: ",
            testMostWkts = "- , Inn- , Avg: ",
            odiMostRuns = "- , Avg: ",
            odiMostWkts = "- , Avg: , Eco: ",
            t20MostRuns = "- , Avg: , SR: ",
            t20MostWkts = "- , Avg: , Eco: "
        )

        // Wanderers, Johannesburg
        "The Wanderers Stadium" -> GroundDetail(
            capacity = "34,000",
            establishedYear = "1956",
            matchesCount = "39",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "High-altitude venue with lively pace and bounce; can favor fast bowlers.",
            pitchTagPrimary = "Pace-and-bounce",
            conditionTagPrimary = "Altitude",
            record1Type = "Highest Test total", record1Value = "-", record1Detail = "-",
            record2Type = "Lowest Test total",  record2Value = "-", record2Detail = "-",
            record3Type = "Best BBI Test", record3Value = "-", record3Detail = "-",
            record4Type = "Highest Score Test", record4Value = "-", record4Detail = "-",
            owner = " Lions Cricket", openedYear = "1956", firstTestDate = "1956", capacityInfo = "34,000",
            history1 = "Located in Illovo, Johannesburg, the Wanderers Stadium is colloquially known as the Bullring for its intense atmosphere. Built in the 1950s, it has been a mainstay of South African international cricket since readmission. The bowl design and vocal crowds contribute to a sense of theatre on major occasions. Altitude and prevailing conditions can produce lively pace and bounce, suiting fast bowlers. The venue regularly hosts Tests, ODIs and T20Is as well as franchise competitions. Its central role in Gauteng cricket extends to development and community pathways. The ground’s aura makes it one of the most challenging tours for visiting teams.",
            history2 = "Memorable high‑scoring chases and gripping finishes have etched the Bullring into limited‑overs folklore. Pitches often start quick before wearing to bring cutters and spinners into play. Upgrades to seating, hospitality and media facilities have accompanied South Africa’s modern cricket era. The ground is home to the Lions and has hosted T20 franchise sides from Johannesburg. Its location near major city arteries aids match‑day logistics for large crowds. Broadcast infrastructure supports global coverage of marquee series and ICC events. The venue’s history reflects Johannesburg’s sporting energy and resilience.",
            history3 = "Operational focus includes turf management suited to Highveld weather patterns. Spectator experience has been enhanced through improved concourses and amenities. Practice facilities and indoor nets support elite preparation throughout the season. Community outreach and school programmes aim to widen participation in the region. The stadium’s intimidating reputation remains a competitive advantage for home sides. Sustainability and maintenance projects continue to modernise the historic venue. Wanderers endures as a symbol of South African cricket’s passion and pace.",
            referencesText = "Sources: Wikipedia (Wanderers Stadium)"
            , testMostRuns = "- , Avg: ",
            testMostWkts = "- , Inn- , Avg: ",
            odiMostRuns = "- , Avg: ",
            odiMostWkts = "- , Avg: , Eco: ",
            t20MostRuns = "- , Avg: , SR: ",
            t20MostWkts = "- , Avg: , Eco: "
        )

        // SuperSport Park, Centurion
        "SuperSport Park" -> GroundDetail(
            capacity = "22,000",
            establishedYear = "1986",
            matchesCount = "30",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "Usually offers pace and bounce with some seam; good strokeplay conditions.",
            pitchTagPrimary = "Pace-and-bounce",
            conditionTagPrimary = "Dry",
            record1Type = "Highest Test total", record1Value = "-", record1Detail = "-",
            record2Type = "Lowest Test total",  record2Value = "-", record2Detail = "-",
            record3Type = "Best BBI Test", record3Value = "-", record3Detail = "-",
            record4Type = "Highest Score Test", record4Value = "-", record4Detail = "-",
            owner = "SuperSport", openedYear = "1986", firstTestDate = "1995", capacityInfo = "22,000",
            history1 = "Situated in Centurion, the ground is widely known as SuperSport Park following a commercial renaming from Centurion Park. With a capacity of roughly 22,000, it provides an intimate yet lively setting for international cricket. The venue became a regular on South Africa’s calendar after the 1990s. Its square is known for pace and carry that reward confident strokeplay and seam bowling. Successive upgrades have refined player areas, media spaces and spectator facilities. The surrounding precinct integrates retail and access roads for efficient event flow. The venue’s reputation rests on competitive cricket and strong local support.",
            history2 = "SuperSport Park has hosted pivotal bilateral series and white‑ball contests producing high scores. Afternoon thunderstorms can influence DLS considerations, adding a tactical dimension. Curators often prepare surfaces that remain true across the first innings and gradually take grip. Domestic Titans fixtures have nurtured talent feeding the national side. Broadcast‑ready lighting and camera gantries aid global dissemination of South African cricket. The stadium’s design ensures clear sightlines from most seats. Its calendar balances international demands with domestic competitions and community use.",
            history3 = "Transport access from Pretoria and Johannesburg makes it a convenient Highveld venue. Drainage and outfield standards support quick turnarounds during packed seasons. Fan facilities include family‑friendly zones and upgraded hospitality tiers. Heritage elements acknowledge the venue’s evolution from the late 20th century to today. Sustainability initiatives guide ongoing maintenance and operations. The ground remains a favoured stop for touring teams expecting lively but fair conditions. SuperSport Park continues to anchor Centurion’s presence on the global cricket map.",
            referencesText = "Sources: Wikipedia (Centurion Park/SuperSport Park)"
            , testMostRuns = "- , Avg: ",
            testMostWkts = "- , Inn- , Avg: ",
            odiMostRuns = "- , Avg: ",
            odiMostWkts = "- , Avg: , Eco: ",
            t20MostRuns = "- , Avg: , SR: ",
            t20MostWkts = "- , Avg: , Eco: "
        )

        // Kingsmead, Durban
        "Kingsmead" -> GroundDetail(
            capacity = "25,000",
            establishedYear = "1923",
            matchesCount = "46",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "Humid coastal venue where fresh grass and breeze can aid seam and swing early; slows later.",
            pitchTagPrimary = "Seam-friendly",
            conditionTagPrimary = "Humid Coastal",
            record1Type = "Highest Test total", record1Value = "-", record1Detail = "-",
            record2Type = "Lowest Test total",  record2Value = "-", record2Detail = "-",
            record3Type = "Best BBI Test", record3Value = "-", record3Detail = "-",
            record4Type = "Highest Score Test", record4Value = "-", record4Detail = "-",
            owner = "The KwaZulu-Natal Cricket Union (KZNCU)", openedYear = "1923", firstTestDate = "1923", capacityInfo = "25,000",
            history1 = "Located near Durban’s beachfront, Kingsmead has staged international cricket since the early 20th century. Established in 1923, it grew alongside KwaZulu‑Natal’s domestic scene and the Dolphins franchise. The ground’s coastal climate and sea breeze introduce variability, with mornings often aiding movement. Traditional end names, the Umgeni End and Old Fort Road End, reflect its city setting. Over time, stands and facilities have been upgraded to support modern broadcast and spectator needs. The venue has hosted Tests, ODIs and T20Is, including global tournament fixtures. Its atmosphere blends holiday‑town energy with serious cricketing pedigree.",
            history2 = "Kingsmead surfaces can present fresh grass and lively seam early before easing for batting. Humidity and wind direction influence swing, while afternoons can bring showers in summer. Night matches sometimes see dew impacting bowling control and fielding. The compact layout and quick outfield provide value for well‑timed shots in white‑ball formats. Domestic competitions keep the square active and prepared across the season. The ground’s location makes it accessible to locals and tourists alike. Its role in South African cricket is both historic and ongoing.",
            history3 = "Facility improvements have focused on accessibility, seating comfort and safety certifications. Practice wickets and indoor nets support elite preparation for international sides. Match operations are tuned to coastal weather patterns and DLS considerations. Hospitality areas cater to family crowds as well as corporate guests. The venue’s association with Durban’s sporting calendar ensures steady attendance. Sustainable maintenance of the outfield and drainage is an operational priority. Kingsmead remains a distinctive, challenging stop on international tours.",
            referencesText = "Sources: Wikipedia (Kingsmead Cricket Ground)"
            , testMostRuns = "- , Avg: ",
            testMostWkts = "- , Inn- , Avg: ",
            odiMostRuns = "- , Avg: ",
            odiMostWkts = "- , Avg: , Eco: ",
            t20MostRuns = "- , Avg: , SR: ",
            t20MostWkts = "- , Avg: , Eco: "
        )

        // Newlands, Cape Town
        "Newlands" -> GroundDetail(
            capacity = "25,000",
            establishedYear = "1888",
            matchesCount = "56",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "Traditionally assists seam and swing with lively carry; can take spin as wear sets in.",
            pitchTagPrimary = "Seam-friendly",
            conditionTagPrimary = "Windy",
            record1Type = "Highest Test total", record1Value = "-", record1Detail = "-",
            record2Type = "Lowest Test total",  record2Value = "-", record2Detail = "-",
            record3Type = "Best BBI Test", record3Value = "-", record3Detail = "-",
            record4Type = "Highest Score Test", record4Value = "-", record4Detail = "-",
            owner = "Western Province Cricket Association (WPCA)", openedYear = "1888", firstTestDate = "1889", capacityInfo = "25,000",
            history1 = "Nestled in Cape Town’s southern suburbs, Newlands is famed for its views of Table Mountain and Devil’s Peak. Established in the late 19th century, it is among South Africa’s oldest and most beloved cricket venues. It has hosted Test cricket since 1889 and remains central to the Western Province game. The amphitheatre‑like stands and trees lend a classic, intimate feel. Atmospheric conditions, including the Cape Doctor wind, often shape swing and seam. The ground’s heritage coexists with continual upgrades to seating, pavilions and broadcast spaces. Newlands is synonymous with summer cricket in the Cape.",
            history2 = "The venue is owned by the Western Province Cricket Association and serves as home to Western Province and franchise sides. It has staged memorable Tests and limited‑overs matches featuring South Africa and visiting teams. Pitches typically provide bounce and carry with lateral movement early before batting improves. Crowds are knowledgeable and vocal, contributing to a distinctive home advantage. The outfield is usually swift, rewarding timing and placement. Hospitality and member facilities blend tradition with modern expectations. Newlands’ calendar ties closely to festive season cricket in South Africa.",
            history3 = "Transport links and the suburban setting encourage strong local attendance. Practice facilities and high‑performance centres support player development pathways. Curators aim for competitive surfaces that evolve to involve spin later in matches. Heritage features and mountain views are carefully preserved through redevelopment. Sustainability and water management shape pitch and outfield care in dry months. The ground’s status as a must‑visit venue for touring sides endures. Newlands remains a jewel of South African cricket.",
            referencesText = "Sources: Wikipedia (Newlands Cricket Ground)"
            , testMostRuns = "- , Avg: ",
            testMostWkts = "- , Inn- , Avg: ",
            odiMostRuns = "- , Avg: ",
            odiMostWkts = "- , Avg: , Eco: ",
            t20MostRuns = "- , Avg: , SR: ",
            t20MostWkts = "- , Avg: , Eco: "
        )

        // Boland Park, Paarl
        "Boland Park" -> GroundDetail(
            capacity = "10,000",
            establishedYear = "1996",
            matchesCount = "17",
            matchesLabel = "ODIs",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "Typically dry and slow; assists spinners and cutters; outfield can be quick.",
            pitchTagPrimary = "Spin-friendly",
            conditionTagPrimary = "Dry",
            record1Type = "Highest Test total", record1Value = "-", record1Detail = "-",
            record2Type = "Lowest Test total",  record2Value = "-", record2Detail = "-",
            record3Type = "Best BBI Test", record3Value = "-", record3Detail = "-",
            record4Type = "Highest Score Test", record4Value = "-", record4Detail = "-",
            owner = "Cricket Boland", openedYear = "1996", firstTestDate = "-", capacityInfo = "10,000",
            history1 = "Set in South Africa’s Cape Winelands at Paarl, Boland Park opened in 1996 as a dedicated cricket venue. Its scenic backdrop of mountains and vineyards gives it a unique character. The ground has primarily hosted domestic cricket for Boland alongside international limited‑overs fixtures. Hot, dry summers and afternoon winds influence playing conditions and comfort. The stadium’s intimate size fosters close engagement between players and spectators. Upgrades over time have improved seating, amenities and broadcast positions. It serves the broader Boland region’s cricketing community.",
            history2 = "Boland Park featured at the 2003 ICC Cricket World Cup, hosting several matches and showcasing Paarl to a global audience. Surfaces here tend to be on the slower, drier side, suiting spinners and cutters. Batters who rotate strike and target short straight boundaries can prosper. The venue has also hosted SA20 franchise matches and prior Mzansi Super League fixtures. Its calendar includes provincial competitions and age‑group tournaments that feed national pathways. The outfield is typically quick under summer sun. Crowd experience remains relaxed and family‑friendly.",
            history3 = "Operational priorities include water‑wise turf management in a warm climate. Practice facilities support regional squads and visiting teams alike. Parking and access are tuned to moderate but enthusiastic crowds. Hospitality areas highlight local produce, adding to the venue’s sense of place. The ground’s compact bowl ensures strong sightlines from grass banks and stands. Continued improvements aim to balance community use with international readiness. Boland Park is a distinctive, picturesque stop on South Africa’s limited‑overs circuit.",
            referencesText = "Sources: Wikipedia (Boland Park)"
            , testMostRuns = "- , Avg: ",
            testMostWkts = "- , Inn- , Avg: ",
            odiMostRuns = "- , Avg: ",
            odiMostWkts = "- , Avg: , Eco: ",
            t20MostRuns = "- , Avg: , SR: ",
            t20MostWkts = "- , Avg: , Eco: "
        )

        // Hagley Oval, Christchurch
        "Hagley Oval" -> GroundDetail(
            capacity = "18,000",
            establishedYear = "1867",
            matchesCount = "14",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "Fresh green surfaces often aid seam and swing, especially early in matches.",
            pitchTagPrimary = "Seam-friendly",
            conditionTagPrimary = "Overcast",
            record1Type = "Highest Test total", record1Value = "-", record1Detail = "-",
            record2Type = "Lowest Test total",  record2Value = "-", record2Detail = "-",
            record3Type = "Best BBI Test", record3Value = "-", record3Detail = "-",
            record4Type = "Highest Score Test", record4Value = "-", record4Detail = "-",
            owner = "the Hagley Oval Foundation", openedYear = "1867", firstTestDate = "2014", capacityInfo = "18,000",
            history1 = "Hagley Oval lies within Christchurch’s Hagley Park, giving it a quintessentially parkland feel. Cricket on the site dates to the 1860s, but modern international use followed post‑earthquake redevelopment. After damage to Lancaster Park, Hagley Oval was upgraded and first hosted Test cricket in 2014. A new pavilion and facilities were built to contemporary standards while respecting the park setting. The ground’s openness and grassy embankments create a relaxed, village‑green atmosphere. Summer conditions and breeze can produce classic New Zealand seam movement. Its setting makes it a favourite for families and neutrals.",
            history2 = "Surfaces at Hagley often start with a green tinge, offering carry and lateral movement early. As matches progress, batting becomes more rewarding with application. The outfield is typically slick, encouraging quick scoring once set. The venue has staged international cricket across formats and domestic finals. Lighting installations broadened its night‑match capabilities in recent seasons. Community access to the park remains a consideration alongside elite sport. The oval has become synonymous with Christchurch’s cricket revival.",
            history3 = "Event operations balance heritage park values with the demands of televised sport. Curators aim for competitive pitches that reward skill rather than extremes. Practice blocks and player facilities support touring teams efficiently. Transport and parking plans account for central‑city proximity and pedestrian flows. Sustainability in turf care aligns with council and park guidelines. As a modern international venue with old‑world charm, it enhances New Zealand’s cricket footprint. Hagley Oval continues to grow in stature with each season.",
            referencesText = "Sources: Wikipedia (Hagley Oval)"
            , testMostRuns = "- , Avg: ",
            testMostWkts = "- , Inn- , Avg: ",
            odiMostRuns = "- , Avg: ",
            odiMostWkts = "- , Avg: , Eco: ",
            t20MostRuns = "- , Avg: , SR: ",
            t20MostWkts = "- , Avg: , Eco: "
        )

        // Eden Park, Auckland
        "Eden Park" -> GroundDetail(
            capacity = "50,000",
            establishedYear = "1900",
            matchesCount = "51",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "Drop-in pitch; shorter straight boundaries in white-ball; generally batting-friendly when hard.",
            pitchTagPrimary = "Balanced",
            conditionTagPrimary = "Drop-in",
            record1Type = "Highest Test total", record1Value = "-", record1Detail = "-",
            record2Type = "Lowest Test total",  record2Value = "-", record2Detail = "-",
            record3Type = "Best BBI Test", record3Value = "-", record3Detail = "-",
            record4Type = "Highest Score Test", record4Value = "-", record4Detail = "-",
            owner = "Eden Park Trust Board", openedYear = "1900", firstTestDate = "1930", capacityInfo = "50,000",
            history1 = "Eden Park in Auckland is New Zealand’s national stadium and a multi‑sport powerhouse. While globally famed for rugby, it has long hosted international cricket on a drop‑in pitch. The ground sits between Mount Eden and Kingsland, a few kilometres from the CBD. Its steep stands create an intense atmosphere and short straight boundaries in white‑ball games. Cricket usage has adapted to the stadium’s dual‑code design, with modern logistics enabling rapid changeovers. The venue has staged matches at ICC Cricket World Cups, including dramatic finishes. Its central city location ensures strong attendances and transport options.",
            history2 = "For cricket, the surface is typically hard and true, favouring batters once new‑ball movement subsides. Bowlers who hit the deck can extract pace and lift, especially under lights. The short straights invite power hitting, shaping T20 tactics markedly. Facilities for players and broadcasters reflect the ground’s global profile. Domestic and international fixtures are scheduled around rugby calendars, demanding operational precision. The stadium’s acoustics amplify crowd energy for marquee games. Eden Park remains a signature venue in New Zealand sport.",
            history3 = "Recent upgrades have improved concourses, accessibility and premium hospitality areas. Transport connectivity via rail and bus, plus walking routes, streamlines event days. Sustainability initiatives govern turf preparation and energy usage across seasons. Community programmes share facilities for school and club engagement. Heritage displays recognise historic rugby and cricket moments alike. As a dual‑purpose arena, Eden Park exemplifies flexible, modern stadium operations. It continues to deliver high‑octane cricket spectacles in Auckland.",
            referencesText = "Sources: Wikipedia (Eden Park)"
            , testMostRuns = "- , Avg: ",
            testMostWkts = "- , Inn- , Avg: ",
            odiMostRuns = "- , Avg: ",
            odiMostWkts = "- , Avg: , Eco: ",
            t20MostRuns = "- , Avg: , SR: ",
            t20MostWkts = "- , Avg: , Eco: "
        )

        // Basin Reserve, Wellington
        "Basin Reserve" -> GroundDetail(
            capacity = "11,600",
            establishedYear = "1868",
            matchesCount = "65",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "Windy venue; can aid swing and seam; flattens for batting with time.",
            pitchTagPrimary = "Seam-friendly",
            conditionTagPrimary = "Windy",
            record1Type = "Highest Test total", record1Value = "-", record1Detail = "-",
            record2Type = "Lowest Test total",  record2Value = "-", record2Detail = "-",
            record3Type = "Best BBI Test", record3Value = "-", record3Detail = "-",
            record4Type = "Highest Score Test", record4Value = "-", record4Detail = "-",
            owner = "Basin Reserve Trust", openedYear = "1868", firstTestDate = "1930", capacityInfo = "11,600",
            history1 = "The Basin Reserve in Wellington is New Zealand’s oldest dedicated cricket ground and a cherished landmark. Established in the 1860s, it sits within a traffic roundabout yet retains a village‑green aura. Test cricket arrived in 1930 and the ground has since hosted numerous milestones. The RA Vance Stand houses a museum celebrating New Zealand cricket heritage. Windy conditions frequently shape swing and seam, especially on opening days. The grass banks and timber features add to its heritage aesthetic. It remains the principal home of the Wellington Firebirds.",
            history2 = "Listed by Heritage New Zealand, the Basin is protected for its cultural and sporting significance. Pitches generally offer fair pace and bounce before wearing to involve spin. Domestic and international cricket coexist with community events at the venue. The outfield is quick when dry, accelerating scoring for set batters. Facilities have been progressively modernised while respecting historic structures. The ground’s intimate feel creates close interaction between players and spectators. Its place in Wellington’s sporting identity is enduring and unique.",
            history3 = "Event planning at the Basin accounts for weather, wind and the surrounding road network. Practice areas and player amenities support touring sides efficiently. Heritage interpretation and guided tours connect visitors with the venue’s past. Sustainability in turf and building maintenance aligns with city guidelines. The ground’s scale suits Test cricket and boutique limited‑overs fixtures. It continues to host memorable contests that resonate with local fans. The Basin Reserve stands as a living museum of New Zealand cricket.",
            referencesText = "Sources: Wikipedia (Basin Reserve)"
            , testMostRuns = "- , Avg: ",
            testMostWkts = "- , Inn- , Avg: ",
            odiMostRuns = "- , Avg: ",
            odiMostWkts = "- , Avg: , Eco: ",
            t20MostRuns = "- , Avg: , SR: ",
            t20MostWkts = "- , Avg: , Eco: "
        )

        // Bay Oval, Mount Maunganui
        "Bay Oval" -> GroundDetail(
            capacity = "10,000",
            establishedYear = "2005",
            matchesCount = "5",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "Generally flat surface aiding batting; slows later to bring spin into play.",
            pitchTagPrimary = "Balanced",
            conditionTagPrimary = "Humid Coastal",
            record1Type = "Highest Test total", record1Value = "-", record1Detail = "-",
            record2Type = "Lowest Test total",  record2Value = "-", record2Detail = "-",
            record3Type = "Best BBI Test", record3Value = "-", record3Detail = "-",
            record4Type = "Highest Score Test", record4Value = "-", record4Detail = "-",
            owner = "The Bay Oval Trust", openedYear = "2005", firstTestDate = "2019", capacityInfo = "10,000",
            history1 = "Bay Oval at Mount Maunganui is a contemporary cricket venue developed within the wider Blake Park complex. Opened in 2005, it rose quickly to international status with its first Test in 2019. The ground’s beachside city setting contributes to a relaxed, scenic match‑day experience. Modern stands and grass banks combine to maintain a community feel. Early in matches, batting is often rewarding on true surfaces and a fast outfield. Evening conditions can introduce dew, shaping white‑ball tactics. The venue has become a Bay of Plenty hub for elite and grassroots cricket.",
            history2 = "International fixtures across formats sit alongside domestic competitions and women’s matches. The square is prepared to offer fair pace initially with gradual wear for spin later. Training facilities and indoor nets support touring sides through variable coastal weather. Accessibility and parking are configured for growing crowds during peak seasons. Broadcast‑ready lighting and infrastructure support night games and global coverage. The ground’s operations emphasise spectator comfort and family‑friendly amenities. Bay Oval’s rise reflects New Zealand Cricket’s expansion of Test venues.",
            history3 = "Sustainability and turf care are priorities given sandy soils and marine climate. The venue’s footprint integrates walking paths and recreation areas of Blake Park. Community engagement includes school programmes and holiday events around major series. Premium hospitality options are balanced with open grass viewing for general admission. The ground’s relaxed identity complements the intensity of international cricket. As a relatively new Test host, it continues to refine event delivery. Bay Oval is now firmly on the international itinerary for touring teams.",
            referencesText = "Sources: Wikipedia (Bay Oval)"
            , testMostRuns = "- , Avg: ",
            testMostWkts = "- , Inn- , Avg: ",
            odiMostRuns = "- , Avg: ",
            odiMostWkts = "- , Avg: , Eco: ",
            t20MostRuns = "- , Avg: , SR: ",
            t20MostWkts = "- , Avg: , Eco: "
        )

        // Seddon Park, Hamilton
        "Seddon Park" -> GroundDetail(
            capacity = "10,000",
            establishedYear = "1950",
            matchesCount = "29",
            matchesLabel = "Tests",
            avgTest1st = "-", avgTest2nd = "-", avgTest3rd = "-", avgTest4th = "-",
            avgOdi1st = "-", avgOdi2nd = "-",
            avgT201st = "-", avgT202nd = "-",
            pitchSummary = "Typically slower, assisting spin as match goes; batting friendly when set.",
            pitchTagPrimary = "Spin-friendly",
            conditionTagPrimary = "Dry",
            record1Type = "Highest Test total", record1Value = "-", record1Detail = "-",
            record2Type = "Lowest Test total",  record2Value = "-", record2Detail = "-",
            record3Type = "Best BBI Test", record3Value = "-", record3Detail = "-",
            record4Type = "Highest Score Test", record4Value = "-", record4Detail = "-",
            owner = "Hamilton City Council", openedYear = "1950", firstTestDate = "1991", capacityInfo = "10,000",
            history1 = "Seddon Park in Hamilton is known for its circular, tree‑lined setting and relaxed banks. Built in the mid‑20th century, it has grown into a regular venue for all international formats. The ground’s dimensions and low stands generate a village‑green ambience. Batting conditions are generally friendly once the shine wears off the ball. Spin tends to become influential as matches progress on drier surfaces. Domestic fixtures for Northern Districts keep the square heavily utilised. The venue is valued by players for its sightlines and outfield quality.",
            history2 = "Pitch preparation typically aims for moderate pace and bounce with consistent carry. White‑ball contests often feature proactive batting and busy running between the wickets. Outfield speed encourages placement and power alike. Facilities have been upgraded to enhance player areas, media capability and spectator comfort. The ground is well integrated into Hamilton’s transport network on event days. Its hosting record includes ICC events alongside bilateral series. Community access and use remain part of its design ethos.",
            history3 = "Operational focus includes weather‑resilient drainage and maintenance for a busy calendar. Practice wickets and indoor spaces support touring teams efficiently. Hospitality and family zones provide a welcoming environment for diverse crowds. Sustainability considerations guide turf care and facility management. Heritage elements acknowledge the venue’s development since the 1950s. Seddon Park’s approachable scale makes it a favourite among New Zealand venues. It continues to deliver competitive cricket in a quintessential garden‑ground setting.",
            referencesText = "Sources: Wikipedia (Seddon Park)"
            , testMostRuns = "- , Avg: ",
            testMostWkts = "- , Inn- , Avg: ",
            odiMostRuns = "- , Avg: ",
            odiMostWkts = "- , Avg: , Eco: ",
            t20MostRuns = "- , Avg: , SR: ",
            t20MostWkts = "- , Avg: , Eco: "
        )

        else -> GroundDetail.placeholderFor(ground)
    }
}
