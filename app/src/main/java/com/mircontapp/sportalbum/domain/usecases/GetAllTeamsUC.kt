package com.mircontapp.sportalbum.domain.usecases

import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.repository.TeamsRepository
import javax.inject.Inject

class GetAllTeamsUC() {
    @Inject lateinit var teamsRepository: TeamsRepository

    fun getAllTeams(): List<TeamModel> {
        return teamsRepository.getAllTeams()
    }
}