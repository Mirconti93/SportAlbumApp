package com.mircontapp.sportalbum.presentation.commons

import com.mircontapp.sportalbum.domain.models.PlayerMatchModel

interface OnPlayerMatchClickHandler {
    fun onPlayerClick(playerModel: PlayerMatchModel)
}