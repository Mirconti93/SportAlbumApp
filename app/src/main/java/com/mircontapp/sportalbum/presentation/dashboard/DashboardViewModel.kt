package com.mircontapp.sportalbum.presentation.dashboard

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.presentation.commons.SearchUIState
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.models.toShortItem
import com.mircontapp.sportalbum.domain.usecases.GetAllPlayersUC
import com.mircontapp.sportalbum.domain.usecases.GetAllTeamsUC
import com.mircontapp.sportalbum.domain.usecases.InsertPlayerUC
import com.mircontapp.sportalbum.domain.usecases.InsertTeamUC
import com.mircontapp.sportalbum.domain.usecases.UpdatePlayerUC
import com.mircontapp.sportalbum.domain.usecases.UpdateTeamUC
import com.mircontapp.sportalbum.presentation.commons.ShortListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
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

    private val _searchUIState = MutableStateFlow(SearchUIState(false, null))
    val searchUIState: StateFlow<SearchUIState> get() = _searchUIState

    private val _teams = MutableStateFlow<List<TeamModel>>(emptyList())
    val teams get() = _teams

    private val _players = MutableStateFlow<List<PlayerModel>>(emptyList())
    val players get() = _players

    val team = mutableStateOf<TeamModel?>(null)
    val player = mutableStateOf<PlayerModel?>(null)

    private val _areas = MutableStateFlow(Enums.Area.values().toList())
    val areas: StateFlow<List<Enums.Area>> get() = _areas
    enum class SelectionType { PLAYERS, TEAMS }



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

    fun searchVisibility(visible: Boolean) {
        _searchUIState.update { current -> current.copy(teamSelectionVisible = visible, searchingText = null) }
    }

    fun onSearch(text: String) {
        _searchUIState.update { current -> current.copy(teamSelectionVisible = _searchUIState.value.teamSelectionVisible, searchingText = text) }
    }

    fun filterTeams(text: String?) : List<TeamModel> {
        return if (text.isNullOrEmpty()) allTeams
            else {
                allTeams.filter { it.name.lowercase().contains(text, ignoreCase = true) }
            }.sortedByDescending { it.area }

    }

    fun filterPlayers(text: String?) : List<PlayerModel> {
        return if (text.isNullOrEmpty()) allPlayers
            else {
                allPlayers.filter { it.name.contains(text) || it.team?.contains(text, ignoreCase = true) ?: false  }
            }.sortedByDescending { it.valueleg }

    }


}