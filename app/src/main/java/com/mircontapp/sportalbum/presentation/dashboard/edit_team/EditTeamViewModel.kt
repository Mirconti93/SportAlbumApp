package com.mircontapp.sportalbum.presentation.dashboard.edit_team

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTeamViewModel @Inject constructor(
    val updateTeamUC: UpdateTeamUC,
) : ViewModel() {

    private val _state = MutableStateFlow(EditTeamState())
    val state: StateFlow<EditTeamState> get() = _state

    init {
        onAction(EditTeamAction.Load)
    }

    fun onAction(action: EditTeamAction) {
        when (action) {
            is EditTeamAction.Load -> _state.value = EditTeamState(isLoading = true)
            is EditTeamAction.ShowEdit -> {
                _state.value = EditTeamState(team = action.team)
            }
            is EditTeamAction.SaveEdit -> {
                viewModelScope.launch(Dispatchers.IO) {
                    updateTeamUC(action.team)
                }
            }
        }

    }


}