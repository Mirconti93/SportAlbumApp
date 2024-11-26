package com.mircontapp.sportalbum.presentation.album.album_team

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.domain.models.TeamModel

sealed class AlbumTeamAction {
    object Load: AlbumTeamAction()
    data class ShowPlayersByTeam(val team: TeamModel) : AlbumTeamAction()
}