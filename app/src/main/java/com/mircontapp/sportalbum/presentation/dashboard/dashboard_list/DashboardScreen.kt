package com.mircontapp.sportalbum.presentation.dashboard.dashboard_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.commons.FileDataManager
import com.mircontapp.sportalbum.commons.ext.toShortItem
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.presentation.album.ShortList
import com.mircontapp.sportalbum.presentation.album.TeamsGrid
import com.mircontapp.sportalbum.presentation.album.TeamsState
import com.mircontapp.sportalbum.presentation.commons.CustomCircularProgress
import com.mircontapp.sportalbum.presentation.commons.CustomTextField
import com.mircontapp.sportalbum.presentation.commons.OnEditClickHandler
import com.mircontapp.sportalbum.presentation.commons.OnPlayerClickHandler
import com.mircontapp.sportalbum.presentation.commons.OnTeamClickHandler
import com.mircontapp.sportalbum.presentation.commons.ShortListElement
import com.mircontapp.sportalbum.presentation.navigation.Routes
import com.mircontapp.sportalbum.presentation.ui.theme.BlueD
import com.mircontapp.sportalbum.presentation.ui.theme.OrangeYellowD


@ExperimentalMaterial3Api
@Composable
fun DashboardScreen(navController: NavController) {
    val viewModel: DashboardViewModel = hiltViewModel()
    Column(verticalArrangement = Arrangement.Top) {

        Text(SportAlbumApplication.instance.getString(R.string.dashboard), textAlign = TextAlign.Center, modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp, 8.dp))

        val state = viewModel.state.collectAsState()
        val isTeams = state.value.selectionType == SelectionType.TEAMS
        when {
            state.value.isLoading -> CustomCircularProgress(modifier = Modifier.fillMaxWidth())
            state.value.message != null -> state.value.message?.let { Text(text = it) }
            else -> {
                Row {

                    TabRow(selectedTabIndex = state.value.selectionType.ordinal, modifier = Modifier.height(40.dp)) {

                        Tab(selected = !isTeams,
                            onClick = {
                                viewModel.onAction(DashboardAction.ChangeSelection(SelectionType.PLAYERS))
                            },
                            text = {Text(SportAlbumApplication.instance.getString(R.string.playerList), color = if (!isTeams) Color.Black else Color.White)},
                            modifier = Modifier
                                .background(color = if (!isTeams) OrangeYellowD else BlueD)
                                .height(40.dp)
                        )

                        Tab(selected = isTeams,
                            onClick = {
                                viewModel.onAction(DashboardAction.ChangeSelection(SelectionType.TEAMS))
                            },
                            text = {Text(SportAlbumApplication.instance.getString(R.string.teams), color = if (isTeams) Color.Black else Color.White)},
                            modifier = Modifier
                                .background(color = if (isTeams) OrangeYellowD else BlueD)
                                .height(40.dp)
                        )
                    }
                }
            }
        }

        val showSearch = remember { mutableStateOf(true) }
        val searchText = remember { mutableStateOf(TextFieldValue("")) }
        Spacer(Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Filled.Create, contentDescription = "Search icon", modifier = Modifier
                    .size(40.dp)
                    .padding(2.dp, 8.dp)
                    .clickable { showSearch.value = !showSearch.value })
            if (showSearch.value) {
                CustomTextField(
                    value = searchText.value,
                    onValueChange = { newValue ->
                        searchText.value = newValue
                        newValue.text?.let {
                            if (isTeams) {
                                viewModel.onAction(DashboardAction.ShowTeamsFiltered(it))
                            } else {
                                viewModel.onAction(DashboardAction.ShowPlayersFiltered(it))
                            }
                        }

                    }
                )
            } else {
                Button(onClick = {
                    if (isTeams) {
                        navController.navigate(Routes.EditTeam(team = null))
                    } else {
                        navController.navigate(Routes.EditPlayer(player = null))
                    }
                }) {
                    Text(text = SportAlbumApplication.instance.getString(R.string.newItem))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    FileDataManager.writePlayers(
                        context = SportAlbumApplication.instance.applicationContext,
                        "players.txt", viewModel.players.value
                    )
                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!isTeams) OrangeYellowD else Color.Blue, contentColor = if (!isTeams) Color.Black else Color.White)) {
                    Text(text = SportAlbumApplication.instance.getString(R.string.saveData))
                }
            }


        }


        if (isTeams) {
            state.value.teams.let {
                TeamsGrid(
                    TeamsState(
                        it,
                        onTeamClickHandler = object : OnTeamClickHandler {
                            override fun onTeamClick(teamModel: TeamModel) {
                                navController.navigate(Routes.EditTeam(Gson().toJson(teamModel)))
                            }
                        }), Modifier.padding(4.dp)
                )
            }

        } else {
            val shortItemList = ArrayList<ShortListElement>()
            state.value.players.forEach {
                shortItemList.add(it.toShortItem(
                    onPlayerClickHandler = object : OnPlayerClickHandler {
                        override fun onPlayerClick(playerModel: PlayerModel) {
                            navController.navigate(Routes.Sticker(Gson().toJson(playerModel)))
                        }
                    },
                    onEditClickHandler = object : OnEditClickHandler {
                        override fun onPlayerClick(playerModel: PlayerModel) {
                            navController.navigate(Routes.EditPlayer(Gson().toJson(playerModel)))
                        }
                    }
                ))
            }
            ShortList(
                shortItemList
            )
        }
    }
}