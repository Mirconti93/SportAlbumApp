package com.mircontapp.sportalbum.data.repository

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.data.datasource.AlbumDataSource
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.repository.TeamsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.Comparator
import kotlin.collections.ArrayList

class TeamsRepositoryImpl @Inject constructor(val albumDataSource: AlbumDataSource) : TeamsRepository {
    var teams: MutableList<TeamModel> = ArrayList()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            teams = albumDataSource.fetchTeams()?.toMutableList() ?: ArrayList()
        }
    }

    override suspend fun getAllTeams(): List<TeamModel> {
        return albumDataSource.fetchTeams()?.toMutableList() ?: ArrayList()
    }

    override suspend fun addTeam(teamModel: TeamModel) {
        teams.add(teamModel)
    }

    override suspend fun teamsFromArea(area: Enums.Area) : List<TeamModel> {
        teams = albumDataSource.fetchTeams()?.toMutableList() ?: ArrayList()
        return teams.filter { area.equals(it.area) }.sortedBy { it.name }
    }



}