package com.mircontapp.sportalbum.presentation.draw

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DrawViewModel@Inject constructor(
    val getAllTeamsUC: GetAllTeamsUC,
) : ViewModel() {

    private val _options = MutableStateFlow<List<String>>(emptyList())
    val options get() = _options

    private val _teams = MutableStateFlow<List<TeamModel>>(emptyList())
    val teams get() = _teams

    init {
        _teams.value = emptyList()
    }

    fun loadOptions() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = getAllTeamsUC.getAllTeams()
            withContext(Dispatchers.Main) {
                _options.value = list.map {
                    it.name
                }
            }
        }
    }


}