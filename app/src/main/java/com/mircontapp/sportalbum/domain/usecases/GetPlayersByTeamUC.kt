package com.mircontapp.sportalbum.domain.usecases

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.data.database.Team
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.repository.PlayersRepository
import javax.inject.Inject
class GetPlayersByTeamUC @Inject constructor(val playersRepository: PlayersRepository) {
    suspend fun getPlayers(teamModel: TeamModel): List<PlayerModel> {
        return when (teamModel.area) {
            Enums.Area.NAZIONALI -> playersRepository.playersFromNational(teamModel.name, Enums.Gender.M)
            Enums.Area.NAZIONALIFEMMINILI-> playersRepository.playersFromNational(teamModel.name, Enums.Gender.F)
            else -> playersRepository.playersFromTeam(teamModel.name)
        }
    }
}