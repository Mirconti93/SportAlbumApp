package com.mircontapp.sportalbum.presentation.match

import android.text.style.BackgroundColorSpan
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.data.database.Player
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.presentation.commons.OnPlayerClickHandler
import com.mircontapp.sportalbum.presentation.ui.theme.BlueD
import com.mircontapp.sportalbum.presentation.ui.theme.Green
import com.mircontapp.sportalbum.presentation.ui.theme.LightGray
import com.mircontapp.sportalbum.presentation.ui.theme.OrangeYellowD
import com.mircontapp.sportalbum.presentation.ui.theme.PaleYellow
import com.mircontapp.sportalbum.presentation.viewmodels.MainViewModel

@Composable
fun MatchGameScreen(navController: NavController, mainViewModel: MainViewModel) {
    val viewModel: MatchViewModel = hiltViewModel()

    LaunchedEffect((Unit), block = {
        viewModel.initLineUp(mainViewModel.homeTeam, mainViewModel.awayTeam)
    })
    Row {
        when (viewModel.currentScreen.value) {
            MatchViewModel.Screen.LINE_UP_HOME -> LineUpSelection(viewModel = viewModel, position = MatchViewModel.TeamPosition.HOME)
            MatchViewModel.Screen.LINE_UP_HOME -> LineUpSelection(viewModel = viewModel, position = MatchViewModel.TeamPosition.AWAY)
            else -> Match()
        }

    }
}

@Composable
fun Match() {

}

@Composable
fun LineUpSelection(viewModel: MatchViewModel, position: MatchViewModel.TeamPosition) {
    val eleven= if (position == MatchViewModel.TeamPosition.HOME ) viewModel.homeEleven.collectAsState() else viewModel.awayEleven.collectAsState()
    val bench= if (position == MatchViewModel.TeamPosition.HOME ) viewModel.homeBench.collectAsState() else viewModel.awayBench.collectAsState()

    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(8.dp)) {
        val teamName = if (position == MatchViewModel.TeamPosition.HOME) viewModel.homeTeam.value?.name else viewModel.awayTeam.value?.name
        Text(text = teamName ?: "")
        Row {

            LazyColumn(modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1f)) {

                    items(eleven.value) {
                        PlayerLineUpItem(it, BlueD,
                            object : OnPlayerClickHandler {
                                override fun onPlayerClick(playerModel: PlayerModel) {
                                    Log.i("BUPI",  viewModel.playerSelected.value?.name ?: "Not selected")
                                    if (viewModel.playerSelected.value != null) {
                                        viewModel.substitutePlayer(viewModel.playerSelected.value!!, playerModel, MatchViewModel.TeamPosition.HOME)
                                    } else {
                                        viewModel.playerSelected.value = playerModel
                                    }
                                }
                            }
                        )
                    }
            }
            LazyColumn(modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .weight(1f)
                .padding(8.dp)) {
                    items(bench.value) {
                        PlayerLineUpItem(it, BlueD,
                            object : OnPlayerClickHandler {
                                override fun onPlayerClick(playerModel: PlayerModel) {
                                    Log.i("BUPI",  viewModel.playerSelected.value?.name ?: "Not selected")
                                    if (viewModel.playerSelected.value != null) {
                                        viewModel.substitutePlayer(viewModel.playerSelected.value!!, playerModel, MatchViewModel.TeamPosition.HOME)
                                    } else {
                                        viewModel.playerSelected.value = playerModel
                                    }
                                }
                            }
                        )
                    }
            }
        }

    }
}

@Composable
fun PlayerLineUpItem(player: PlayerModel, backgroundColor: Color, onPlayerClickHandler: OnPlayerClickHandler)  {
    Row(
        modifier = Modifier
            .background(backgroundColor, shape = RoundedCornerShape(2.dp))
            .fillMaxWidth()
            .padding(all = 2.dp)
            .clickable {
                onPlayerClickHandler.onPlayerClick(player)
            },
    ) {
        Text(text = player.name, color = White, modifier = Modifier.weight(3f))
        Text(text = SportAlbumApplication.instance.getString(player.roleMatch?.text ?: R.string.na),
            modifier = Modifier
                .padding(start = 4.dp)
                .weight(1f),
            color = OrangeYellowD,)
        Text(text = player.valueleg.toString(),
            modifier = Modifier
                .padding(start = 4.dp)
                .weight(1f),
            color = OrangeYellowD)


    }
    Spacer(modifier = Modifier.padding(2.dp))

}
