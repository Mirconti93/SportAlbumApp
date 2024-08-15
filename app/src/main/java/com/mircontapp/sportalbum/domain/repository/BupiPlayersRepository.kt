package com.mircontapp.sportalbum.domain.repository

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.domain.models.BupiPlayerModel
import com.mircontapp.sportalbum.domain.models.PlayerModel

interface BupiPlayersRepository {

    suspend fun getAllPlayers(): List<BupiPlayerModel>
    suspend fun playersFromTeam(teamName: String) : List<BupiPlayerModel>
    suspend fun updatePlayer(player: BupiPlayerModel)
}