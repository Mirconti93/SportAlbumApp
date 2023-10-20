package com.mircontapp.sportalbum.data.repository

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.commons.PlayerHelper
import com.mircontapp.sportalbum.data.datasource.AlbumDataSource
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.repository.PlayersRepository
import javax.inject.Inject


class PlayersRepositoryImpl(albumDataSource: AlbumDataSource): PlayersRepository {
    val players: MutableList<PlayerModel>

    init {
        players = albumDataSource.fetchPlayers()?.toMutableList() ?: ArrayList()
    }

    override fun getAllPlayers(): List<PlayerModel> {
        return players
    }

    override fun playersFromTeam(teamName: String) : List<PlayerModel> {
        return players.filter { it.team.equals(teamName) }
    }

    override fun playersFromTeamLegend(teamName: String): List<PlayerModel> {
        return players.filter { teamName.equals(it.teamLegend, ignoreCase = true) }
    }

    override fun playersFromNational(country: String, gender: Enums.Gender): List<PlayerModel> {
        return players.filter {country == it.country && gender == it.gender && it.national == 1}
    }

    override fun playersFromNationalLegend(country: String, gender: Enums.Gender): List<PlayerModel> {
        return players.filter { country == it.country && gender == it.gender && it.nationalLegend == 1 }
    }




}