package com.mircontapp.sportalbum.presentation.draw

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.usecases.DoDrawUC
import com.mircontapp.sportalbum.domain.usecases.GetAllPlayersUC
import com.mircontapp.sportalbum.domain.usecases.GetAllTeamsUC
import com.mircontapp.sportalbum.domain.usecases.InsertPlayerUC
import com.mircontapp.sportalbum.domain.usecases.InsertTeamUC
import com.mircontapp.sportalbum.domain.usecases.UpdatePlayerUC
import com.mircontapp.sportalbum.domain.usecases.UpdateTeamUC
import com.mircontapp.sportalbum.presentation.dashboard.edit_team.EditTeamState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DrawViewModel@Inject constructor(
    val getAllTeamsUC: GetAllTeamsUC,
) : ViewModel() {

    private val _options = MutableStateFlow<List<String>>(emptyList())
    val options get() = _options

    private val _state = MutableStateFlow(DrawState())
    val state: StateFlow<DrawState> get() = _state

    init {
        loadOptions()
    }

    fun loadOptions() {
        viewModelScope.launch {
            val list =  withContext(Dispatchers.IO) {
               getAllTeamsUC()
            }
            _options.value = list.map {
                it.name
            }
        }
    }

    fun onAction(action: DrawAction) {
        when (action) {
            is DrawAction.Draw ->{
                _state.value = DrawState(beforeDraw = false, drawModel = DoDrawUC(action.drawModel).invoke())
            }
        }
    }


}