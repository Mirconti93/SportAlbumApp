package com.mircontapp.sportalbum.data.repository

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.domain.datasource.AlbumDataSource
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.repository.PlayersRepository


class PlayersRepositoryImpl(val albumDataSource: AlbumDataSource): PlayersRepository {

    override suspend fun getAllPlayers(): List<PlayerModel> {
        return albumDataSource.fetchPlayers()?.toList() ?: ArrayList()
    }

    override suspend fun playersFromTeam(teamName: String) : List<PlayerModel> {
        return getAllPlayers().filter { it.team.equals(teamName) }
    }

    override suspend fun playersFromTeamLegend(teamName: String): List<PlayerModel> {
        return getAllPlayers().filter { teamName.equals(it?.teamLegend) }
    }

    override suspend fun playersFromNational(country: String, gender: Enums.Gender): List<PlayerModel> {
        return getAllPlayers().filter {country == it.country && gender == it.gender && it.national == 1}
    }

    override suspend fun playersFromNationalLegend(country: String, gender: Enums.Gender): List<PlayerModel> {
        return getAllPlayers().filter { country == it.country && gender == it.gender && it.nationalLegend == 1 }
    }

    override suspend fun insertPlayer(playerModel: PlayerModel) {
        albumDataSource.insertPlayer(playerModel)
    }

    override suspend fun updatePlayer(playerModel: PlayerModel) {
        albumDataSource.updatePlayer(playerModel)
    }


}