package com.mircontapp.sportalbum.domain.usecases

import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.repository.TeamsRepository

class GetAllTeamsUC(val teamsRepository: TeamsRepository) {
    fun getAllTeams(): List<TeamModel> {
        return teamsRepository.getAllTeams()
    }
}