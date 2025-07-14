package com.mircontapp.sportalbum.presentation.commons.team_select

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.presentation.album.TeamsGrid
import com.mircontapp.sportalbum.presentation.album.TeamsState
import com.mircontapp.sportalbum.presentation.commons.CustomCircularProgress
import com.mircontapp.sportalbum.presentation.commons.OnTeamClickHandler
import com.mircontapp.sportalbum.presentation.commons.ScreenTitle

@Composable
fun TeamSelectView(isLegend: Boolean, nextAction : (teamModel: TeamModel) -> Unit) {
    val viewModel: TeamSelectViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()

    LaunchedEffect (key1 = (Unit), block = {
        viewModel.onAction(TeamSelectAction.ShowTeamsByArea(Enums.Area.SERIEA, isLegend))
    })
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        ScreenTitle(text = SportAlbumApplication.instance.getString(R.string.chooseTeams))
        Row(
            Modifier
                .padding(2.dp)
                .horizontalScroll(rememberScrollState())) {
            Enums.Area.values().forEach { 
                Button(onClick = { 
                    viewModel.onAction(TeamSelectAction.ShowTeamsByArea(it, isLegend))
                }) {
                    Text(text = it.text)
                }

                Spacer(modifier = Modifier.width(4.dp))
            }
        }

        when {
            state.value.isLoading -> CustomCircularProgress(modifier = Modifier.fillMaxWidth())
            state.value.message != null -> state.value.message?.let { Text(text = it) }
            state.value.teams.isNotEmpty() -> {
                TeamsGrid(TeamsState(state.value.teams, object : OnTeamClickHandler {
                    override fun onTeamClick(teamModel: TeamModel) {
                        nextAction(teamModel)
                    }
                }),
                    Modifier
                        .fillMaxHeight()
                        .padding(4.dp))
            }
            else -> Text(modifier = Modifier.fillMaxWidth().fillMaxHeight(), text = SportAlbumApplication.getString(R.string.genericError))
        }

    }

}



