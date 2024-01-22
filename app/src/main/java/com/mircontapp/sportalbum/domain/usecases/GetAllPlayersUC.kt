package com.mircontapp.sportalbum.domain.usecases

import com.mircontapp.sportalbum.data.database.Team
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.repository.PlayersRepository
import javax.inject.Inject
class GetAllPlayersUC @Inject constructor(val playersRepository: PlayersRepository) {
    suspend fun getPlayers(): List<PlayerModel> {
        return playersRepository.getAllPlayers()
    }
}