package com.mircontapp.sportalbum.data.datasource

import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.data.database.AppDatabase
import com.mircontapp.sportalbum.data.database.BupiPlayer
import com.mircontapp.sportalbum.data.database.BupiTeam
import com.mircontapp.sportalbum.data.database.Player
import com.mircontapp.sportalbum.data.database.Team
import com.mircontapp.sportalbum.domain.datasource.AlbumDataSource
import com.mircontapp.sportalbum.domain.models.BupiPlayerModel
import com.mircontapp.sportalbum.domain.models.BupiTeamModel
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel


class DatabaseDataSource : AlbumDataSource {
    private val players: MutableList<PlayerModel> = ArrayList()
    private val teams: MutableList<TeamModel> = ArrayList()
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

    override suspend fun insertPlayer(playerModel: PlayerModel) {
        database?.playerDao()?.insert(DataMapper.entityFromPlayerModel(playerModel))
    }

    override suspend fun updatePlayer(playerModel: PlayerModel) {
        database?.playerDao()?.update(DataMapper.entityFromPlayerModel(playerModel))
    }

    override suspend fun updateTeam(teamModel: TeamModel) {
        database?.teamDao()?.update(DataMapper.entityFromTeamModel(teamModel))
    }

    override suspend fun insertTeam(teamModel: TeamModel) {
        database?.teamDao()?.insert(DataMapper.entityFromTeamModel(teamModel))
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

    fun insertAllBupiTeams(teams: List<BupiTeamModel>?) {
        val teamsEntities = ArrayList<BupiTeam>()
        teams?.forEach { teamsEntities.add(DataMapper.entityFromBupiTeam(it)) }
        database?.bupiTeamDao()?.insertAll(teamsEntities)
    }

    fun insertAllBupiPlayers(players: List<BupiPlayerModel>?) {
        val playerEntities = ArrayList<BupiPlayer>()
        players?.forEach { playerEntities.add(DataMapper.entityFromBupiPlayer(it)) }
        database?.bupiDao()?.insertAll(playerEntities)
    }


}