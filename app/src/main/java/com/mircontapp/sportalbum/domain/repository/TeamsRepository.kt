package com.mircontapp.sportalbum.domain.repository

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.domain.models.TeamModel

interface TeamsRepository {
    fun getAllTeams(): List<TeamModel>
    fun addTeam(TeamModel: TeamModel)
    fun teamsFromArea(area: Enums.Area) : List<TeamModel>
}