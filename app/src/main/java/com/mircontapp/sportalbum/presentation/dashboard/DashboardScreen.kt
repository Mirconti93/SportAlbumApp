package com.mircontapp.sportalbum.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.commons.FileDataManager
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.presentation.album.AlbumViewModel
import com.mircontapp.sportalbum.presentation.album.PlayersGrid
import com.mircontapp.sportalbum.presentation.album.PlayersShortList
import com.mircontapp.sportalbum.presentation.album.PlayersState
import com.mircontapp.sportalbum.presentation.album.TeamsGrid
import com.mircontapp.sportalbum.presentation.album.TeamsState
import com.mircontapp.sportalbum.presentation.commons.OnPlayerClickHandler
import com.mircontapp.sportalbum.presentation.commons.OnTeamClickHandler
import com.mircontapp.sportalbum.presentation.navigation.NavigationItem
import com.mircontapp.sportalbum.presentation.ui.theme.LightBlue
import com.mircontapp.sportalbum.presentation.ui.theme.OrangeYellowD
import com.mircontapp.sportalbum.presentation.viewmodels.MainViewModel


@ExperimentalMaterial3Api
@Composable
fun DashboardScreen(navController: NavController, mainViewModel: MainViewModel) {
    val viewModel: DashboardViewModel = hiltViewModel()
    Column(verticalArrangement = Arrangement.Top) {
        val isTeams = viewModel.selectionType.value.equals(DashboardViewModel.SelectionType.TEAMS)
        val isPlayers = viewModel.selectionType.value.equals(DashboardViewModel.SelectionType.PLAYERS)

        val players = viewModel.players.collectAsState()
        val teams = viewModel.teams.collectAsState()

        Row {
           Button(onClick = { viewModel.selectionType.value = DashboardViewModel.SelectionType.TEAMS },
               shape = RoundedCornerShape(2.dp),
               colors = ButtonDefaults.buttonColors(containerColor = if (isTeams) OrangeYellowD else Color.Blue, contentColor = if (isTeams) Color.Black else Color.White)) {
               Text(text = SportAlbumApplication.instance.getString(R.string.teams))
            }
            Spacer(modifier = Modifier.width(4.dp))
            Button(onClick = { viewModel.selectionType.value = DashboardViewModel.SelectionType.PLAYERS },
                shape = RoundedCornerShape(2.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isPlayers) OrangeYellowD else Color.Blue, contentColor = if (isPlayers) Color.Black else Color.White)) {
                Text(text = SportAlbumApplication.instance.getString(R.string.playerList))
            }
        }

        Row {

            Button(onClick = {
                if (isPlayers) {
                    navController.navigate(NavigationItem.EditPlayer.route)
                } else {
                    navController.navigate(NavigationItem.EditTeam.route)
                }
            }) {
                Text(text = SportAlbumApplication.instance.getString(R.string.newItem))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (players.value != null) {
                    FileDataManager.writePlayers(
                        context = SportAlbumApplication.instance.applicationContext,
                        "players.txt", viewModel.players.value!!
                    )
                }},
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isPlayers) OrangeYellowD else Color.Blue, contentColor = if (isPlayers) Color.Black else Color.White)) {
                Text(text = SportAlbumApplication.instance.getString(R.string.saveData))
            }
        }


        Column {
            val searchUIState = viewModel.searchUIState.collectAsState()
            TextField(value = searchUIState.value.searchingText ?: "",
                onValueChange = { newValue -> viewModel.onSearch(newValue) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = LightBlue, // Cambia il colore dello sfondo
                    textColor = Color.White// Cambia il colore del testo
                ),
                textStyle = TextStyle(fontSize = 14.sp),
                leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = "") },
                label = { Text(SportAlbumApplication.instance.getString(R.string.search)) }
            )

            if (isTeams) {
                teams.value.let {
                    TeamsGrid(
                        TeamsState(
                            it,
                            onTeamClickHandler = object : OnTeamClickHandler {
                                override fun onTeamClick(teamModel: TeamModel) {
                                    mainViewModel.teamModel = teamModel
                                    navController.navigate(NavigationItem.EditTeam.route)
                                }
                            }), Modifier.padding(4.dp)
                    )
                }

            } else {

                PlayersShortList(
                    PlayersState(
                        players.value,
                        onPlayerClickHandler = object : OnPlayerClickHandler {
                            override fun onPlayerClick(playerModel: PlayerModel) {
                                mainViewModel.playerModel = playerModel
                                navController.navigate(NavigationItem.EditPlayer.route)
                            }
                        })
                )

            }
        }


    }

}