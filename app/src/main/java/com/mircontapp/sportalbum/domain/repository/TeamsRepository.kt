package com.mircontapp.sportalbum.domain.repository

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.data.database.Team
import com.mircontapp.sportalbum.domain.models.TeamModel

interface TeamsRepository {
    suspend fun getAllTeams(): List<TeamModel>
    suspend fun addTeam(TeamModel: TeamModel)
    suspend fun teamsFromArea(area: Enums.Area) : List<TeamModel>
    suspend fun teamsFromSuperlega() : List<TeamModel>
    suspend fun getNationalTeams() : List<TeamModel>
    suspend fun insertTeam(teamModel: TeamModel)
    suspend fun updateTeam(teamModel: TeamModel)
}