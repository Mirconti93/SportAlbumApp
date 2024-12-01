package com.mircontapp.sportalbum.presentation.album.album_choose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.usecases.GetTeamsFromAreaUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
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

    init {
        onAction(AlbumChooseAction.ShowTeamsByArea(Enums.Area.SERIEA))
    }

    fun onAction(action: AlbumChooseAction) {
        when (action) {
            is AlbumChooseAction.Load -> _state.value = AlbumChooseState(isLoading = true)
            is AlbumChooseAction.ShowTeamsByArea -> {
                viewModelScope.launch {
                    onAction(AlbumChooseAction.Load)
                    val list: List<TeamModel> = withContext(Dispatchers.IO) {
                         teamsFromAreaOrderedUC(action.area)
                    }
                    if (list.isEmpty()) {
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

