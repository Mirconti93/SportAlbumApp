package com.mircontapp.sportalbum.presentation.commons.team_select

import com.mirco.sportalbum.utils.Enums

sealed class TeamSelectAction {
    object Load: TeamSelectAction()
    data class ShowTeamsByArea(val area: Enums.Area, val isLegend: Boolean) : TeamSelectAction()
}