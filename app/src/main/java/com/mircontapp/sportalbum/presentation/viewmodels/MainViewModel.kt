package com.mircontapp.sportalbum.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.data.datasource.FirebaseDataSource
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.presentation.navigation.NavigationItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel()  {

    var routeSelected: MutableLiveData<NavigationItem> = MutableLiveData()
    var teamModel: TeamModel? = null
    var homeTeam: TeamModel? = null
    var awayTeam: TeamModel? = null
    var playerModel: PlayerModel? = null

}