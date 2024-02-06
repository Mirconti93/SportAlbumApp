package com.mircontapp.sportalbum.presentation.dashboard

import android.content.res.Resources.Theme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.presentation.album.AlbumViewModel
import com.mircontapp.sportalbum.presentation.album.PlayersGrid
import com.mircontapp.sportalbum.presentation.album.PlayersState
import com.mircontapp.sportalbum.presentation.album.TeamsGrid
import com.mircontapp.sportalbum.presentation.album.TeamsState
import com.mircontapp.sportalbum.presentation.commons.OnPlayerClickHandler
import com.mircontapp.sportalbum.presentation.commons.OnTeamClickHandler
import com.mircontapp.sportalbum.presentation.navigation.NavigationItem
import com.mircontapp.sportalbum.presentation.ui.theme.OrangeYellowD
import com.mircontapp.sportalbum.presentation.viewmodels.MainViewModel

@Composable
fun DashboardScreen(navController: NavController, mainViewModel: MainViewModel) {
    val viewModel: DashboardViewModel = hiltViewModel()
    Column {
        val isTeams = viewModel.selectionType.value.equals(DashboardViewModel.SelectionType.TEAMS)
        val isPlayers = viewModel.selectionType.value.equals(DashboardViewModel.SelectionType.PLAYERS)

        Row {
           Button(onClick = { viewModel.selectionType.value = DashboardViewModel.SelectionType.TEAMS },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isTeams) OrangeYellowD else Color.Blue, contentColor = if (isTeams) Color.Black else Color.White)) {
                Text(text = SportAlbumApplication.instance.getString(R.string.teams))
            }
            Button(onClick = { viewModel.selectionType.value = DashboardViewModel.SelectionType.PLAYERS },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isPlayers) OrangeYellowD else Color.Blue, contentColor = if (isPlayers) Color.Black else Color.White)) {
                Text(text = SportAlbumApplication.instance.getString(R.string.playerList))
            }
        }

        if (isTeams) {
            if (viewModel.teams.value != null) {
                TeamsGrid(
                    TeamsState(
                        viewModel.teams.value!!,
                        onTeamClickHandler = object : OnTeamClickHandler {
                            override fun onTeamClick(teamModel: TeamModel) {
                                mainViewModel.teamModel = teamModel
                                navController.navigate(NavigationItem.EditTeam.route)
                            }
                        }), Modifier
                )
            } else {
                Text(text = SportAlbumApplication.instance.getString(R.string.noTeams))
            }
        } else {
            Row {
                Button(onClick = { viewModel.selectionType.value = DashboardViewModel.SelectionType.PLAYERS },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isPlayers) OrangeYellowD else Color.Blue, contentColor = if (isPlayers) Color.Black else Color.White)) {
                    Text(text = SportAlbumApplication.instance.getString(R.string.playerList))
                }
            }
            if (viewModel.players.value != null) {
                PlayersGrid(
                    PlayersState(
                        viewModel.players.value!!,
                        onPlayerClickHandler = object : OnPlayerClickHandler {
                            override fun onPlayerClick(playerModel: PlayerModel) {
                                mainViewModel.playerModel = playerModel
                                navController.navigate(NavigationItem.EditPlayer.route)
                            }
                        })
                )
            } else {
                Text(text = SportAlbumApplication.instance.getString(R.string.noTeams))
            }
        }
    }

}