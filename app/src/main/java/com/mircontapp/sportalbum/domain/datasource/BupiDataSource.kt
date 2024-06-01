package com.mircontapp.sportalbum.domain.datasource

import com.mircontapp.sportalbum.data.database.BupiPlayer
import com.mircontapp.sportalbum.domain.models.BupiPlayerModel
import com.mircontapp.sportalbum.domain.models.BupiTeamModel
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel

interface BupiDataSource {
    abstract suspend fun fetchBupiPlayers(): List<BupiPlayerModel>?

    abstract suspend fun fetchBupiTeams(): List<BupiTeamModel>?

    abstract suspend fun insertBupiPlayer(bupiPlayerModel: BupiPlayerModel)
    abstract suspend fun updateBupiPlayer(bupiPlayerModel: BupiPlayerModel)

    abstract suspend fun updateBupiTeam(bupiTeamModel: BupiTeamModel)
    abstract suspend fun insertBupiTeam(bupiTeamModel: BupiTeamModel)

}