package com.mircontapp.sportalbum.presentation.dashboard.edit_player

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.commons.PlayerHelper
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.presentation.commons.SearchUIState
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.usecases.GetAllPlayersUC
import com.mircontapp.sportalbum.domain.usecases.GetAllTeamsUC
import com.mircontapp.sportalbum.domain.usecases.InsertPlayerUC
import com.mircontapp.sportalbum.domain.usecases.InsertTeamUC
import com.mircontapp.sportalbum.domain.usecases.UpdatePlayerUC
import com.mircontapp.sportalbum.domain.usecases.UpdateTeamUC
import com.mircontapp.sportalbum.presentation.album.album_choose.AlbumChooseAction
import com.mircontapp.sportalbum.presentation.album.album_choose.AlbumChooseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditPlayerViewModel @Inject constructor(
    val insertPlayerUC: InsertPlayerUC,
    val updatePlayerUC: UpdatePlayerUC
) : ViewModel() {
    var updateType = mutableStateOf(Enums.UpdateType.UPDATE)

    private val _state = MutableStateFlow(EditPlayerState())
    val state: StateFlow<EditPlayerState> get() = _state

    init {
        onAction(EditPlayerAction.Load)
    }

    fun onAction(action: EditPlayerAction) {
        when (action) {
            is EditPlayerAction.Load -> _state.value = EditPlayerState(isLoading = true)
            is EditPlayerAction.ShowEdit -> {
                _state.value = EditPlayerState( playerModel = action.playerModel)
            }
            is EditPlayerAction.SaveEdit -> {
                viewModelScope.launch(Dispatchers.IO) {
                    if (updateType.value == Enums.UpdateType.NEW) {
                        insertPlayerUC.invoke(action.playerModel)
                    } else {
                        updatePlayerUC.invoke(action.playerModel)
                    }
                }
            }
        }

    }


}