package com.mircontapp.sportalbum.presentation.match.match_start

import com.mircontapp.sportalbum.commons.AlbumHelper
import com.mircontapp.sportalbum.domain.models.TeamModel

data class MatchStartState(val isLoading: Boolean = false, val homeTeam: TeamModel = AlbumHelper.emptyTeamModel(""), val awayTeam: TeamModel = AlbumHelper.emptyTeamModel(""))