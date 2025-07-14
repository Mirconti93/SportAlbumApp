package com.mircontapp.sportalbum.domain.usecases

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.repository.TeamsRepository
import javax.inject.Inject

class GetTeamsFromAreaLegendUC @Inject constructor(val teamsRepository: TeamsRepository) {
    suspend operator fun invoke(area: Enums.Area): List<TeamModel> {
        return teamsRepository.getAllTeams().filter { area === it.arealegend }.sortedBy { it.name }
    }
}