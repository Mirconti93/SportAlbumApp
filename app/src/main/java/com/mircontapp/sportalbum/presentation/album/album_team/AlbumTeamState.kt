package com.mircontapp.sportalbum.presentation.album.album_team

import com.mircontapp.sportalbum.domain.models.PlayerModel

data class AlbumTeamState(val isLoading: Boolean = false, val isLegend: Boolean = false, val players: List<PlayerModel> = emptyList(), val message: String? = null)