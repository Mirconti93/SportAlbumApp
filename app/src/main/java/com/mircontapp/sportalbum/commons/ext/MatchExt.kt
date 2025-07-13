package com.mircontapp.sportalbum.commons.ext

import android.util.Log
import com.mirco.sportalbum.utils.Enums
import com.mirco.sportalbum.utils.Enums.MatchModule
import com.mirco.sportalbum.utils.Enums.Role
import com.mirco.sportalbum.utils.Enums.RoleLineUp
import com.mircontapp.sportalbum.commons.AlbumHelper.Companion.getDefaultRoles
import com.mircontapp.sportalbum.domain.models.PlayerMatchModel
import com.mircontapp.sportalbum.domain.models.PlayerModel

fun MatchModule?.getLineUpGenericRoles(): Array<Role> {
    return when (this) {
        MatchModule.M442 -> arrayOf(
            Role.PT,
            Role.DC,
            Role.DC,
            Role.TD,
            Role.TS,
            Role.MD,
            Role.CC,
            Role.AD,
            Role.AS,
            Role.SP,
            Role.PP
        )

        MatchModule.M433 -> arrayOf(
            Role.PT,
            Role.DC,
            Role.DC,
            Role.TD,
            Role.TS,
            Role.MD,
            Role.CC,
            Role.TQ,
            Role.SP,
            Role.SP,
            Role.PP
        )

        MatchModule.M451 -> arrayOf(
            Role.PT,
            Role.DC,
            Role.DC,
            Role.TD,
            Role.TS,
            Role.MD,
            Role.CC,
            Role.TQ,
            Role.AD,
            Role.AS,
            Role.PP
        )

        MatchModule.M4231 -> arrayOf(
            Role.PT,
            Role.DC,
            Role.DC,
            Role.TD,
            Role.TS,
            Role.MD,
            Role.CC,
            Role.TQ,
            Role.AD,
            Role.SP,
            Role.PP
        )

        MatchModule.M4312 -> arrayOf(
            Role.PT,
            Role.DC,
            Role.DC,
            Role.TD,
            Role.TS,
            Role.MD,
            Role.CC,
            Role.CC,
            Role.TQ,
            Role.SP,
            Role.PP
        )

        MatchModule.M541 -> arrayOf(
            Role.PT,
            Role.DC,
            Role.DC,
            Role.DC,
            Role.TD,
            Role.TS,
            Role.MD,
            Role.CC,
            Role.AD,
            Role.AS,
            Role.PP
        )

        MatchModule.M532 -> arrayOf(
            Role.PT,
            Role.DC,
            Role.DC,
            Role.DC,
            Role.TD,
            Role.TS,
            Role.MD,
            Role.CC,
            Role.CC,
            Role.PP,
            Role.PP
        )

        MatchModule.M3511 -> arrayOf(
            Role.PT,
            Role.DC,
            Role.DC,
            Role.DC,
            Role.TD,
            Role.TS,
            Role.MD,
            Role.CC,
            Role.CC,
            Role.SP,
            Role.PP
        )

        MatchModule.M352 -> arrayOf(
            Role.PT,
            Role.DC,
            Role.DC,
            Role.DC,
            Role.MD,
            Role.CC,
            Role.CC,
            Role.TD,
            Role.TS,
            Role.SP,
            Role.PP
        )

        MatchModule.M343 -> arrayOf(
            Role.PT,
            Role.DC,
            Role.DC,
            Role.DC,
            Role.MD,
            Role.CC,
            Role.TD,
            Role.TS,
            Role.SP,
            Role.SP,
            Role.PP
        )

        MatchModule.M3412 -> arrayOf(
            Role.PT,
            Role.DC,
            Role.DC,
            Role.DC,
            Role.MD,
            Role.CC,
            Role.TD,
            Role.TS,
            Role.TQ,
            Role.SP,
            Role.PP
        )

        MatchModule.M3313 -> arrayOf(
            Role.PT,
            Role.DC,
            Role.DC,
            Role.DC,
            Role.MD,
            Role.CC,
            Role.CC,
            Role.TQ,
            Role.AD,
            Role.AS,
            Role.PP
        )

        else -> arrayOf(
            Role.PT,
            Role.DC,
            Role.DC,
            Role.TD,
            Role.TS,
            Role.MD,
            Role.CC,
            Role.AD,
            Role.AS,
            Role.SP,
            Role.PP
        )
    }
}

fun MatchModule?.getLineUpRoles(): Array<RoleLineUp> {
    return when (this) {
        MatchModule.M442 -> arrayOf(
            RoleLineUp.PTC,
            RoleLineUp.DCS,
            RoleLineUp.DCS,
            RoleLineUp.TDD,
            RoleLineUp.TSD,
            RoleLineUp.MED,
            RoleLineUp.REG,
            RoleLineUp.ALD,
            RoleLineUp.ALS,
            RoleLineUp.SPP,
            RoleLineUp.PPM
        )

        MatchModule.M433 -> arrayOf(
            RoleLineUp.PTC,
            RoleLineUp.DCS,
            RoleLineUp.DCS,
            RoleLineUp.TDD,
            RoleLineUp.TSD,
            RoleLineUp.MED,
            RoleLineUp.REG,
            RoleLineUp.TRQ,
            RoleLineUp.SPP,
            RoleLineUp.SPP,
            RoleLineUp.PPM
        )

        MatchModule.M451 -> arrayOf(
            RoleLineUp.PTC,
            RoleLineUp.DCL,
            RoleLineUp.DCS,
            RoleLineUp.TDD,
            RoleLineUp.TSD,
            RoleLineUp.MED,
            RoleLineUp.REG,
            RoleLineUp.TRQ,
            RoleLineUp.ALD,
            RoleLineUp.ALS,
            RoleLineUp.PPM
        )

        MatchModule.M4231 -> arrayOf(
            RoleLineUp.PTC,
            RoleLineUp.DCS,
            RoleLineUp.DCS,
            RoleLineUp.TDD,
            RoleLineUp.TSD,
            RoleLineUp.MED,
            RoleLineUp.REG,
            RoleLineUp.TRQ,
            RoleLineUp.ALD,
            RoleLineUp.SPP,
            RoleLineUp.CAV
        )

        MatchModule.M4312 -> arrayOf(
            RoleLineUp.PTC,
            RoleLineUp.DCL,
            RoleLineUp.DCS,
            RoleLineUp.TDD,
            RoleLineUp.TSD,
            RoleLineUp.MED,
            RoleLineUp.MSP,
            RoleLineUp.MZL,
            RoleLineUp.TRQ,
            RoleLineUp.SPP,
            RoleLineUp.PPM
        )

        MatchModule.M541 -> arrayOf(
            RoleLineUp.PTC,
            RoleLineUp.DCL,
            RoleLineUp.DCS,
            RoleLineUp.DCL,
            RoleLineUp.TDD,
            RoleLineUp.TSD,
            RoleLineUp.MED,
            RoleLineUp.REG,
            RoleLineUp.ALD,
            RoleLineUp.ALS,
            RoleLineUp.CAV
        )

        MatchModule.M532 -> arrayOf(
            RoleLineUp.PTC,
            RoleLineUp.DCL,
            RoleLineUp.DCS,
            RoleLineUp.DCL,
            RoleLineUp.TDD,
            RoleLineUp.TSD,
            RoleLineUp.MED,
            RoleLineUp.REG,
            RoleLineUp.MZL,
            RoleLineUp.SPP,
            RoleLineUp.CAV
        )

        MatchModule.M352 -> arrayOf(
            RoleLineUp.PTC,
            RoleLineUp.DCL,
            RoleLineUp.DCS,
            RoleLineUp.DCL,
            RoleLineUp.MED,
            RoleLineUp.REG,
            RoleLineUp.MSP,
            RoleLineUp.ESD,
            RoleLineUp.ESS,
            RoleLineUp.SPP,
            RoleLineUp.CAV
        )

        MatchModule.M343 -> arrayOf(
            RoleLineUp.PTC,
            RoleLineUp.DCL,
            RoleLineUp.DCS,
            RoleLineUp.DCL,
            RoleLineUp.MED,
            RoleLineUp.REG,
            RoleLineUp.ESD,
            RoleLineUp.ESS,
            RoleLineUp.SPP,
            RoleLineUp.SPP,
            RoleLineUp.CAV
        )

        MatchModule.M3412 -> arrayOf(
            RoleLineUp.PTC,
            RoleLineUp.DCL,
            RoleLineUp.DCS,
            RoleLineUp.DCL,
            RoleLineUp.MED,
            RoleLineUp.REG,
            RoleLineUp.ESD,
            RoleLineUp.ESS,
            RoleLineUp.TRQ,
            RoleLineUp.SPP,
            RoleLineUp.PPM
        )

        MatchModule.M3313 -> arrayOf(
            RoleLineUp.PTC,
            RoleLineUp.DCL,
            RoleLineUp.DCS,
            RoleLineUp.DCL,
            RoleLineUp.MED,
            RoleLineUp.REG,
            RoleLineUp.MZL,
            RoleLineUp.TRQ,
            RoleLineUp.ALD,
            RoleLineUp.ALS,
            RoleLineUp.PPM
        )

        else -> getDefaultRoles()
    }
}

fun List<PlayerModel>.getBestPlayer(isLegend: Boolean): PlayerModel? {
    if (this == null || this.isEmpty()) {
        return null
    }
    var playerModel = this[0]
    for (p in this) {
        val pVal: Int = if (isLegend) p.valueleg?.toInt() ?: 0  else p.value?.toInt() ?: 0
        val playerModelVal: Int =
            if (isLegend) playerModel.valueleg?.toInt() ?: 0 else playerModel.value?.toInt() ?: 0
        if (pVal > playerModelVal) {
            playerModel = p
        }
    }
    return playerModel
}

fun List<PlayerMatchModel>.findBestPlayerInRole(role: Role): PlayerMatchModel? {
    var playerModel: PlayerMatchModel? = null

    if (isEmpty()) return playerModel

    /*Log.i("BUPI", "findBestPlayerInRole")
    var vBest = 0;
    for (p in this) {
        val vp = (if (isLegend) p.valueleg else p.value) ?: 0
        Log.i("BUPI", "vp: " + vp + " vBest: " + vBest)
        if (p.roleLineUp == roleLineUp && vp > vBest) {
            playerModel = p
            vBest = (if (isLegend) playerModel.valueleg else playerModel.value) ?: 0
            Log.i("BUPI", p.name + " " + playerModel.valueleg.toString())

        }
    }*/

    var vBest = 0;
    forEach { p->
        if (p.role == role && p.value > vBest) {
            playerModel = p
            vBest = p.value
        }
    }

    if (playerModel == null) {
        playerModel = get(0)
    }
    return playerModel
}

fun PlayerMatchModel.isPortiere(): Boolean {
    return this.roleMatch == RoleLineUp.PTC || this.roleMatch == RoleLineUp.PTM
}

fun PlayerMatchModel.getMatchValue(isLegend: Boolean): Double {
    val value = if (isLegend) this.valueleg?.toDouble() else this.value?.toDouble()
    return value ?: 0.0
}

fun RoleLineUp.generalRole(): Role {
    return when (this) {
        RoleLineUp.PTC -> Enums.Role.PT
        RoleLineUp.PTM -> Enums.Role.PT
        RoleLineUp.DCS -> Enums.Role.DC
        RoleLineUp.DCL -> Enums.Role.DC
        RoleLineUp.TDD -> Enums.Role.TD
        RoleLineUp.TDO -> Enums.Role.TD
        RoleLineUp.TSD -> Enums.Role.TS
        RoleLineUp.TSO -> Enums.Role.TS
        RoleLineUp.MED -> Enums.Role.MD
        RoleLineUp.MZL -> Enums.Role.CC
        RoleLineUp.MSP -> Enums.Role.MD
        RoleLineUp.TRQ -> Enums.Role.TQ
        RoleLineUp.ESD -> Enums.Role.TD
        RoleLineUp.ALD -> Enums.Role.AD
        RoleLineUp.ESS -> Enums.Role.TS
        RoleLineUp.ALS -> Enums.Role.AS
        RoleLineUp.SPP -> Enums.Role.SP
        RoleLineUp.PPM -> Enums.Role.PP
        RoleLineUp.CAV -> Enums.Role.PP
        else -> Enums.Role.PP
    }
}

fun Role.getRoleLineUp(): RoleLineUp {
    return when (this) {
        Role.PT -> RoleLineUp.PTC
        Role.DC -> RoleLineUp.DCS
        Role.TD -> RoleLineUp.TDD
        Role.TS -> RoleLineUp.TSD
        Role.MD -> RoleLineUp.MED
        Role.CC -> RoleLineUp.REG
        Role.AD -> RoleLineUp.ALD
        Role.AS -> RoleLineUp.ALS
        Role.SP -> RoleLineUp.SPP
        Role.TQ -> RoleLineUp.TRQ
        else -> RoleLineUp.PPM
    }
}

fun List<PlayerMatchModel>.findTiratore(): Int {
    var index = 0
    var mRig = 0.0
    for (i in this.indices) {
        val p: PlayerMatchModel = this[i]
        if (!p.isEspulso && p.rig >= mRig) {
            index = i
            mRig = p.rig
        }
    }
    return index
}

fun List<PlayerMatchModel>.findGoalkeeper(): PlayerMatchModel {
    return this.find { it.isPortiere()} ?: this.get(0)
}

fun PlayerMatchModel.partecipa(partRole: Double): Boolean {
    if (this.isEspulso) return false
    return (Math.random()*100.0).let {
        return (it < this.energy && it > partRole)
    }
}