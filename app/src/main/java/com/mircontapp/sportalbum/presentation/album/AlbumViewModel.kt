package com.mircontapp.sportalbum.presentation.album

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.usecases.GetAllTeamsUC
import com.mircontapp.sportalbum.domain.usecases.GetTeamsFromAreaUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    val teamsFromAreaOrderedUC: GetTeamsFromAreaUC,
) : ViewModel() {
    val teams = MutableStateFlow<List<TeamModel>>(emptyList())
    val showSelection = mutableStateOf(true)

    fun getTeamsFromArea(area: Enums.Area) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = teamsFromAreaOrderedUC.getTeamsFromArea(area)
            withContext(Dispatchers.Main) {
                teams.value = list
            }
        }
    }

}

