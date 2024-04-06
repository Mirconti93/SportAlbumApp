package com.mircontapp.sportalbum.presentation.album

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.usecases.GetAllTeamsUC
import com.mircontapp.sportalbum.domain.usecases.GetPlayersByTeamLegendUC
import com.mircontapp.sportalbum.domain.usecases.GetPlayersByTeamUC
import com.mircontapp.sportalbum.domain.usecases.GetTeamsFromAreaUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TeamAlbumViewModel @Inject constructor(
    val getPlayersByTeamUC: GetPlayersByTeamUC,
    val getPlayersByTeamLegendUC: GetPlayersByTeamLegendUC,
) : ViewModel() {
    val players = mutableStateOf<List<PlayerModel>>(emptyList())
    val showSelection = mutableStateOf(true)

    fun playersFromTeamLegend(team: TeamModel?) :Boolean {
        if (team != null) {
            viewModelScope.launch(Dispatchers.IO) {
                val list = getPlayersByTeamLegendUC.getPlayers(team)
                withContext(Dispatchers.Main) {
                    players.value = list.sortedWith(compareBy<PlayerModel> { it.roleLineUp }.thenByDescending { it.valueleg })
                }
            }
        }
        return true
    }

}

