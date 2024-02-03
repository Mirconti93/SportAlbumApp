package com.mircontapp.sportalbum.presentation.match

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.presentation.commons.OnPlayerClickHandler
import com.mircontapp.sportalbum.presentation.ui.theme.Green
import com.mircontapp.sportalbum.presentation.ui.theme.LightGray
import com.mircontapp.sportalbum.presentation.ui.theme.PaleYellow
import com.mircontapp.sportalbum.presentation.viewmodels.MainViewModel

@Composable
fun MatchGameScreen(navController: NavController, mainViewModel: MainViewModel) {
    val viewModel: MatchViewModel = hiltViewModel()

    LaunchedEffect((Unit), block = {
        viewModel.initLineUp(mainViewModel.homeTeam, mainViewModel.awayTeam)
    })

    Row {
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .weight(1f)
            .padding(8.dp)) {
            Text(text = mainViewModel.homeTeam?.name?: "")
            LazyColumn(modifier = Modifier.fillMaxHeight().fillMaxWidth()) {
                items(viewModel.homeRoster.value?: emptyList()) {
                    PlayerLineUpItem(it, viewModel.getLineUpPlace(it, MatchViewModel.TeamPosition.HOME), object : OnPlayerClickHandler {
                        override fun onPlayerClick(playerModel: PlayerModel) {
                            if (viewModel.playerSelected != null) {
                                viewModel.substitutePlayer(viewModel.playerSelected!!, playerModel, MatchViewModel.TeamPosition.HOME)
                            } else {
                                viewModel.playerSelected = playerModel
                            }

                        }
                    })
                }
            }
        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .weight(1f)
            .padding(8.dp),
            horizontalAlignment = Alignment.End) {
            Text(text = mainViewModel.awayTeam?.name ?: "")
            LazyColumn(modifier = Modifier.fillMaxHeight().fillMaxWidth()) {
                items(viewModel.awayRoster.value?: emptyList()) {
                    PlayerLineUpItem(it, viewModel.getLineUpPlace(it, MatchViewModel.TeamPosition.AWAY), object : OnPlayerClickHandler {
                        override fun onPlayerClick(playerModel: PlayerModel) {
                            if (viewModel.playerSelected != null) {
                                viewModel.substitutePlayer(viewModel.playerSelected!!, playerModel, MatchViewModel.TeamPosition.AWAY)
                            } else {
                                viewModel.playerSelected = playerModel
                            }

                        }
                    })
                }
            }

        }
    }






}

@Composable
fun PlayerLineUpItem(player: PlayerModel, lineUpPlace: MatchViewModel.LineUpPlace, onPlayerClickHandler: OnPlayerClickHandler)  {
    val backgroundColor = when (lineUpPlace) {
        MatchViewModel.LineUpPlace.FIELD-> Green
        MatchViewModel.LineUpPlace.BENCH-> PaleYellow
        else -> LightGray

    }
    Row(
        modifier = Modifier.background(backgroundColor, shape = RoundedCornerShape(2.dp)).fillMaxWidth().padding(2.dp),
    ) {
        Text(text = player.name, color = Black)
        Text(text = SportAlbumApplication.instance.getString(player.roleLineUp?.text ?: R.string.na),
            modifier = Modifier.padding(start = 4.dp),
            color = Black)


    }

}
