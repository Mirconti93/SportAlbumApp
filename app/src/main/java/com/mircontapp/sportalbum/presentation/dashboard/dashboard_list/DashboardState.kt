package com.mircontapp.sportalbum.presentation.dashboard.dashboard_list

import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel

data class DashboardState(val isLoading: Boolean = false, val editMode: Boolean = false, val selectionType: SelectionType = SelectionType.PLAYERS, val players: List<PlayerModel> = emptyList(), val teams: List<TeamModel> = emptyList(), val message: String? = null)

enum class SelectionType {TEAMS, PLAYERS}