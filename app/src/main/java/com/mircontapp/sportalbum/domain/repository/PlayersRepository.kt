package com.mircontapp.sportalbum.domain.repository

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.domain.models.PlayerModel

interface PlayersRepository {

    fun getAllPlayers(): List<PlayerModel>
    fun playersFromTeam(teamName: String) : List<PlayerModel>
    fun playersFromTeamLegend(teamName: String): List<PlayerModel>
    fun playersFromNational(country: String, gender: Enums.Gender): List<PlayerModel>
    fun playersFromNationalLegend(country: String, gender: Enums.Gender): List<PlayerModel>
}