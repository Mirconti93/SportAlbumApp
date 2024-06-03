package com.mircontapp.sportalbum.domain.repository

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.data.database.Team
import com.mircontapp.sportalbum.domain.models.BupiTeamModel
import com.mircontapp.sportalbum.domain.models.TeamModel

interface BupiTeamsRepository {
    suspend fun getAllTeams(): List<BupiTeamModel>
    suspend fun teamsFromArea(area: String) : List<BupiTeamModel>
    suspend fun insertTeam(teamModel: BupiTeamModel)
    suspend fun updateTeam(teamModel: BupiTeamModel)
}