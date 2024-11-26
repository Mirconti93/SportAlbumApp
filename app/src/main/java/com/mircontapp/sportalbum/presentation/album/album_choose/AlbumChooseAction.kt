package com.mircontapp.sportalbum.presentation.album.album_choose

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.presentation.album.album_team.AlbumTeamAction

sealed class AlbumChooseAction {
    object Load: AlbumChooseAction()
    data class ShowTeamsByArea(val area: Enums.Area) : AlbumChooseAction()
}