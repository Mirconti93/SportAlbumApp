package com.mircontapp.sportalbum.presentation.dashboard

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.presentation.album.AlbumViewModel
import com.mircontapp.sportalbum.presentation.album.TeamsGrid
import com.mircontapp.sportalbum.presentation.album.TeamsState
import com.mircontapp.sportalbum.presentation.commons.OnTeamClickHandler
import com.mircontapp.sportalbum.presentation.navigation.NavigationItem

@Composable
fun DashboardScreen() {
    val viewModel: DashboardViewModel = hiltViewModel()
    val navController = rememberNavController()
    Text(text = SportAlbumApplication.instance.getString(R.string.teams))
    if (viewModel.selectionType.value.equals(DashboardViewModel.SelectionType.TEAMS)) {
        if (viewModel.teams.value != null) {
            TeamsGrid(
                TeamsState(
                viewModel.teams.value!!,
                onTeamClickHandler = object : OnTeamClickHandler {
                    override fun onTeamClick(teamModel: TeamModel) {
                        navController.navigate(NavigationItem.LineUps.route)
                    }
                })
            )
        } else {
            Text(text = SportAlbumApplication.instance.getString(R.string.noTeams))
        }
    } else {

    }
}