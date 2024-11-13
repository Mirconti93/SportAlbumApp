package com.mircontapp.sportalbum.presentation.dashboard

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.commons.FileDataManager
import com.mircontapp.sportalbum.commons.customTextEdit
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.domain.models.toShortItem
import com.mircontapp.sportalbum.presentation.album.ShortList
import com.mircontapp.sportalbum.presentation.album.PlayersState
import com.mircontapp.sportalbum.presentation.album.TeamsGrid
import com.mircontapp.sportalbum.presentation.album.TeamsState
import com.mircontapp.sportalbum.presentation.commons.CustomTextField
import com.mircontapp.sportalbum.presentation.commons.OnEditClickHandler
import com.mircontapp.sportalbum.presentation.commons.OnPlayerClickHandler
import com.mircontapp.sportalbum.presentation.commons.OnTeamClickHandler
import com.mircontapp.sportalbum.presentation.commons.ShortListItem
import com.mircontapp.sportalbum.presentation.navigation.NavigationItem
import com.mircontapp.sportalbum.presentation.navigation.Routes
import com.mircontapp.sportalbum.presentation.ui.theme.BlueD
import com.mircontapp.sportalbum.presentation.ui.theme.BlueL
import com.mircontapp.sportalbum.presentation.ui.theme.OrangeYellowD
import com.mircontapp.sportalbum.presentation.viewmodels.MainViewModel


@ExperimentalMaterial3Api
@Composable
fun DashboardScreen(navController: NavController) {
    val viewModel: DashboardViewModel = hiltViewModel()
    Column(verticalArrangement = Arrangement.Top) {
        val isTeams = viewModel.selectionType.value.equals(DashboardViewModel.SelectionType.TEAMS)
        val isPlayers = viewModel.selectionType.value.equals(DashboardViewModel.SelectionType.PLAYERS)

        val players = viewModel.players.collectAsState()
        val teams = viewModel.teams.collectAsState()

        Text(SportAlbumApplication.instance.getString(R.string.dashboard), textAlign = TextAlign.Center, modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp, 8.dp))

        Row {
            TabRow(selectedTabIndex = viewModel.selectionType.value.ordinal, modifier = Modifier.height(40.dp)) {
                Tab(selected = isTeams,
                    onClick = { viewModel.selectionType.value = DashboardViewModel.SelectionType.TEAMS },
                    text = {Text(SportAlbumApplication.instance.getString(R.string.teams), color = if (isTeams) Color.Black else Color.White)},
                    modifier = Modifier
                        .background(color = if (isTeams) OrangeYellowD else BlueD)
                        .height(40.dp)
                )
                Tab(selected = isPlayers,
                    onClick = { viewModel.selectionType.value = DashboardViewModel.SelectionType.PLAYERS },
                    text = {Text(SportAlbumApplication.instance.getString(R.string.playerList), color = if (isPlayers) Color.Black else Color.White)},
                    modifier = Modifier
                        .background(color = if (isPlayers) OrangeYellowD else BlueD)
                        .height(40.dp)
                )
            }
        }

        val searchUIState = viewModel.searchUIState.collectAsState()

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
                                viewModel.filterTeams(it)
                            } else {
                                viewModel.filterPlayers(it)
                            }
                        }

                    }
                )
            } else {
                Button(onClick = {
                    if (isPlayers) {
                        navController.navigate(Routes.EditPlayer(player = null))
                    } else {
                        navController.navigate(Routes.EditTeam(team = null))
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


        }


        /*TextField(value = searchUIState.value.searchingText ?: "",
            onValueChange = { newValue -> viewModel.onSearch(newValue) },
            modifier = Modifier.fillMaxWidth().border(1.dp, color = BlueD, shape=RoundedCornerShape(4.dp)).height(56.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = LightBlue, // Cambia il colore dello sfondo
                textColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent// Cambia il colore del testo
            ),
            shape = RoundedCornerShape(4.dp),
            textStyle = TextStyle(fontSize = 14.sp, lineHeight = 16.sp),
            leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = "") },
            //label = { Text(SportAlbumApplication.instance.getString(R.string.search)) }
        )

        Row {

        }*/

        if (isTeams) {
            teams.value.let {
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
            val shortItemList = ArrayList<ShortListItem>()
            players.value.forEach {
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