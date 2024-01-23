package com.mircontapp.sportalbum.presentation.commons

import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel

interface OnPlayerClickHandler {
    fun onPlayerClick(playerModel: PlayerModel)
}