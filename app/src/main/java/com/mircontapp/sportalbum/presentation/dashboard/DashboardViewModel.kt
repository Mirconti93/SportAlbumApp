package com.mircontapp.sportalbum.presentation.dashboard

import androidx.compose.runtime.mutableStateOf
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
    var updateType = mutableStateOf(UpdateType.UPDATE)
    val teams = mutableStateOf<List<TeamModel>>(emptyList())
    val players = mutableStateOf<List<PlayerModel>>(emptyList())
    val team = mutableStateOf<TeamModel?>(null)
    val player = mutableStateOf<PlayerModel?>(null)
    enum class SelectionType { PLAYERS, TEAMS }
    enum class UpdateType { NEW, UPDATE }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val list = getAllTeamsUC.getAllTeams()
            withContext(Dispatchers.Main) {
                teams.value = list
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            val list = getAllPlayersUC.getPlayers()
            withContext(Dispatchers.Main) {
                players.value = list
            }
        }
    }

    fun updateTeam(teamModel: TeamModel) {
        viewModelScope.launch(Dispatchers.IO) {
            if (updateType.value == UpdateType.NEW) {
                insertTeamUC.invoke(teamModel)
            } else {
                updateTeamUC.invoke(teamModel)
            }
        }
    }

    fun updatePlayer(playerModel: PlayerModel) {
        viewModelScope.launch(Dispatchers.IO) {
            if (updateType.value == UpdateType.NEW) {
                insertPlayerUC.invoke(playerModel)
            } else {
                updatePlayerUC.invoke(playerModel)
            }
        }
    }
}