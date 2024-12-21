package com.mircontapp.sportalbum.presentation.match.match_start

import com.mircontapp.sportalbum.domain.models.TeamModel

sealed class MatchStartAction {
    object Load : MatchStartAction()
    data class SelectHomeTeam(val team: TeamModel) : MatchStartAction()
    data class SelectAwayTeam(val team: TeamModel) : MatchStartAction()
}