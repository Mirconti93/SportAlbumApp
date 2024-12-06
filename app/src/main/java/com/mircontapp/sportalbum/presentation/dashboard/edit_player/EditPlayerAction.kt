package com.mircontapp.sportalbum.presentation.dashboard.edit_player

import com.mircontapp.sportalbum.domain.models.PlayerModel

sealed class EditPlayerAction {
    object Load: EditPlayerAction()
    data class ShowEdit(val playerModel: PlayerModel) : EditPlayerAction()
    data class SaveEdit(val playerModel: PlayerModel) : EditPlayerAction()
}