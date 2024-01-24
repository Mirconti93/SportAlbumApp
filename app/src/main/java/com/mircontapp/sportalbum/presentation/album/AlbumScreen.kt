package com.mircontapp.sportalbum.presentation.album

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.commons.UIHelper
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.presentation.commons.OnTeamClickHandler
import com.mircontapp.sportalbum.presentation.navigation.NavigationItem
import com.mircontapp.sportalbum.presentation.viewmodels.MainViewModel

@Composable
fun AlbumScreen(navController: NavController, mainViewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val viewModel: AlbumViewModel = hiltViewModel()
        Text(text = SportAlbumApplication.instance.getString(R.string.teams))
        if (viewModel.teams.value != null) {
            val teams = viewModel.teams.value
            TeamsGrid(TeamsState(teams, object : OnTeamClickHandler {
                override fun onTeamClick(teamModel: TeamModel) {
                    mainViewModel.teamModel = teamModel
                    navController.navigate(NavigationItem.TeamAlbum.route)
                }
            }))


        } else {
            Text(text = SportAlbumApplication.instance.getString(R.string.noTeams))
        }

    }

}

