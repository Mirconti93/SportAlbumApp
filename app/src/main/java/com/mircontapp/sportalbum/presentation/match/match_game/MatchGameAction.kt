package com.mircontapp.sportalbum.presentation.match.match_game

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.domain.models.TeamModel

sealed class MatchGameAction {
    object Load : MatchGameAction()
    data class ChangeLineUp(val teamPosition: Enums.TeamPosition) : MatchGameAction()
    data class ToggleModule(val showModuleOption: Boolean, val teamPosition: Enums.TeamPosition) : MatchGameAction()
    object Play : MatchGameAction()
}