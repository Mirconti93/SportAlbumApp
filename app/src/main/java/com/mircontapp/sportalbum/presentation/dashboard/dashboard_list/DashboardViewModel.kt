package com.mircontapp.sportalbum.presentation.dashboard.dashboard_list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mircontapp.sportalbum.domain.usecases.GetAllPlayersUC
import com.mircontapp.sportalbum.domain.usecases.GetAllTeamsUC

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    val getAllTeamsUC: GetAllTeamsUC,
    val getAllPlayersUC: GetAllPlayersUC,
) : ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> get() = _state

    init {
        onAction(DashboardAction.Load)
    }

    fun onAction(action: DashboardAction) {
        when (action) {
            is DashboardAction.Load -> {
                _state.value = DashboardState(isLoading = true)
                viewModelScope.launch {
                    val loadedState = withContext(Dispatchers.IO) {
                        DashboardState(
                            players = getAllPlayersUC(),
                            teams = getAllTeamsUC()
                        )
                    }
                    _state.value = loadedState
                }
            }
            is DashboardAction.ShowPlayersFiltered -> {
                viewModelScope.launch {
                    val players = withContext(Dispatchers.IO) {
                        getAllPlayersUC()
                    }
                    _state.value = DashboardState(
                        players = action.text?.let { text->
                            players.filter { it.name.contains(text, ignoreCase = true) || it.team?.contains(text, ignoreCase = true) ?: false  }
                        } ?: players
                    )
                }

            }
            is DashboardAction.ShowTeamsFiltered -> {
                viewModelScope.launch {
                    val teams = withContext(Dispatchers.IO) {
                        getAllTeamsUC()
                    }
                    _state.value = DashboardState(
                        teams = action.text?.let { text ->
                            teams.filter { it.name.contains(text, ignoreCase = true) }
                        } ?: teams
                    )
                }
            }
            is DashboardAction.ChangeSelection ->_state.value = _state.value.copy(selectionType = action.selectionType)
        }

    }


}