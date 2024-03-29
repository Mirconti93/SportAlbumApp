package com.mircontapp.sportalbum.presentation.dashboard

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.usecases.GetAllPlayersUC
import com.mircontapp.sportalbum.domain.usecases.GetAllTeamsUC
import com.mircontapp.sportalbum.domain.usecases.InsertPlayerUC
import com.mircontapp.sportalbum.domain.usecases.InsertTeamUC
import com.mircontapp.sportalbum.domain.usecases.UpdatePlayerUC
import com.mircontapp.sportalbum.domain.usecases.UpdateTeamUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.enums.EnumEntries

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
    var updateType = mutableStateOf(UpdateType.UPDATE)
    private var allTeams: List<TeamModel> = emptyList()
    private var allPlayers: List<PlayerModel> = emptyList()

    private val _teams = MutableStateFlow<List<TeamModel>>(emptyList())
    val teams: StateFlow<List<TeamModel>> get() = _teams
    private val _players = MutableStateFlow<List<PlayerModel>>(emptyList())
    val players: StateFlow<List<PlayerModel>> get() = _players

    val team = mutableStateOf<TeamModel?>(null)
    val player = mutableStateOf<PlayerModel?>(null)

    private val _areas = MutableStateFlow(Enums.Area.values().toList())
    val areas: StateFlow<List<Enums.Area>> get() = _areas
    enum class SelectionType { PLAYERS, TEAMS }
    enum class UpdateType { NEW, UPDATE }

    private val _editUIState = MutableStateFlow(EditUIState(false))
    val editUIState: StateFlow<EditUIState> get() = _editUIState

    init {
        loadTeams()
        loadPlayers()
    }

    fun updateTeam(teamModel: TeamModel) {
        viewModelScope.launch(Dispatchers.IO) {
            if (updateType.value == UpdateType.NEW) {
                insertTeamUC.invoke(teamModel)
            } else {
                updateTeamUC.invoke(teamModel)
            }
        }
        loadTeams()
    }

    fun updatePlayer(playerModel: PlayerModel) {
        viewModelScope.launch(Dispatchers.IO) {
            if (updateType.value == UpdateType.NEW) {
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

    fun showTeamsSelection(visible: Boolean) {
        _editUIState.update { current -> current.copy(visible) }
    }

    fun filterTeams(text: String) {
        var teams = allTeams.filter { it.name.contains(text) || it.area?.name?.contains(text) ?: false  }
        if (teams.isNullOrEmpty()) {
            teams = allTeams
        }
        _teams.value = teams
    }

    fun filterPlayers(text: String) {
        var players = allPlayers.filter { it.name.contains(text) || it.team?.contains(text) ?: false  }
        if (players.isNullOrEmpty()) {
            players = allPlayers
        }
        _players.value = players
    }

    data class EditUIState(var teamSelectionVisible: Boolean)
}