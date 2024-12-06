package com.mircontapp.sportalbum.presentation.dashboard.edit_team

import com.mircontapp.sportalbum.commons.PlayerHelper
import com.mircontapp.sportalbum.domain.models.PlayerModel

data class EditTeamState(val isLoading: Boolean = false, val playerModel: PlayerModel = PlayerHelper.buildPlayerModel(""), val isSaved: Boolean = false, val isNew: Boolean = true)