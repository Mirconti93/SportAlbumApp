package com.mircontapp.sportalbum.presentation.dashboard.edit_player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mircontapp.sportalbum.domain.usecases.InsertPlayerUC
import com.mircontapp.sportalbum.domain.usecases.UpdatePlayerUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditPlayerViewModel @Inject constructor(
    val updatePlayerUC: UpdatePlayerUC
) : ViewModel() {
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
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        updatePlayerUC(action.playerModel)
                    }
                }

            }
        }

    }


}