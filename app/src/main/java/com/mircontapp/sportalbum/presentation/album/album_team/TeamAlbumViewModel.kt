package com.mircontapp.sportalbum.presentation.album.album_team

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.usecases.GetPlayersByTeamLegendUC
import com.mircontapp.sportalbum.domain.usecases.GetPlayersByTeamUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TeamAlbumViewModel @Inject constructor(
    val getPlayersByTeamUC: GetPlayersByTeamUC,
    val getPlayersByTeamLegendUC: GetPlayersByTeamLegendUC,
) : ViewModel() {

    private val _state by lazy {0
        MutableStateFlow(AlbumTeamState())
    }
    val state: StateFlow<AlbumTeamState> get() = _state

    fun onAction(action: AlbumTeamAction) {
        when (action) {
            is AlbumTeamAction.Load -> _state.value = AlbumTeamState(isLoading = true)
            is AlbumTeamAction.ShowPlayersByTeam -> {
                viewModelScope.launch {
                    val list = withContext(Dispatchers.IO) {
                        getPlayersByTeamLegendUC(action.team).sortedBy { it.roleLineUp }
                    }
                    if (list.isEmpty()) {
                        _state.value = AlbumTeamState(message = SportAlbumApplication.getString(R.string.noPlayers))
                    } else {
                        _state.value = AlbumTeamState(players = list)
                    }
                }
            }
        }
    }

    /*fun playersFromTeamLegend(team: TeamModel?) :Boolean {
        if (team != null) {
            viewModelScope.launch(Dispatchers.IO) {
                val list = getPlayersByTeamLegendUC.getPlayers(team)
                withContext(Dispatchers.Main) {
                    players.value = list.sortedWith(compareBy<PlayerModel> { it.roleLineUp }.thenByDescending { it.valueleg })
                }
            }
        }
        return true
    }*/

}

