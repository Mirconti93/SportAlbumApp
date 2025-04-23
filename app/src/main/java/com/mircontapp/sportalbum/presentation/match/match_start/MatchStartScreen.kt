package com.mircontapp.sportalbum.presentation.match.match_start

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.google.gson.Gson
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.commons.ext.getColorByString
import com.mircontapp.sportalbum.commons.ext.getDrawableId
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.presentation.album.TeamsGrid
import com.mircontapp.sportalbum.presentation.album.TeamsState
import com.mircontapp.sportalbum.presentation.commons.OnClickHandler
import com.mircontapp.sportalbum.presentation.commons.OnTeamClickHandler
import com.mircontapp.sportalbum.presentation.match.MatchViewModel
import com.mircontapp.sportalbum.presentation.navigation.Routes
import com.mircontapp.sportalbum.presentation.ui.theme.OrangeYellowD

@Composable
fun MatchStartScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val viewModel: MatchViewModel = hiltViewModel()
        LaunchedEffect(key1 = (Unit), block = {
            viewModel.initMatch()
        })

        Text(text = SportAlbumApplication.instance.getString(R.string.match))
        if (viewModel.showSelection.value) {
            if (viewModel.teams.value != null) {
                TeamsGrid(TeamsState(
                    viewModel.teams.value!!,
                    onTeamClickHandler = object : OnTeamClickHandler {
                        override fun onTeamClick(teamModel: TeamModel) {
                            if (viewModel.teamPosition == Enums.TeamPosition.HOME) {
                                viewModel.homeTeam.value = teamModel
                            } else {
                                viewModel.awayTeam.value = teamModel
                            }
                            viewModel.showSelection.value = false
                        }
                    }), Modifier
                )
            } else {
                Text(text = SportAlbumApplication.instance.getString(R.string.noTeams))
            }
        } else {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxHeight()
            ) {

                TeamSelected(modifier = Modifier.weight(1f),
                    team = viewModel.homeTeam.value, object : OnClickHandler {
                    override fun onClick() {
                        viewModel.let {
                            it.showSelection.value = true
                            it.teamPosition = Enums.TeamPosition.HOME
                        }
                    }
                })
                Text(text = SportAlbumApplication.instance.getString(R.string.vs))
                TeamSelected(modifier = Modifier.weight(1f),
                    team = viewModel.awayTeam.value, object : OnClickHandler {
                    override fun onClick() {
                        viewModel.let {
                            it.showSelection.value = true
                            it.teamPosition = Enums.TeamPosition.AWAY
                        }
                    }
                })
                Button(onClick = {
                    viewModel.homeTeam.value?.let { homeTeam ->
                        viewModel.awayTeam.value?.let { awayTeam ->
                            navController.navigate(Routes.Match(
                                homeTeam = Gson().toJson(homeTeam),
                                awayTeam = Gson().toJson(awayTeam)
                            ))
                        }
                    }
                },  colors = ButtonDefaults.buttonColors(
                    containerColor = OrangeYellowD, contentColor = Color.Black
                )) {
                    Text(text = SportAlbumApplication.instance.getString(R.string.next))
                }
            }

        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamSelected(modifier: Modifier, team: TeamModel?, onClickHandler: OnClickHandler) {
    Column(modifier = modifier.padding(16.dp, 8.dp)) {
        val bkgColor = team?.color1.getColorByString()
        Card(
            modifier = Modifier.shadow(2.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(
                containerColor = bkgColor,
            ),
            shape = RoundedCornerShape(4.dp),
            onClick = {onClickHandler.onClick()}

            ) {
            val name = if (team != null) team.name else SportAlbumApplication.instance.getString(R.string.notSelected)
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                val idDrawable = name.getDrawableId(R.drawable.empty_logo)
                Image(
                    painter = painterResource(idDrawable),
                    contentDescription = "Team icon", // Descrizione opzionale per l'accessibilità
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(32.dp),
                    contentScale = ContentScale.Crop
                )
                Text(modifier = Modifier, text = name)
            }

        }
    }

}

