package com.mircontapp.sportalbum.presentation.bupi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mircontapp.sportalbum.domain.models.BupiPlayerModel
import com.mircontapp.sportalbum.domain.models.BupiTeamModel
import com.mircontapp.sportalbum.presentation.commons.SearchUIState
import com.mircontapp.sportalbum.domain.repository.BupiPlayersRepository
import com.mircontapp.sportalbum.domain.repository.BupiTeamsRepository
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
class BupiViewModel @Inject constructor(
    val bupiPlayersRepository: BupiPlayersRepository,
    val bupiTeamsRepository: BupiTeamsRepository
) : ViewModel() {

    private var allTeams: List<BupiTeamModel> = emptyList()
    private var allPlayers: List<BupiPlayerModel> = emptyList()

    private val _searchUIState = MutableStateFlow(SearchUIState(false, null))
    val searchUIState: StateFlow<SearchUIState> get() = _searchUIState

    private val _bupiTeams = MutableStateFlow<List<BupiTeamModel>>(emptyList())
    val bupiTeams = searchUIState.combine(_bupiTeams) { searchUIState, teams ->
        filterTeams(searchUIState.searchingText)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _bupiTeams.value)

    private val _bupiPlayers = MutableStateFlow<List<BupiPlayerModel>>(emptyList())
    val bupiPlayers = searchUIState.combine(_bupiPlayers) { searchUIState, players ->
        filterPlayers(searchUIState.searchingText)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _bupiPlayers.value)

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
                _bupiPlayers.value = list
            }
        }
    }

    fun filterTeams(text: String?) : List<BupiTeamModel> {
        var teams =
            if (text.isNullOrEmpty()) allTeams
            else {
                allTeams.filter { it.name.contains(text) || it.area?.contains(text) ?: false  }
            }
        return teams
    }

    fun filterPlayers(text: String?) : List<BupiPlayerModel> {
        var players =
            if (text.isNullOrEmpty()) allPlayers
            else {
                allPlayers.filter { it.name.contains(text) || it.team.contains(text)   }
            }
        return players
    }

    fun onSearch(text: String) {
        _searchUIState.update { current -> current.copy(teamSelectionVisible = _searchUIState.value.teamSelectionVisible, searchingText = text) }
    }



}