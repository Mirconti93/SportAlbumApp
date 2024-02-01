package com.mircontapp.sportalbum.commons

import com.mirco.sportalbum.utils.Enums
import com.mirco.sportalbum.utils.Enums.MatchModule
import com.mirco.sportalbum.utils.Enums.RoleLineUp
import com.mircontapp.sportalbum.domain.models.PlayerModel


class PlayerHelper {

    companion object {
        fun findAreaEnum(keyText: String): Enums.Area {
            for (a in Enums.Area.values()) {
                if (keyText.equals(a.toString(), ignoreCase = true)) {
                    return a
                }
            }
            return Enums.Area.OTHER
        }

        fun roleFromString(roleString: String): Enums.Role? {
            for (role in Enums.Role.values()) {
                if (roleString.equals(role.toString(), ignoreCase = true)) {
                    return role
                }
            }
            return Enums.Role.PP
        }

        fun roleLineUpFromString(roleString: String?): Enums.RoleLineUp? {
            for (role in Enums.RoleLineUp.values()) {
                if (roleString.equals(role.toString(), ignoreCase = true)) {
                    return role
                }
            }
            return Enums.RoleLineUp.PPM
        }

        fun genderFromString(genderString: String?): Enums.Gender? {
            for (gender in Enums.Gender.values()) {
                if (genderString.equals(gender.toString(), ignoreCase = true)) {
                    return gender
                }
            }
            return Enums.Gender.OTHER
        }

        fun sortPlayerByRole(players: List<PlayerModel>): List<PlayerModel> {
            return players.sortedBy { it?.role }
        }

        val comparator = { p1: PlayerModel?, p2: PlayerModel? ->
            when {
                (p1 == null && p2 == null) -> 0
                (p1 == null) -> -1
                else -> {
                    when {
                        (p1.role == null) -> -1
                        (p2?.role == null) -> 1
                        (p1.role == p2.role) -> {
                            when {
                                (p1.value == null) -> -1
                                (p2.value == null) -> 1
                                else -> -p1.value.compareTo(p2.value)
                            }
                        }
                        else -> p1.role.compareTo(p2.role)
                    }
                }
            }
        }


    }

    /*fun getBestInRole(role: Enums.Role): PlayerModel? {
        var playerModel: PlayerModel? = null
        for (p in lineUpModel.getValue().getAvailables()) {
            if (role == p.role) {
                if (playerModel != null) {
                    val pVal: Int =
                        if (lineUpModel.getValue().isLegend()) p.getValueLegend() else p.value
                    val playerModelVal: Int = if (lineUpModel.getValue()
                            .isLegend()
                    ) playerModel.valueleg else playerModel.value
                    if (pVal > playerModelVal) {
                        playerModel = p
                    }
                }
            } else {
                playerModel = p
            }
        }
        if (playerModel == null) {
            playerModel = getBestPlayer(
                lineUpModel.getValue().getAvailables(),
                lineUpModel.getValue().isLegend()
            )
        }
        return playerModel
    }

    fun getBestPlayer(playerModels: List<PlayerModel>?, isLegend: Boolean): PlayerModel? {
        if (playerModels == null || playerModels.isEmpty()) {
            return null
        }
        var playerModel = playerModels[0]
        for (p in playerModels) {
            val pVal: Int = if (isLegend) p.valueleg?.toInt() ?: 0  else p.value?.toInt() ?: 0
            val playerModelVal: Int =
                if (isLegend) playerModel.valueleg?.toInt() ?: 0 else playerModel.value?.toInt() ?: 0
            if (pVal > playerModelVal) {
                playerModel = p
            }
        }
        return playerModel
    }

    fun composeBench(): List<PlayerModel>? {
        for (playerModel in playerModels) {
            playerModel.setRoleLineUp(RoleLineUp.PAN)
        }
        return playerModels
    }

    fun getDefaultRoles(): Array<RoleLineUp?> {
        return arrayOf(
            RoleLineUp.PTC,
            RoleLineUp.DCL,
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
    }

    fun getLineUpRoles(matchModule: MatchModule?): Array<RoleLineUp?> {
        return when (matchModule) {
            MatchModule.M442 -> arrayOf(
                RoleLineUp.PTC,
                RoleLineUp.DCL,
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
                RoleLineUp.DCL,
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
                RoleLineUp.DCL,
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

    fun getRoleLineUp(role: Enums.Role?): RoleLineUp? {
        var roleLineUp = RoleLineUp.PPM
        roleLineUp = when (role) {
            Enums.Role.PT -> RoleLineUp.PTC
            Enums.Role.DC -> RoleLineUp.DCS
            Enums.Role.TD -> RoleLineUp.TDD
            Enums.Role.TS -> RoleLineUp.TSD
            Enums.Role.MD -> RoleLineUp.MED
            Enums.Role.CC -> RoleLineUp.REG
            Enums.Role.AD -> RoleLineUp.ALD
            Enums.Role.AS -> RoleLineUp.ALS
            Enums.Role.SP -> RoleLineUp.SPP
            Enums.Role.TQ -> RoleLineUp.TRQ
            else -> RoleLineUp.PPM
        }
        return roleLineUp
    }

    fun composeLineUp(): List<PlayerModel>? {
        val players: MutableList<PlayerModel> = ArrayList()
        for (roleLineUp in getRolesLineUp()) {
            val p = findBestPlayerInRole(roleLineUp)
            if (p != null) {
                players.add(p)
            }
        }
        players.addAll(composeBench()!!)
        PlayerDataManager.sortPlayerByRoleLU(players)
        return players
    }

    fun changeModule(
        playerModels: List<PlayerModel>,
        matchModule: MatchModule?
    ): List<PlayerModel>? {
        val role = getLineUpRoles(matchModule)
        if (playerModels.size == 11 && role.size == 11) {
            for (i in playerModels.indices) {
                val p = playerModels[i]
                p.setRoleLineUp(role[i])
            }
        }
        return playerModels
    }

    fun findBestPlayerInRole(playerModels: List<PlayerModel>, roleLineUp: RoleLineUp): PlayerModel? {
        var playerModel: PlayerModel? = null
        if (playerModels == null || playerModels.isEmpty()) {
            return pl
        }
        for (i in 0 until playerModels.size()) {
            val p: PlayerModel = playerModels.get(i)
            if (p.roleLineUp == roleLineUp) {
                playerModel = p
                playerModels.remove(i)
                break
            }
        }
        if (playerModel == null) {
            for (i in 0 until playerModels.size()) {
                val p: PlayerModel = playerModels.get(i)
                if (p.role == generalRole(roleLineUp)) {
                    playerModel = p
                    playerModels.remove(i)
                    break
                }
            }
        }
        if (playerModel == null) {
            playerModel = playerModels.remove(0)
        }
        return playerModel
    }

    fun generalRole(roleLineUp: RoleLineUp?): Enums.Role {
        return when (roleLineUp) {
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
            RoleLineUp.ESD -> Enums.Role.AD
            RoleLineUp.ALD -> Enums.Role.AD
            RoleLineUp.ESS -> Enums.Role.AS
            RoleLineUp.ALS -> Enums.Role.AS
            RoleLineUp.SPP -> Enums.Role.SP
            RoleLineUp.PPM -> Enums.Role.PP
            RoleLineUp.CAV -> Enums.Role.PP
            else -> Enums.Role.PP
        }
    }*/



}