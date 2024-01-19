package com.mircontapp.sportalbum.data.datasource

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.commons.PlayerHelper
import com.mircontapp.sportalbum.commons.TeamHelper
import com.mircontapp.sportalbum.data.database.AppDatabase
import com.mircontapp.sportalbum.data.database.Player
import com.mircontapp.sportalbum.data.database.PlayerDao
import com.mircontapp.sportalbum.data.database.Team
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel


class DatabaseDataSource : AlbumDataSource {
    val players: MutableList<PlayerModel> = ArrayList()
    val teams: MutableList<TeamModel> = ArrayList()
    val database: AppDatabase?

    init {
       database = AppDatabase.getInstance(SportAlbumApplication.instance.applicationContext)
    }

    override suspend fun fetchPlayers(): List<PlayerModel>? {
        if (players.isEmpty()) {
            database?.playerDao()?.getAll()?.forEach { player ->
                players.add(DataMapper.playerModelFromEntity(player))
            }
        }
        return players
    }

    override suspend fun fetchTeams(): List<TeamModel>? {
        if (teams.isEmpty()) {
            database?.teamDao()?.getAll()?.forEach {
                    team ->teams.add(DataMapper.teamModelFromEntity(team))
            }
        }

        return teams
    }

    fun insertAllTeams(teams: List<TeamModel>?) {
        val teamsEntities = ArrayList<Team>()
        teams?.forEach { teamsEntities.add(DataMapper.entityFromTeamModel(it)) }
        database?.teamDao()?.insertAll(teamsEntities)
    }

    fun insertAllPlayers(players: List<PlayerModel>?) {
        val playerEntities = ArrayList<Player>()
        players?.forEach { playerEntities.add(DataMapper.entityFromPlayerModel(it)) }
        database?.playerDao()?.insertAll(playerEntities)
    }


}