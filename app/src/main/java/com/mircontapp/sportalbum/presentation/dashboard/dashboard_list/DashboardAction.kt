package com.mircontapp.sportalbum.presentation.dashboard.dashboard_list

import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.presentation.album.album_team.AlbumTeamAction

sealed class DashboardAction {
    object Load: DashboardAction()
    data class ShowPlayersFiltered(val text: String?) : DashboardAction()
    data class ShowTeamsFiltered(val text: String?) : DashboardAction()
}