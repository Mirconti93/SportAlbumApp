package com.mirco.sportalbum.utils

import com.mircontapp.sportalbum.R

class Enums {
    enum class Area(val text: String) {
        SERIEA("Serie A"), SERIEB("Serie B"), PREMIERLEAGUE("Premier League"), LIGA("Liga"), BUNDESLIGA("Bundesliga"), LIGUE1("League 1"), OTHER("Other"), CLUBFEMMINILI("Women clubs"), NAZIONALI("Nationals"), NAZIONALIFEMMINILI("Women nationals")
    }

    enum class Role(val code: Int, override val text: Int) : EnumWriteble {
        PT(R.string.pt, R.string.goalkeeper),
        DC(R.string.dc, R.string.centralBack),
        TD(
            R.string.td,
            R.string.rightBack
        ),
        TS(R.string.ts, R.string.leftBack),
        MD(
            R.string.md,
            R.string.defensiveMidfielder
        ),
        CC(R.string.cc, R.string.centralMidfielder),
        TQ(
            R.string.tq,
            R.string.advancedMidfielder
        ),
        ES(
            R.string.md,
            R.string.defensiveMidfielder
        ),
        AD(R.string.ad, R.string.rightWing), AS(R.string.`as`, R.string.leftWing), SP(
            R.string.sp,
            R.string.centralForward
        ),
        PP(R.string.pp, R.string.stricker), ALL(R.string.all, R.string.all);

    }

    enum class RoleLineUp(
        override val text: Int,
        var att: Int,
        var dif: Int,
        var tec: Int,
        var dri: Int,
        var fin: Int,
        var bal: Int,
        var fis: Int,
        var vel: Int,
        var rig: Int,
        var por: Int,
        var partDif: Int,
        var partCen: Int,
        var partAtt: Int,
        partfin: Int
    ) :
        EnumWriteble {
        PTC(
            R.string.ptc,
            31,
            92,
            55,
            34,
            31,
            83,
            67,
            85,
            31,
            100,
            30,
            92,
            98,
            99
        ),
        PTM(R.string.ptm, 32, 95, 62, 35, 32, 80, 64, 92, 32, 95, 25, 85, 95, 99), DCL(
            R.string.dcl,
            47,
            100,
            58,
            41,
            62,
            95,
            88,
            85,
            64,
            31,
            10,
            35,
            55,
            65
        ),
        DCS(
            R.string.dcs,
            50,
            95,
            70,
            45,
            65,
            100,
            92,
            75,
            68,
            31,
            15,
            30,
            55,
            65
        ),
        TDD(R.string.tdd, 55, 92, 75, 70, 50, 75, 85, 95, 50, 31, 10, 45, 55, 65), TDO(
            R.string.tdo,
            64,
            88,
            83,
            80,
            62,
            65,
            80,
            100,
            58,
            31,
            20,
            35,
            55,
            65
        ),
        TSD(R.string.tsd, 55, 92, 75, 70, 50, 75, 85, 95, 50, 31, 10, 45, 55, 65), TSO(
            R.string.tso,
            64,
            88,
            83,
            80,
            62,
            65,
            80,
            100,
            58,
            31,
            20,
            35,
            55,
            65
        ),
        MED(
            R.string.med,
            55,
            92,
            75,
            55,
            62,
            92,
            100,
            77,
            77,
            31,
            25,
            30,
            50,
            75
        ),
        REG(
            R.string.reg,
            65,
            68,
            100,
            68,
            80,
            62,
            75,
            68,
            95,
            31,
            45,
            10,
            40,
            50
        ),
        MSP(R.string.msp, 75, 70, 80, 65, 85, 75, 92, 85, 70, 31, 40, 25, 35, 40), MZL(
            R.string.mzl,
            70,
            75,
            92,
            70,
            75,
            68,
            80,
            75,
            75,
            31,
            50,
            20,
            40,
            50
        ),
        TRQ(
            R.string.trq,
            92,
            58,
            100,
            92,
            80,
            50,
            55,
            71,
            95,
            31,
            75,
            20,
            20,
            45
        ),
        ESD(R.string.esd, 80, 65, 85, 88, 75, 73, 80, 95, 75, 31, 50, 25, 40, 55), ALD(
            R.string.ald,
            92,
            50,
            85,
            100,
            85,
            65,
            65,
            85,
            75,
            31,
            65,
            40,
            30,
            40
        ),
        ESS(R.string.ess, 80, 65, 85, 88, 75, 73, 80, 95, 75, 31, 50, 25, 40, 55), ALS(
            R.string.als,
            92,
            50,
            85,
            100,
            85,
            65,
            65,
            85,
            75,
            31,
            65,
            40,
            30,
            40
        ),
        SPP(
            R.string.spp,
            100,
            45,
            85,
            92,
            92,
            71,
            68,
            75,
            95,
            31,
            85,
            40,
            10,
            20
        ),
        PPM(
            R.string.ppm,
            95,
            31,
            85,
            82,
            100,
            80,
            70,
            82,
            95,
            31,
            95,
            55,
            20,
            10
        ),
        CAV(
            R.string.cav,
            92,
            35,
            77,
            70,
            95,
            92,
            88,
            70,
            100,
            31,
            92,
            70,
            20,
            10
        ),
        PAN(
            R.string.pan,
            50,
            50,
            50,
            50,
            50,
            50,
            50,
            50,
            50,
            50,
            100,
            100,
            100,
            100
        ),
        TRI(R.string.tri, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 100, 100, 100, 100);

        var partfin: Int
        fun getAtt(): Double {
            return att.toDouble()
        }

        fun getDif(): Double {
            return dif.toDouble()
        }

        fun getTec(): Double {
            return tec.toDouble()
        }

        fun getDri(): Double {
            return dri.toDouble()
        }

        fun getFin(): Double {
            return fin.toDouble()
        }

        fun getBal(): Double {
            return bal.toDouble()
        }

        fun getFis(): Double {
            return fis.toDouble()
        }

        fun getVel(): Double {
            return vel.toDouble()
        }

        fun getRig(): Double {
            return rig.toDouble()
        }

        fun getPor(): Double {
            return por.toDouble()
        }

        fun getPartDif(): Double {
            return partDif.toDouble()
        }

        fun getPartCen(): Double {
            return partCen.toDouble()
        }

        fun getPartAtt(): Double {
            return partAtt.toDouble()
        }

        fun getPartfin(): Double {
            return partfin.toDouble()
        }

        init {
            this.partfin = partAtt
        }
    }

    enum class PlayStyle(
        val att: Int,
        val dif: Int,
        val tec: Int,
        val dri: Int,
        val fin: Int,
        val bal: Int,
        val fis: Int,
        val vel: Int,
        val rig: Int,
        val por: Int
    ) {
        NORMAL(50, 50, 50, 50, 50, 50, 50, 50, 50, 50),
        FUORICLASSE(95, 35, 85, 95, 97, 35, 35, 88, 85, 25),
        RAPACE(35, 35, 35, 35, 35, 35, 35, 35, 35, 25),
        BOA(35, 35, 35, 35, 35, 35, 35, 35, 35, 25),
        BOMBER(35, 35, 35, 35, 35, 35, 35, 35, 35, 25)

    }

    enum class Gender(override val text: Int) : EnumWriteble {
        M(R.string.men), F(R.string.women), OTHER(R.string.all);

    }

    enum class PlayerStatus(override val text: Int) : EnumWriteble {
        ACTUAL(R.string.actual), LEGENDS(R.string.legends), ALL(R.string.all);

    }

    enum class TeamType(override val text: Int) : EnumWriteble {
        CLUB(R.string.club), CLUB_FEMMINILE(R.string.clubFemminile), NAZIONALE(R.string.nazionale), NAZIONALE_FEMMINILE(
            R.string.nazionaleFemminile
        );

    }

    enum class TeamColor(override val text: Int, val color: Int) :
        EnumWriteble {
        WHITE(R.string.bianco, R.color.white), BLACK(
            R.string.nero,
            R.color.black
        ),
        BLUE(R.string.blu, R.color.blue3), LIGHT_BLUE(
            R.string.azzurro,
            R.color.lightblue1
        ),
        RED(R.string.rosso, R.color.red1), GREEN(
            R.string.verde,
            R.color.green1
        ),
        YELLOW(R.string.giallo, R.color.yellow2), ORANGE(
            R.string.arancione,
            R.color.orange1
        ),
        PURPLE(R.string.viola, R.color.purple), PINK(
            R.string.rosa,
            R.color.pink
        ),
        BROWN(R.string.marrone, R.color.brown), GRAY(R.string.grigio, R.color.gray2);

    }

    enum class IntegerBoolean(override val text: Int, val num: Int, val isYes: Boolean) :
        EnumWriteble {
        YES(R.string.yes, 1, true), NO(R.string.no, 0, false);

        companion object {
            fun getInstance(b: Boolean): IntegerBoolean {
                return if (b) YES else NO
            }

            fun getInstance(num: Int): IntegerBoolean {
                return if (num == 1) YES else NO
            }

            fun left(): IntegerBoolean {
                return NO
            }

            fun right(): IntegerBoolean {
                return YES
            }
        }
    }

    enum class PlayerSource {
        CHOICE, RANDOM
    }

    interface EnumWriteble {
        val text: Int
    }

    enum class LineUpModule {
        P442, P433, P4231, P352
    }

    enum class Evento {
        NONE, PALLEGGIO, PRESSING, ATTACCO, RECUPERO, RESPINTA, PARATA, AMMONIZIONE, DOPPIA_AMMONIZIONE, ESPULSIONE, RIGORE, PUNIZIONE, PUNIZIONE_DIRETTA, GOAL, GOAL_RIGORE, GOAL_PUNIZIONE, TIRO
    }

    enum class Fase {
        CENTROCAMPO, ATTACCO, CONCLUSIONE, PUNIZIONE, RIGORE
    }

    enum class TeamPosition {
        HOME, AWAY
    }

    enum class LineUpChoice {
        FIELD, BENCH
    }

    enum class MatchType {
        SIMPLE_MATCH, PENALTIES
    }

    enum class MatchModule(override val text: Int) : EnumWriteble {
        M442(R.string.m442), M433(R.string.m433), M4231(R.string.m4231), M451(R.string.m451), M4312(
            R.string.m4312
        ),
        M532(R.string.m532), M541(R.string.m541), M352(R.string.m352), M3511(R.string.m3511), M343(R.string.m343), M3412(R.string.m3412), M3313(
            R.string.m3313
        );

    }

    enum class FeminineTag(val text: String) {
        FEMMINILE("_femminile"), FEMININE("_feminine"), LADIES("_ladies"), FRAUEN("_frauen"), FEMINI(
            "_femenì"
        ),
        FEMININO("_feminino"), FEMENINO("_femenino"), WOMEN("_women"), CF("_cf");

    }

    enum class UpdateType { NEW, UPDATE }

    enum class GameScreen {
        LINE_UP_START, MATCH, LINE_UP
    }
}