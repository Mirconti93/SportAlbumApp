package com.mircontapp.sportalbum.domain.usecases

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.repository.TeamsRepository
import javax.inject.Inject

class InsertTeamUC @Inject constructor(val teamsRepository: TeamsRepository) {
    operator suspend fun invoke(teamModel: TeamModel) {
        teamsRepository.insertTeam(teamModel)
    }
}