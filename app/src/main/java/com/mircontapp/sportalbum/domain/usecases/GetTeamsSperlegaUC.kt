package com.mircontapp.sportalbum.domain.usecases

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.repository.TeamsRepository
import javax.inject.Inject

class GetTeamsSuperlegaUC @Inject constructor(val teamsRepository: TeamsRepository) {
    suspend fun getTeams(): List<TeamModel> {
        return teamsRepository.teamsFromSuperlega()
    }
}