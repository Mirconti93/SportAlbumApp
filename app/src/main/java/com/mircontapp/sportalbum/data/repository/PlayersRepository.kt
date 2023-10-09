package com.mircontapp.sportalbum.data.repository

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.commons.PlayerHelper
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel

class PlayersRepository {
    val players: MutableList<PlayerModel> = ArrayList()

    fun playersFromTeam(teamName: String) : List<PlayerModel> {
        var playersList = ArrayList<PlayerModel>()
        this.players.forEach {
            if (it.team.equals(teamName)) {
                playersList.add(it)
            }
        }
        return playersList
    }

    fun playersFromTeamLegend(teamName: String): List<PlayerModel> {
        val playersList: MutableList<PlayerModel> = java.util.ArrayList<PlayerModel>()
        this.players.forEach {
            if (teamName.equals(it.teamLegend, ignoreCase = true)) {
                playersList.add(it)
            }
        }
        return PlayerHelper.sortPlayerByRole(playersList)
    }

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
    /*

    fun sortPlayerByRoleLegend(players: List<PlayerModel?>?): List<PlayerModel> {
        val orderedPlayers: List<PlayerModel> = ArrayList<PlayerModel>(players)
        Collections.sort(
            orderedPlayers,
            comparator)
        return orderedPlayers
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


    fun teamsFromArea(area: Enums.Area): List<TeamModel>? {
        val teams: MutableList<TeamModel> = ArrayList()
        for (team in teams) {
            if (team.area != null && area.equals(team.area)) {
                teams.add(team)
            }
        }
        return teams
    }





}