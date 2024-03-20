package com.mircontapp.sportalbum.presentation.dashboard

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    val getAllTeamsUC: GetAllTeamsUC,
    val getAllPlayersUC: GetAllPlayersUC,
) : ViewModel() {





}