package com.mircontapp.sportalbum.domain.usecases

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.repository.PlayersRepository
import com.mircontapp.sportalbum.domain.repository.TeamsRepository
import javax.inject.Inject

class UpdatePlayerUC @Inject constructor(val playerRepository: PlayersRepository) {
    operator suspend fun invoke(playerModel: PlayerModel) {
        if (playerRepository.getAllPlayers().map { it.name }.contains(playerModel.name)) {
            playerRepository.updatePlayer(playerModel)
        } else {
            playerRepository.insertPlayer(playerModel)
        }

    }
}