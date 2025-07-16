package com.mircontapp.sportalbum.presentation.match.match_game

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.domain.models.MatchModel
import com.mircontapp.sportalbum.domain.models.PlayerMatchModel

data class MatchGameState(
    var isLoading: Boolean = false,
    val screen: Enums.GameScreen = Enums.GameScreen.LINE_UP,
    var teamPosition: Enums.TeamPosition = Enums.TeamPosition.HOME,
    val showModules: Boolean = false,
    var homeBench: List<PlayerMatchModel> = emptyList(),
    var awayBench: List<PlayerMatchModel> = emptyList(),
    var matchModel: MatchModel,
    var isStarting: Boolean = true,
    var phase: Int = 0
)

