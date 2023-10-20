package com.mircontapp.sportalbum.domain.usecases

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.repository.TeamsRepository

class GetTeamsFromAreaOrderedUC(val teamsRepository: TeamsRepository) {
    fun getTeamsFromAreaOrdered(area: Enums.Area): List<TeamModel> {
        return teamsRepository.teamsFromArea(area)
    }
}