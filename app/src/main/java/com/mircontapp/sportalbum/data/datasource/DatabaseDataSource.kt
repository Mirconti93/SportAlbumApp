package com.mircontapp.sportalbum.data.datasource

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.commons.PlayerHelper
import com.mircontapp.sportalbum.commons.TeamHelper
import com.mircontapp.sportalbum.data.database.AppDatabase
import com.mircontapp.sportalbum.data.database.Player
import com.mircontapp.sportalbum.data.database.PlayerDao
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel


class DatabaseDataSource : AlbumDataSource {
    val players: MutableList<PlayerModel> = ArrayList()
    val teams: MutableList<TeamModel> = ArrayList()
    val database: AppDatabase?

    init {
        database = AppDatabase.getInstance(SportAlbumApplication.instance.applicationContext)
    }

    override fun fetchPlayers(): List<PlayerModel>? {
        val players = ArrayList<PlayerModel>()
        database?.playerDao()?.getAll()?.forEach {
            player ->players.add(playerModelFromEntity(player))
        }
        return players
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
            roleLineUp = player.roleLineUp
        )
    }

    override fun fetchTeams(): List<TeamModel> {
        TODO("Not yet implemented")
    }






}