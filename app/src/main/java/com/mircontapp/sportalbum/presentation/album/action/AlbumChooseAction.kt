package com.mircontapp.sportalbum.presentation.album.action

import com.mirco.sportalbum.utils.Enums

sealed class AlbumChooseAction {
    object Load: AlbumChooseAction()
    data class ShowTeamsByArea(val area: Enums.Area) : AlbumChooseAction()
}