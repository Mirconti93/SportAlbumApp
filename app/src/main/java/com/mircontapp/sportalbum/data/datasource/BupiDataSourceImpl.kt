package com.mircontapp.sportalbum.data.datasource

import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.data.database.AppDatabase
import com.mircontapp.sportalbum.data.database.BupiPlayer
import com.mircontapp.sportalbum.data.database.BupiTeam
import com.mircontapp.sportalbum.data.database.Player
import com.mircontapp.sportalbum.data.database.Team
import com.mircontapp.sportalbum.data.database.bupiPlayerFromEntity
import com.mircontapp.sportalbum.data.database.bupiTeamModelFromEntity
import com.mircontapp.sportalbum.domain.datasource.BupiDataSource
import com.mircontapp.sportalbum.domain.models.BupiPlayerModel
import com.mircontapp.sportalbum.domain.models.BupiTeamModel
import com.mircontapp.sportalbum.domain.models.entityFromBupiPlayer
import com.mircontapp.sportalbum.domain.models.entityFromBupiTeam



class BupiDataSourceImpl : BupiDataSource {
    private val players: MutableList<BupiPlayerModel> = ArrayList()
    private val teams: MutableList<BupiTeamModel> = ArrayList()
    val database: AppDatabase?

    init {
       database = AppDatabase.getInstance(SportAlbumApplication.instance.applicationContext)
    }

    override suspend fun fetchBupiPlayers(): List<BupiPlayerModel> {
        if (players.isEmpty()) {
            database?.bupiDao()?.getBupi()?.forEach { player ->
                players.add(player.bupiPlayerFromEntity())
            }
        }
        return players
    }

    override suspend fun fetchBupiTeams(): List<BupiTeamModel>? {
        if (teams.isEmpty()) {
            database?.bupiTeamDao()?.getTeams()?.forEach {
                    team ->teams.add(team.bupiTeamModelFromEntity())
            }
        }

        return teams
    }

    override suspend fun insertBupiPlayer(bupiPlayerModel: BupiPlayerModel) {
        database?.bupiDao()?.insert(bupiPlayerModel.entityFromBupiPlayer())
    }

    override suspend fun updateBupiPlayer(bupiPlayerModel: BupiPlayerModel) {
        database?.bupiDao()?.update(bupiPlayerModel.entityFromBupiPlayer())
    }

    override suspend fun updateBupiTeam(bupiTeamModel: BupiTeamModel) {
        database?.bupiTeamDao()?.update(bupiTeamModel.entityFromBupiTeam())
    }

    override suspend fun insertBupiTeam(bupiTeamModel: BupiTeamModel) {
        database?.bupiTeamDao()?.insert(bupiTeamModel.entityFromBupiTeam())
    }



}