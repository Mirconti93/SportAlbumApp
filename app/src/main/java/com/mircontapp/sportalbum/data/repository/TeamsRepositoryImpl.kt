package com.mircontapp.sportalbum.data.repository

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.data.datasource.AlbumDataSource
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.repository.TeamsRepository
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

class TeamsRepositoryImpl(albumDataSource: AlbumDataSource) : TeamsRepository {
    val teams: MutableList<TeamModel>

    init {
        teams = albumDataSource.fetchTeams()?.toMutableList() ?: ArrayList()
    }

    override fun getAllTeams(): List<TeamModel> {
        return teams.toList()
    }

    override fun addTeam(teamModel: TeamModel) {
        teams.add(teamModel)
    }

    override fun teamsFromArea(area: Enums.Area) : List<TeamModel> {
        return teams.filter { area.equals(it.area) }
    }



}