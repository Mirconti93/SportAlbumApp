package com.mircontapp.sportalbum.presentation.album

import androidx.lifecycle.ViewModel
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.repository.TeamsRepository
import com.mircontapp.sportalbum.domain.usecases.GetAllTeamsUC
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(teamsRepository: TeamsRepository) : ViewModel() {
    var teams: List<TeamModel> = emptyList()

    init {
        teams = GetAllTeamsUC(teamsRepository).getAllTeams().filter { teamModel -> teamModel.area == Enums.Area.SERIEA  }
    }

}