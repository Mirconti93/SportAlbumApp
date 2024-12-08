package com.mircontapp.sportalbum.presentation.dashboard.edit_player

import com.mircontapp.sportalbum.commons.AlbumHelper
import com.mircontapp.sportalbum.domain.models.PlayerModel

data class EditPlayerState(val isLoading: Boolean = false, val playerModel: PlayerModel = AlbumHelper.emptyPlayerModel(""))