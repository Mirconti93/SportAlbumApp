package com.mircontapp.sportalbum.presentation.match.match_start


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.usecases.GetTeamsForMatchUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MatchStartViewModel @Inject constructor(
    val getTeamsForMatchUC: GetTeamsForMatchUC,
) : ViewModel() {

    val homeTeam: MutableLiveData<TeamModel> = MutableLiveData()
    val awayTeam: MutableLiveData<TeamModel> = MutableLiveData()


    val minute: MutableLiveData<Int> = MutableLiveData()

    var isLegend: Boolean = true


    var teamPosition = Enums.TeamPosition.HOME
    val teams = mutableStateOf<List<TeamModel>>(emptyList())
    val showSelection = mutableStateOf(false)



    private val _state = MutableStateFlow(MatchStartState())
    val state: StateFlow<MatchStartState> get() = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val list = getTeamsForMatchUC()
            withContext(Dispatchers.Main) {
                teams.value = list
                if (teams.value.size > 0) {
                    homeTeam.value = teams.value[0]
                }
                if (teams.value.size > 1) {
                    awayTeam.value = teams.value[1]
                }
            }
        }
    }

    fun onAction(action: MatchStartAction) {
        when (action) {
            is MatchStartAction.Load -> _state.value = MatchStartState(isLoading = true)
            is MatchStartAction.SelectHomeTeam -> {
                _state.value = MatchStartState(homeTeam = action.team)
            }
            is MatchStartAction.SelectAwayTeam-> {
                _state.value = MatchStartState(awayTeam = action.team)
            }
        }
    }

    fun initMatch() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = getTeamsForMatchUC()
            withContext(Dispatchers.Main) {
                teams.value = list
                if (teams.value.size > 0) {
                    homeTeam.value = teams.value[0]
                }
                if (teams.value.size > 1) {
                    awayTeam.value = teams.value[1]
                }
            }
        }
    }

}