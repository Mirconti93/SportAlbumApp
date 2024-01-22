package com.mircontapp.sportalbum.data.repository

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.data.datasource.AlbumDataSource
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.repository.TeamsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import kotlin.Comparator
import kotlin.collections.ArrayList

class TeamsRepositoryImpl @Inject constructor(val albumDataSource: AlbumDataSource) : TeamsRepository {

    override suspend fun getAllTeams(): List<TeamModel> {
        return albumDataSource.fetchTeams()?.toMutableList() ?: ArrayList()
    }

    override suspend fun addTeam(teamModel: TeamModel) {
        //teams.add(teamModel)
    }

    override suspend fun teamsFromArea(area: Enums.Area) : List<TeamModel> {
        return getAllTeams().filter { area.equals(it.area) }.sortedBy { it.name }
    }

    override suspend fun updateTeam(teamModel: TeamModel) {
        albumDataSource.updateTeam(teamModel)
    }

    override suspend fun insertTeam(teamModel: TeamModel) {
        albumDataSource.insertTeam(teamModel)
    }



}