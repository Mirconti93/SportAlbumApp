package com.mircontapp.sportalbum.domain.usecases

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.data.database.Team
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.repository.PlayersRepository
import javax.inject.Inject
class GetPlayersByTeamLegendUC @Inject constructor(val playersRepository: PlayersRepository) {
    suspend operator fun invoke(teamModel: TeamModel): List<PlayerModel> {
        return when (teamModel.area) {
            Enums.Area.NAZIONALI -> playersRepository.playersFromNationalLegend(teamModel.name, Enums.Gender.M)
            Enums.Area.NAZIONALIFEMMINILI-> playersRepository.playersFromNationalLegend(teamModel.name, Enums.Gender.F)
            else -> playersRepository.playersFromTeamLegend(teamModel.name)
        }
    }
}