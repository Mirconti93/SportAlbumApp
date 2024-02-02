package com.mircontapp.sportalbum.presentation.match

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.commons.UIHelper
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.presentation.album.TeamsGrid
import com.mircontapp.sportalbum.presentation.album.TeamsState
import com.mircontapp.sportalbum.presentation.commons.OnClickHandler
import com.mircontapp.sportalbum.presentation.commons.OnTeamClickHandler
import com.mircontapp.sportalbum.presentation.navigation.NavigationItem
import com.mircontapp.sportalbum.presentation.ui.theme.OrangeYellowD
import com.mircontapp.sportalbum.presentation.viewmodels.MainViewModel

@Composable
fun MatchStartScreen(navController: NavController, mainViewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val viewModel: MatchViewModel = hiltViewModel()
        LaunchedEffect(key1 = (Unit), block = {
            viewModel.initMatch()
        })

        Text(text = SportAlbumApplication.instance.getString(R.string.teams))
        if (viewModel.showSelection.value) {
            if (viewModel.teams.value != null) {
                TeamsGrid(TeamsState(
                    viewModel.teams.value!!,
                    onTeamClickHandler = object : OnTeamClickHandler {
                        override fun onTeamClick(teamModel: TeamModel) {
                            if (viewModel.teamPosition == MatchViewModel.TeamPosition.HOME) {
                                viewModel.homeTeam.value = teamModel
                            } else {
                                viewModel.awayTeam.value = teamModel
                            }
                            viewModel.showSelection.value = false
                        }
                    })
                )
            } else {
                Text(text = SportAlbumApplication.instance.getString(R.string.noTeams))
            }
        } else {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(8.dp)
                    ) {
                        TeamSelected(team = viewModel.homeTeam.value?.name, object : OnClickHandler {
                            override fun onClick() {
                                viewModel.let {
                                    it.showSelection.value = true
                                    it.teamPosition = MatchViewModel.TeamPosition.HOME
                                }
                            }
                        })
                    }
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(8.dp)) {
                        TeamSelected(team = viewModel.awayTeam.value?.name, object : OnClickHandler {
                            override fun onClick() {
                                viewModel.let {
                                    it.showSelection.value = true
                                    it.teamPosition = MatchViewModel.TeamPosition.AWAY
                                }
                            }
                        })
                    }
                }
                Button(onClick = {
                    if (viewModel.homeTeam.value != null && viewModel.awayTeam.value != null) {
                        mainViewModel.homeTeam = viewModel.homeTeam.value!!
                        mainViewModel.awayTeam = viewModel.awayTeam.value!!
                        navController.navigate(NavigationItem.LineUps.route)
                    }

                }, modifier = Modifier.background(OrangeYellowD)) {
                    Text(text = SportAlbumApplication.instance.getString(R.string.next), color = Color.Black)
                }
            }


        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamSelected(team: String?, onClickHandler: OnClickHandler) {
    Column {
        Card(
            modifier = Modifier.shadow(2.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
            ),
            shape = RoundedCornerShape(8.dp),
            onClick = {onClickHandler.onClick()}

            ) {
            val name = if (team != null) team else SportAlbumApplication.instance.getString(R.string.notSelected)
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                val idDrawable = UIHelper.getDrawableId(name, R.drawable.empty_logo)
                Image(
                    painter = painterResource(idDrawable),
                    contentDescription = "Team icon", // Descrizione opzionale per l'accessibilità
                    modifier = Modifier.size(140.dp),
                    contentScale = ContentScale.Crop
                )
                Text(modifier = Modifier, text = name)
            }

        }
    }

}

