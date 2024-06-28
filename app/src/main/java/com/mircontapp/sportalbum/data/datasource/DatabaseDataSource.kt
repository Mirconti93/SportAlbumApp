package com.mircontapp.sportalbum.data.datasource

import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.data.database.AppDatabase
import com.mircontapp.sportalbum.data.database.BupiPlayer
import com.mircontapp.sportalbum.data.database.BupiTeam
import com.mircontapp.sportalbum.data.database.Player
import com.mircontapp.sportalbum.data.database.Team
import com.mircontapp.sportalbum.data.database.playerModelFromEntity
import com.mircontapp.sportalbum.data.database.teamModelFromEntity
import com.mircontapp.sportalbum.domain.datasource.AlbumDataSource
import com.mircontapp.sportalbum.domain.models.BupiPlayerModel
import com.mircontapp.sportalbum.domain.models.BupiTeamModel
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.models.entityFromBupiPlayer
import com.mircontapp.sportalbum.domain.models.entityFromBupiTeam
import com.mircontapp.sportalbum.domain.models.entityFromPlayerModel
import com.mircontapp.sportalbum.domain.models.entityFromTeamModel


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
                players.add(player.playerModelFromEntity())
            }
        }
        return players
    }

    override suspend fun fetchTeams(): List<TeamModel>? {
        if (teams.isEmpty()) {
            database?.teamDao()?.getAll()?.forEach {
                    team ->teams.add(team.teamModelFromEntity())
            }
        }

        return teams
    }

    override suspend fun insertPlayer(playerModel: PlayerModel) {
        database?.playerDao()?.insert(playerModel.entityFromPlayerModel())
    }

    override suspend fun updatePlayer(playerModel: PlayerModel) {
        database?.playerDao()?.update(playerModel.entityFromPlayerModel())
    }

    override suspend fun updateTeam(teamModel: TeamModel) {
        database?.teamDao()?.update(teamModel.entityFromTeamModel())
    }

    override suspend fun insertTeam(teamModel: TeamModel) {
        database?.teamDao()?.insert(teamModel.entityFromTeamModel())
    }

    fun insertAllTeams(teams: List<TeamModel>?) {
        val teamsEntities = ArrayList<Team>()
        teams?.forEach { teamsEntities.add(it.entityFromTeamModel())}
        database?.teamDao()?.insertAll(teamsEntities.distinctBy{it.name})
    }

    fun insertAllPlayers(players: List<PlayerModel>?) {
        val playerEntities = ArrayList<Player>()
        players?.forEach { playerEntities.add(it.entityFromPlayerModel()) }
        database?.playerDao()?.insertAll(playerEntities.distinctBy{it.name})
    }

    fun insertAllBupiTeams(teams: List<BupiTeamModel>?) {
        val teamsEntities = ArrayList<BupiTeam>()
        teams?.forEach { teamsEntities.add(it.entityFromBupiTeam()) }
        database?.bupiTeamDao()?.insertAll(teamsEntities.distinctBy{it.name})
    }

    fun insertAllBupiPlayers(players: List<BupiPlayerModel>?) {
        val playerEntities = ArrayList<BupiPlayer>()
        players?.forEach { playerEntities.add(it.entityFromBupiPlayer()) }
        database?.bupiDao()?.insertAll(playerEntities.distinctBy{it.name})
    }


}