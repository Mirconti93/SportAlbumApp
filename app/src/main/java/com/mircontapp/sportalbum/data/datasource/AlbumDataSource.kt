package com.mircontapp.sportalbum.data.datasource

import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel

interface AlbumDataSource {
    abstract suspend fun fetchPlayers(): List<PlayerModel>?

    abstract suspend fun fetchTeams(): List<TeamModel>?

}