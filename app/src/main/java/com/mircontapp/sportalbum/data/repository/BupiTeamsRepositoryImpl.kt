package com.mircontapp.sportalbum.data.repository

import com.mircontapp.sportalbum.domain.datasource.BupiDataSource
import com.mircontapp.sportalbum.domain.models.BupiTeamModel
import com.mircontapp.sportalbum.domain.repository.BupiTeamsRepository
import javax.inject.Inject

class BupiTeamsRepositoryImpl @Inject constructor(val bupiDataSource: BupiDataSource) : BupiTeamsRepository {

    override suspend fun getAllTeams(): List<BupiTeamModel> {
        return bupiDataSource.fetchBupiTeams() ?: emptyList()
    }

    override suspend fun teamsFromArea(area: String) : List<BupiTeamModel> {
        return getAllTeams().filter { area == it.area }
    }

    override suspend fun updateTeam(bupiTeamModel: BupiTeamModel) {
        if (getAllTeams().find { it.name == bupiTeamModel.name } != null) {
            bupiDataSource.updateBupiTeam(bupiTeamModel)
        } else {
            bupiDataSource.insertBupiTeam(bupiTeamModel)
        }
    }

}