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
        val players = ArrayList<PlayerModel>()
        database?.playerDao()?.getAll()?.forEach {
            player ->players.add(playerModelFromEntity(player))
        }
        return players
    }

    override suspend fun fetchTeams(): List<TeamModel>? {
        val teams = ArrayList<TeamModel>()
        database?.teamDao()?.getAll()?.forEach {
                team ->teams.add(teamModelFromEntity(team))
        }
        return teams
    }

    fun insertAllTeams(teams: List<TeamModel>?) {
        val teamsEntities = ArrayList<Team>()
        teams?.forEach { teamsEntities.add(entityFromTeamModel(it)) }
        database?.teamDao()?.insertAll(teamsEntities)
    }

    fun insertAllPlayers(teams: List<PlayerModel>?) {
        val playerEntities = ArrayList<Player>()
        players?.forEach { playerEntities.add(entityFromPlayerModel(it)) }
        database?.playerDao()?.insertAll(playerEntities)
    }

    private fun entityFromPlayerModel(player: PlayerModel) : Player {
        return Player(
            name = player.name,
            role = player.role.name,
            gender = player.gender?.name,
            team = player.team,
            country = player.country,
            birthyear = player.birthyear,
            value = player.value,
            valueleg = player.valueleg,
            teamLegend = player.teamLegend,
            national = player.national,
            nationalLegend = player.nationalLegend,
            roleLineUp = player.roleLineUp
        )
    }

    private fun playerModelFromEntity(player: Player) : PlayerModel {
        return PlayerModel(
            name = player.name,
            role = PlayerHelper.roleFromString(player.role) ?: Enums.Role.PP,
            gender = PlayerHelper.genderFromString(player.gender) ?: Enums.Gender.OTHER,
            team = player.team,
            country = player.country,
            birthyear = player.birthyear,
            value = player.value,
            valueleg = player.valueleg,
            teamLegend = player.teamLegend,
            national = player.national,
            nationalLegend = player.nationalLegend,
            roleLineUp = player.roleLineUp,
            null,
            null
        )
    }

    private fun teamModelFromEntity(team: Team) : TeamModel {
        return TeamModel(
            name = team.name,
            city = team.city,
            country = team.country,
            type = team.type,
            color1 =  team.color1,
            color2 = team.color2,
            stadium = team.stadium,
            coach = team.coach,
            area = TeamHelper.findAreaEnum(team.area)

        )
    }

    private fun entityFromTeamModel(team: TeamModel) : Team {
        return Team(
            name = team.name,
            city = team.city,
            country = team.country,
            type = team.type,
            color1 =  team.color1,
            color2 = team.color2,
            stadium = team.stadium,
            coach = team.coach,
            area = team.area?.name
        )
    }




}