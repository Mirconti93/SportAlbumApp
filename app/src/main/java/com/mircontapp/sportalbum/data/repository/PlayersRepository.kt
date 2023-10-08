package com.mircontapp.sportalbum.data.repository

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.domain.models.PlayerModel
import java.util.Collections

class PlayersRepository {
    val players: MutableList<PlayerModel> = ArrayList()
    val FILE_NAME = "players.txt"

    fun playersFromTeam(teamName: String) : List<PlayerModel> {
        var playersList = ArrayList<PlayerModel>()
        this.players.forEach {
            if (it.team.equals(teamName)) {
                playersList.add(it)
            }
        }
        return playersList
    }

    //todo
    /*
    fun playersFromTeamLegend(teamName: String): List<PlayerModel> {
        val playersList: MutableList<PlayerModel> = java.util.ArrayList<PlayerModel>()
        this.players.forEach {
            if (teamName.equals(it.teamLegend, ignoreCase = true)) {
                playersList.add(it)
            }
        }
        return sortPlayerByRole(playersList)
    }*/

    fun playersFromNational(country: String, gender: Enums.Gender): List<PlayerModel> {
        val playersList: MutableList<PlayerModel> = java.util.ArrayList<PlayerModel>()
        this.players.forEach {
            if (country == it.country && gender.name == it.Gender && it.national === 1) {
                playersList.add(it)
            }
        }
        return playersList
    }

    fun playersFromNationalLegend(country: String, gender: Enums.Gender): List<PlayerModel> {
        val playersList: MutableList<PlayerModel> = java.util.ArrayList<PlayerModel>()
        this.players.forEach{
            if (country == it.country && gender.name == it.Gender && it.nationalLegend === 1) {
                playersList.add(it)
            }
        }
        return playersList
    }

    //todo
    /*fun sortPlayerByRole(players: List<PlayerModel?>?): List<PlayerModel> {
        val orderedPlayers: MutableList<PlayerModel> = ArrayList<PlayerModel>(players)
        Collections.sort(
            orderedPlayers,
            comparator
        )
        return orderedPlayers
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

    fun sortPlayerByRoleLegend(players: List<PlayerModel?>?): List<PlayerModel> {
        val orderedPlayers: List<PlayerModel> = ArrayList<PlayerModel>(players)
        Collections.sort(
            orderedPlayers,
            comparator)
        return orderedPlayers
    }

    fun playerFactory(row: String) : PlayerModel {
        val fields = row.split("_")
        if (fields.size>=12) {
            return PlayerModel(
                fields[0], DataManager.roleFromString(fields[1]) ?: Enums.Role.PP,
                fields[2],
                fields[3],
                fields[4],
                fields[5],
                fields[6],
                fields[7],
                fields[8],
                Integer.parseInt(fields[9]),
                Integer.parseInt(fields[10]),
                fields[11]
            )
        } else {
            return PlayerModel(fields[0], Enums.Role.PP, null, null, null, null, null, null, null, null, null, null)
        }

    }

    fun playersFromTeamByRole(teamName: String) : List<PlayerModel> {
        return playersFromTeam(teamName).also {
            Collections.sort(it, object : Comparator<PlayerModel> {
                override fun compare(a: PlayerModel, b: PlayerModel): Int {

                    return a.role!!.compareTo(b.role!!)

                }}
            )
        }
    }*/
}