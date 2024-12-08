package com.mircontapp.sportalbum.presentation.dashboard.edit_team

import com.mircontapp.sportalbum.commons.AlbumHelper
import com.mircontapp.sportalbum.domain.models.TeamModel

data class EditTeamState(val isLoading: Boolean = false, val team: TeamModel = AlbumHelper.emptyTeamModel(""))