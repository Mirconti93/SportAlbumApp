package com.mircontapp.sportalbum.presentation.match

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.presentation.album.AlbumViewModel
import com.mircontapp.sportalbum.presentation.album.TeamsGrid
import com.mircontapp.sportalbum.presentation.album.TeamsState
import com.mircontapp.sportalbum.presentation.commons.OnTeamClickHandler

@Composable
fun MatchScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val viewModel: AlbumViewModel = hiltViewModel()
        Text(text = SportAlbumApplication.instance.getString(R.string.teams))
        if (viewModel.showSelection.value) {
            if (viewModel.teams.value != null) {
                TeamsGrid(
                    TeamsState(
                        viewModel.teams.value!!,
                        onTeamClickHandler = object : OnTeamClickHandler {
                            override fun onTeamClick(teamModel: TeamModel) {
                                TODO("Not yet implemented")
                            }
                        })
                )
            } else {
                Text(text = SportAlbumApplication.instance.getString(R.string.noTeams))
            }
        } else {

        }
    }

}

@Composable
fun MatchChoice(homeTeam: String, awayTeam: String) {
    Column {
        TeamSelected(team = homeTeam)
    }
    Column {
        TeamSelected(team = awayTeam)
    }
}

@Composable
fun TeamSelected(team: String) {
    Column {

    }

}

