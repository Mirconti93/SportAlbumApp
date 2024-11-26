package com.mircontapp.sportalbum.presentation.album.state

import com.mircontapp.sportalbum.domain.models.TeamModel

data class AlbumChooseState(val isLoading: Boolean = false, val teams: List<TeamModel> = emptyList(), val message: String? = null)