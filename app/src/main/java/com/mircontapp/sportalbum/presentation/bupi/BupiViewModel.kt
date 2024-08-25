package com.mircontapp.sportalbum.presentation.bupi

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.domain.models.BupiPlayerModel
import com.mircontapp.sportalbum.domain.models.BupiTeamModel
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.presentation.commons.SearchUIState
import com.mircontapp.sportalbum.domain.repository.BupiPlayersRepository
import com.mircontapp.sportalbum.domain.repository.BupiTeamsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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
class BupiViewModel @Inject constructor(
    val bupiPlayersRepository: BupiPlayersRepository,
    val bupiTeamsRepository: BupiTeamsRepository
) : ViewModel() {

    private var allTeams: List<BupiTeamModel> = emptyList()
    private var allPlayers: List<BupiPlayerModel> = emptyList()

    private val _searchUIState = MutableStateFlow(SearchUIState(false, null))
    val searchUIState: StateFlow<SearchUIState> get() = _searchUIState

    private val _bupiTeams = MutableStateFlow<List<BupiTeamModel>>(emptyList())
    val bupiTeams get() = _bupiTeams

    private val _bupiPlayers = MutableStateFlow<List<BupiPlayerModel>>(emptyList())
    val bupiPlayers = _bupiPlayers

    var updateType = mutableStateOf(Enums.UpdateType.UPDATE)


    init{
        loadTeams()
        loadPlayers()
    }

    fun loadTeams() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = bupiTeamsRepository.getAllTeams()
            withContext(Dispatchers.Main) {
                allTeams = list
                _bupiTeams.value = list
            }
        }
    }

    fun loadPlayers() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = bupiPlayersRepository.getAllPlayers()
            withContext(Dispatchers.Main) {
                allPlayers = list
                _bupiPlayers.value = list.sortedBy { it.role }
            }
        }
    }

    fun bupiPlayersByTeam(team: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = bupiPlayersRepository.playersFromTeam(team)
            withContext(Dispatchers.Main) {
                allPlayers = list
                _bupiPlayers.value = list
            }
        }
    }

    fun filterTeams(text: String) {
        if (!text.isEmpty()) {
            _bupiTeams.value = allTeams.filter {
                it.name.contains(text, ignoreCase = true) || it.area?.contains(
                    text,
                    ignoreCase = true
                ) ?: false
            }

        }
    }

    fun filterPlayers(text: String) {
        if (!text.isEmpty()) {
            _bupiPlayers.value = allPlayers.filter { it.name.contains(text, ignoreCase = true) || it.team.contains(text, ignoreCase = true)   }
        }
    }


    fun updatePlayer(bupiPlayerModel: BupiPlayerModel) {
        viewModelScope.async(Dispatchers.IO) {
            bupiPlayersRepository.updatePlayer(bupiPlayerModel)
        }.invokeOnCompletion {
            loadPlayers()
        }
    }

    fun updateTeam(bupiTeamModel: BupiTeamModel) {
        viewModelScope.async(Dispatchers.IO) {
            bupiTeamsRepository.updateTeam(bupiTeamModel)
        }.invokeOnCompletion {
            loadTeams()
        }
    }



}