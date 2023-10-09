package com.mircontapp.sportalbum.data.datasource

import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel

interface AlbumDataSource {
    fun fetchPlayers(): List<PlayerModel>?

    fun fetchTeams(): List<TeamModel>
}