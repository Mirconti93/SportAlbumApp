package com.mircontapp.sportalbum.data.repository

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.domain.datasource.AlbumDataSource
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.repository.TeamsRepository
import javax.inject.Inject
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

    override suspend fun getNationalTeams(): List<TeamModel> {
        return getAllTeams().filter { "national".equals(it.type) }.sortedBy { it.name }
    }

    override suspend fun teamsFromSuperlega(): List<TeamModel> {
        return getAllTeams().filter { it.superlega ?: false }.sortedBy { it.name }
    }



}