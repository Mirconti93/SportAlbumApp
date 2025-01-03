package com.mircontapp.sportalbum.domain.usecases

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.repository.TeamsRepository
import javax.inject.Inject

class GetTeamsForMatchUC @Inject constructor(val teamsRepository: TeamsRepository) {
    suspend operator fun invoke(): List<TeamModel> {
        return teamsRepository.getAllTeams().filter { it.area === Enums.Area.SERIEA || it.isMatch ?: false }.sortedBy { it.name }
    }

}