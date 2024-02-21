package com.mircontapp.sportalbum.presentation.match

import android.text.style.BackgroundColorSpan
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.data.database.Player
import com.mircontapp.sportalbum.domain.models.MatchModel
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.presentation.commons.OnItemClickHandler
import com.mircontapp.sportalbum.presentation.commons.OnPlayerClickHandler
import com.mircontapp.sportalbum.presentation.dashboard.DashboardViewModel
import com.mircontapp.sportalbum.presentation.ui.theme.BlueD
import com.mircontapp.sportalbum.presentation.ui.theme.Green
import com.mircontapp.sportalbum.presentation.ui.theme.LightBlue
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

    when (viewModel.currentScreen.value) {
        MatchViewModel.Screen.LINE_UP_HOME_START, MatchViewModel.Screen.LINE_UP_HOME -> LineUpSelection(viewModel = viewModel, position = MatchViewModel.TeamPosition.HOME)
        MatchViewModel.Screen.LINE_UP_AWAY_START, MatchViewModel.Screen.LINE_UP_AWAY -> LineUpSelection(viewModel = viewModel, position = MatchViewModel.TeamPosition.AWAY)
        else -> Match(viewModel)
    }


}

@Composable
fun Match(matchViewModel: MatchViewModel) {
    val matchModel = remember { matchViewModel.matchModel }
    if (matchModel != null)
        Row {
            Column {
                Text(text = matchModel.home)
            }
            Column {

            }
            Column {
                Text(text = matchModel.away)
            }


        }
}


@Composable
fun LineUpSelection(viewModel: MatchViewModel, position: MatchViewModel.TeamPosition) {
    val eleven= if (position == MatchViewModel.TeamPosition.HOME ) viewModel.homeEleven.collectAsState() else viewModel.awayEleven.collectAsState()
    val bench= if (position == MatchViewModel.TeamPosition.HOME ) viewModel.homeBench.collectAsState() else viewModel.awayBench.collectAsState()

    val isRoleSelection = remember { mutableStateOf(false) }
    val isModuleSelection = remember { mutableStateOf(false) }

    Row {
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(8.dp)) {
            val teamName = if (position == MatchViewModel.TeamPosition.HOME) viewModel.homeTeam.value?.name else viewModel.awayTeam.value?.name
            val module =  if (position == MatchViewModel.TeamPosition.HOME) viewModel.homeTeam.value?.module else viewModel.awayTeam.value?.module
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = teamName ?: "" , modifier = Modifier
                    .weight(0.7f)
                    .padding(start = 8.dp)
                    .size(16.dp))
                Button(onClick = { isModuleSelection.value = true },
                    colors = ButtonDefaults.buttonColors(containerColor = OrangeYellowD, contentColor = Black),
                    modifier = Modifier
                        .weight(0.3f)
                        .padding(0.dp)
                        .height(20.dp)) {
                        Text(text = SportAlbumApplication.instance.getString(module?.text ?: R.string.module))
                }
                Button(onClick = {viewModel.nextScreen()},
                    colors = ButtonDefaults.buttonColors(containerColor = OrangeYellowD, contentColor = Black),
                    modifier = Modifier
                        .weight(0.3f)
                        .padding(0.dp)
                        .height(20.dp)) {
                    Text(text = SportAlbumApplication.instance.getString(R.string.next))
                }
            }
            if (isModuleSelection.value) {
                Row {
                    LazyRow() {
                        items(Enums.MatchModule.values()) {
                            Text(text = SportAlbumApplication.instance.getString(it.text),
                                modifier = Modifier
                                    .background(BlueD)
                                    .clickable {
                                        viewModel.changeModule(position, it)
                                        isModuleSelection.value = false
                                    })
                            Spacer(modifier = Modifier.height(2.dp))
                        }
                    }
                }
            }
            Row {
                Text(SportAlbumApplication.instance.getString(R.string.titolars), modifier = Modifier
                    .weight(0.5f)
                    .padding(start = 8.dp))
                Text(SportAlbumApplication.instance.getString(R.string.bench), modifier = Modifier
                    .weight(0.5f)
                    .padding(start = 8.dp))
            }
            Row {
                LazyColumn(modifier = Modifier
                    .fillMaxHeight()
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
                            },
                            object: OnPlayerClickHandler {
                                override fun onPlayerClick(playerModel: PlayerModel) {
                                    viewModel.playerToChangeRole = playerModel
                                    isRoleSelection.value = true
                                }
                            }
                        )
                    }
                }
                if (isRoleSelection.value) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(8.dp)
                            .weight(0.2f)
                    ) {
                        items(Enums.RoleLineUp.values()) {
                            Text(text = SportAlbumApplication.instance.getString(it.text),
                                modifier = Modifier
                                    .background(BlueD)
                                    .clickable {
                                        viewModel.playerToChangeRole?.roleMatch = it
                                        viewModel.changePlayerRole(position)
                                        isRoleSelection.value = false
                                    })
                            Spacer(modifier = Modifier.height(2.dp))
                        }
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(8.dp)
                ) {
                    items(bench.value) {
                        PlayerLineUpItem(
                            it, LightBlue,
                            object : OnPlayerClickHandler {
                                override fun onPlayerClick(playerModel: PlayerModel) {
                                    Log.i(
                                        "BUPI",
                                        viewModel.playerSelected.value?.name ?: "Not selected"
                                    )
                                    if (viewModel.playerSelected.value != null) {
                                        viewModel.substitutePlayer(
                                            viewModel.playerSelected.value!!,
                                            playerModel,
                                            MatchViewModel.TeamPosition.HOME
                                        )
                                    } else {
                                        viewModel.playerSelected.value = playerModel
                                    }
                                }
                            }, null
                        )
                    }
                }

            }

        }
    }

}

@Composable
fun PlayerLineUpItem(player: PlayerModel, backgroundColor: Color, onPlayerClickHandler: OnPlayerClickHandler, onRoleClick: OnPlayerClickHandler?)  {
    Row(
        modifier = Modifier
            .background(backgroundColor, shape = RoundedCornerShape(2.dp))
            .padding(all = 2.dp)
            .fillMaxWidth()
            .clickable {
                onPlayerClickHandler.onPlayerClick(player)
            },
    ) {
        Text(text = player.name, color = White, modifier = Modifier
            .wrapContentWidth())
        Text(text = player.valueleg.toString(),
            modifier = Modifier
                .padding(start = 4.dp)
                .weight(1f),
            color = OrangeYellowD)
        val roleString = if (onRoleClick != null) SportAlbumApplication.instance.getString(player.roleMatch?.text ?: R.string.na) else SportAlbumApplication.instance.getString(player.roleLineUp?.text ?: R.string.na)
        Text(text = SportAlbumApplication.instance.getString(player.roleMatch?.text ?: R.string.na),
            modifier = Modifier
                .padding(start = 4.dp)
                .clickable(enabled = onRoleClick != null) {
                    onRoleClick?.onPlayerClick(player)
                },
            color = Yellow
        )


    }
    Spacer(modifier = Modifier.padding(2.dp))

}
