package com.mircontapp.sportalbum.presentation.commons.team_select

import com.mircontapp.sportalbum.domain.models.TeamModel

data class TeamSelectState(val isLoading: Boolean = false, val teams: List<TeamModel> = emptyList(), val message: String? = null)