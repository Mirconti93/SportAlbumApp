package com.mircontapp.sportalbum.domain.usecases

import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.repository.TeamsRepository
import javax.inject.Inject

class GetTeamFromNameUC @Inject constructor(val teamRepository: TeamsRepository) {
    suspend operator fun invoke(teamName: String) : TeamModel? {
        return teamRepository.getAllTeams().find {
            teamName.equals(it.name)
        }
    }
}