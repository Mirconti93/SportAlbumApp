package com.mircontapp.sportalbum.commons

import com.mirco.sportalbum.utils.Enums
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
            val pVal: Int = if (isLegend) p.getValueLegend() else p.value
            val playerModelVal: Int =
                if (isLegend) playerModel.getValueLegend() else playerModel.value
            if (pVal > playerModelVal) {
                playerModel = p
            }
        }
        return playerModel
    }*/



}