package com.mircontapp.sportalbum.domain.usecases

import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.repository.TeamsRepository
import dagger.hilt.InstallIn
import javax.inject.Inject
class GetAllTeamsUC @Inject constructor(val teamsRepository: TeamsRepository) {
    suspend operator fun invoke(): List<TeamModel> {
        return teamsRepository.getAllTeams()
    }
}