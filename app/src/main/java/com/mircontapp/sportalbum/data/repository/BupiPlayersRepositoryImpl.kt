package com.mircontapp.sportalbum.data.repository

import com.mircontapp.sportalbum.domain.datasource.BupiDataSource
import com.mircontapp.sportalbum.domain.models.BupiPlayerModel
import com.mircontapp.sportalbum.domain.repository.BupiPlayersRepository


class BupiPlayersRepositoryImpl(val bupiDataSource: BupiDataSource): BupiPlayersRepository {

    override suspend fun getAllPlayers(): List<BupiPlayerModel> {
        return bupiDataSource.fetchBupiPlayers() ?: emptyList()
    }

    override suspend fun playersFromTeam(teamName: String) : List<BupiPlayerModel> {
        return getAllPlayers().filter { it.team.equals(teamName) }
    }

    override suspend fun insertPlayer(bupiPlayerModel: BupiPlayerModel) {
        bupiDataSource.insertBupiPlayer(bupiPlayerModel)
    }

    override suspend fun updatePlayer(bupiPlayerModel: BupiPlayerModel) {
        bupiDataSource.updateBupiPlayer(bupiPlayerModel)
    }


}