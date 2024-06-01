package com.mircontapp.sportalbum.domain.repository

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.domain.models.PlayerModel

interface BupiPlayersRepository {

    suspend fun getAllPlayers(): List<PlayerModel>
    suspend fun playersFromTeam(teamName: String) : List<PlayerModel>
    suspend fun insertPlayer(playerModel: PlayerModel)
    suspend fun updatePlayer(playerModel: PlayerModel)
}