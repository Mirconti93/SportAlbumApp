package com.mircontapp.sportalbum.presentation.dashboard.edit_player

import com.mircontapp.sportalbum.commons.PlayerHelper
import com.mircontapp.sportalbum.domain.models.PlayerModel

data class EditPlayerState(val isLoading: Boolean = false, val playerModel: PlayerModel = PlayerHelper.buildPlayerModel(""), val isSaved: Boolean = false, val isNew: Boolean = true)