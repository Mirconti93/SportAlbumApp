package com.mircontapp.sportalbum.presentation.commons.team_select

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.usecases.GetTeamsFromAreaLegendUC
import com.mircontapp.sportalbum.domain.usecases.GetTeamsFromAreaUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TeamSelectViewModel @Inject constructor(
    val teamsFromAreaOrderedUC: GetTeamsFromAreaUC,
    val teamsFromAreaLegendOrderedUC: GetTeamsFromAreaLegendUC
) : ViewModel() {

    private val _state by lazy {
        MutableStateFlow(TeamSelectState())
    }
    val state: StateFlow<TeamSelectState> get() = _state


    fun onAction(action: TeamSelectAction) {
        when (action) {
            is TeamSelectAction.Load -> _state.value = TeamSelectState(isLoading = true, message = SportAlbumApplication.getString(R.string.loading))
            is TeamSelectAction.ShowTeamsByArea -> {
                viewModelScope.launch {
                    onAction(TeamSelectAction.Load)
                    val list: List<TeamModel> = withContext(Dispatchers.IO) {
                        if (action.isLegend) teamsFromAreaLegendOrderedUC(action.area)
                        else teamsFromAreaOrderedUC(action.area)
                    }
                    if (list.isEmpty()) {
                        _state.value = TeamSelectState(
                            isLoading = false,
                            message = SportAlbumApplication.getString(R.string.noTeams)
                        )
                    } else {
                        _state.value = TeamSelectState(
                            isLoading = false,
                            teams = list
                        )
                    }

                }
            }

        }
    }

}

