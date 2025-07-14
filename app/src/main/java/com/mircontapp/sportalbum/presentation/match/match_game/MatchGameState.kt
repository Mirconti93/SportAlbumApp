package com.mircontapp.sportalbum.presentation.match.match_game

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.domain.models.MatchModel
import com.mircontapp.sportalbum.domain.models.PlayerMatchModel

data class MatchGameState(
    val isLoading: Boolean = false,
    val screen: Enums.GameScreen = Enums.GameScreen.LINE_UP_START,
    val teamPosition: Enums.TeamPosition = Enums.TeamPosition.HOME,
    val showModules: Boolean = false,
    val homeEleven: List<PlayerMatchModel> = emptyList(),
    val awayEleven: List<PlayerMatchModel> = emptyList(),
    var homeBench: List<PlayerMatchModel> = emptyList(),
    var awayBench: List<PlayerMatchModel> = emptyList(),
    val matchModel: MatchModel
)

