package com.mircontapp.sportalbum.presentation.album

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.usecases.GetAllTeamsUC
import com.mircontapp.sportalbum.domain.usecases.GetTeamsFromAreaUC
import com.mircontapp.sportalbum.presentation.album.action.AlbumChooseAction
import com.mircontapp.sportalbum.presentation.album.action.AlbumChooseAction.*
import com.mircontapp.sportalbum.presentation.album.state.AlbumChooseState
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

    private val _state by lazy {
        MutableStateFlow(AlbumChooseState())
    }
    val state: StateFlow<AlbumChooseState> get() = _state


    fun onAction(action: AlbumChooseAction) {
        when (action) {
            is Load -> _state.value = AlbumChooseState(isLoading = true)
            is ShowTeamsByArea -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val list = teamsFromAreaOrderedUC(action.area)
                    withContext(Dispatchers.Main) {
                        if (list.isNullOrEmpty()) {
                            _state.value = AlbumChooseState(
                                isLoading = false,
                                message = SportAlbumApplication.getString(R.string.noTeams)
                            )
                        } else {
                            _state.value = AlbumChooseState(
                                isLoading = false,
                                teams = list
                            )
                        }
                    }
                }
            }

        }
    }

}

