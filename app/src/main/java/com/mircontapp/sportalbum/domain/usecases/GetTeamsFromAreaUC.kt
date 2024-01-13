package com.mircontapp.sportalbum.domain.usecases

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.repository.TeamsRepository
import javax.inject.Inject

class GetTeamsFromAreaUC @Inject constructor(val teamsRepository: TeamsRepository) {
    suspend fun getTeamsFromArea(area: Enums.Area): List<TeamModel> {
        return teamsRepository.teamsFromArea(area)
    }
}