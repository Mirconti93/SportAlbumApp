package com.mircontapp.sportalbum.data.datasource

import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel

interface AlbumDataSource {
    abstract suspend fun fetchPlayers(): List<PlayerModel>?

    abstract suspend fun fetchTeams(): List<TeamModel>?

    abstract suspend fun insertPlayer(playerModel: PlayerModel)
    abstract suspend fun updatePlayer(playerModel: PlayerModel)

    abstract suspend fun updateTeam(teamModel: TeamModel)
    abstract suspend fun insertTeam(teamModel: TeamModel)

}