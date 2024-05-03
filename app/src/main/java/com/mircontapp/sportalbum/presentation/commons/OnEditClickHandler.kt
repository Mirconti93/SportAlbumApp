package com.mircontapp.sportalbum.presentation.commons

import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel

interface OnEditClickHandler {
    fun onPlayerClick(playerModel: PlayerModel)
}