package com.mircontapp.sportalbum.presentation.dashboard.dashboard_list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.presentation.commons.SearchUIState
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.usecases.GetAllPlayersUC
import com.mircontapp.sportalbum.domain.usecases.GetAllTeamsUC
import com.mircontapp.sportalbum.domain.usecases.InsertPlayerUC
import com.mircontapp.sportalbum.domain.usecases.InsertTeamUC
import com.mircontapp.sportalbum.domain.usecases.UpdatePlayerUC
import com.mircontapp.sportalbum.domain.usecases.UpdateTeamUC
import com.mircontapp.sportalbum.presentation.album.album_choose.AlbumChooseAction
import com.mircontapp.sportalbum.presentation.album.album_choose.AlbumChooseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    val getAllTeamsUC: GetAllTeamsUC,
    val getAllPlayersUC: GetAllPlayersUC,
    val updateTeamUC: UpdateTeamUC,
    val insertTeamUC: InsertTeamUC,
    val insertPlayerUC: InsertPlayerUC,
    val updatePlayerUC: UpdatePlayerUC
) : ViewModel() {
    var selectionType = mutableStateOf(SelectionType.TEAMS)
    var updateType = mutableStateOf(Enums.UpdateType.UPDATE)
    private var allTeams: List<TeamModel> = emptyList()
    private var allPlayers: List<PlayerModel> = emptyList()
    private val _teams = MutableStateFlow<List<TeamModel>>(emptyList())
    val teams get() = _teams

    private val _players = MutableStateFlow<List<PlayerModel>>(emptyList())
    val players get() = _players

    val team = mutableStateOf<TeamModel?>(null)
    val player = mutableStateOf<PlayerModel?>(null)

    private val _searchUIState = MutableStateFlow(SearchUIState(false, null))
    val searchUIState: StateFlow<SearchUIState> get() = _searchUIState

    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> get() = _state

    init {
        onAction(DashboardAction.ShowPlayersFiltered(null))
    }

    fun onAction(action: DashboardAction) {
        when (action) {
            is DashboardAction.Load -> _state.value = DashboardState(isLoading = true)
            is DashboardAction.ShowPlayersFiltered -> {
                _state.value = DashboardState(
                    players = action.text?.let { text->
                        allPlayers.filter { it.name.contains(text, ignoreCase = true) || it.team?.contains(text, ignoreCase = true) ?: false  }
                        } ?: allPlayers
                )
            }
            is DashboardAction.ShowTeamsFiltered -> {
                _state.value = DashboardState(
                    teams = action.text?.let { text ->
                        allTeams.filter { it.name.contains(text, ignoreCase = true) }
                    } ?: allTeams
                )
            }
        }

    }



    init {
        loadTeams()
        loadPlayers()
    }

    fun updateTeam(teamModel: TeamModel) {
        viewModelScope.launch(Dispatchers.IO) {
            if (updateType.value == Enums.UpdateType.NEW) {
                insertTeamUC.invoke(teamModel)
            } else {
                updateTeamUC.invoke(teamModel)
            }
        }
        loadTeams()
    }

    fun updatePlayer(playerModel: PlayerModel) {
        viewModelScope.launch(Dispatchers.IO) {
            if (updateType.value == Enums.UpdateType.NEW) {
                insertPlayerUC.invoke(playerModel)
            } else {
                updatePlayerUC.invoke(playerModel)
            }
        }
        loadPlayers()
    }

    fun loadPlayers() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = getAllPlayersUC.getPlayers()
            withContext(Dispatchers.Main) {
                allPlayers = list
                _players.value = list
            }
        }
    }

    fun loadTeams() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = getAllTeamsUC.getAllTeams()
            withContext(Dispatchers.Main) {
                allTeams = list
                _teams.value = list
            }
        }
    }

    fun filterTeams(text: String) {
        if (!text.isEmpty()) {
            _teams.value = allTeams.filter {
                it.name.contains(text, ignoreCase = true)
            }

        }
    }

    fun filterPlayers(text: String) {
        if (!text.isEmpty()) {
            _players.value = allPlayers.filter { it.name.contains(text, ignoreCase = true) || it.team?.contains(text, ignoreCase = true) ?: false  }
        }
    }


}