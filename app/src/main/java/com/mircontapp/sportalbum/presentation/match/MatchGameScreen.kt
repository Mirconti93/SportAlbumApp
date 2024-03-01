package com.mircontapp.sportalbum.presentation.match

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.commons.UIHelper
import com.mircontapp.sportalbum.domain.models.MatchModel
import com.mircontapp.sportalbum.domain.models.PlayerMatchModel
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.presentation.commons.OnPlayerMatchClickHandler
import com.mircontapp.sportalbum.presentation.commons.OnPlayerClickHandler
import com.mircontapp.sportalbum.presentation.ui.theme.BlueD
import com.mircontapp.sportalbum.presentation.ui.theme.LightBlue
import com.mircontapp.sportalbum.presentation.ui.theme.OrangeYellowD
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
    matchViewModel.matchModel.collectAsState().let {matchModel->

        Column(modifier = Modifier.fillMaxHeight(), horizontalAlignment = CenterHorizontally) {
            Row(modifier = Modifier.padding(16.dp, 8.dp)) {
                MatchScore(modifier = Modifier
                    .padding(4.dp, 2.dp)
                    .weight(1f), matchModel = matchModel.value, position = MatchViewModel.TeamPosition.HOME )
                MatchScore(modifier = Modifier.weight(1f), matchModel = matchModel.value, position = MatchViewModel.TeamPosition.AWAY )
            }
            Row(modifier = Modifier.padding(8.dp, 0.dp)) {

                PlayersInMatch(modifier = Modifier
                    .weight(0.35f).padding(2.dp)
                    .background(UIHelper.getColorByString(matchViewModel.homeTeam.value?.color1)), viewModel = matchViewModel, MatchViewModel.TeamPosition.HOME)
                Image(
                    painter = painterResource(UIHelper.getDrawableId(matchModel.value.protagonista ?: "", R.drawable.no_photo_icon)),
                    contentDescription = "Team icon",
                    modifier = Modifier.weight(0.3f),
                    contentScale = ContentScale.FillWidth
                )
                PlayersInMatch(modifier = Modifier
                    .weight(0.35f).padding(2.dp)
                    .background(UIHelper.getColorByString(matchViewModel.awayTeam.value?.color1)), viewModel = matchViewModel, MatchViewModel.TeamPosition.AWAY)
            }

            matchModel.value.comment.let {
                if (it.size>0) {
                    Text(text = matchModel.value.comment[0])
                }
                if (it.size>1) {
                    Text(text = matchModel.value.comment[1])
                }

            }

            Button(onClick = { matchViewModel.nextAction() }) {
                Text(text = SportAlbumApplication.instance.applicationContext.getString(R.string.next))

            }


        }


    }




}

@Composable
fun MatchScore(modifier: Modifier, matchModel: MatchModel, position: MatchViewModel.TeamPosition) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        val teamName =  if (position == MatchViewModel.TeamPosition.HOME) matchModel.home else matchModel.away
        val score = if (position == MatchViewModel.TeamPosition.HOME) matchModel.homeScore else matchModel.awayScore
        Text(text = teamName, fontSize = 16.sp, color = OrangeYellowD)
        Spacer(modifier = Modifier.height(8.dp))
        Image(
            painter = painterResource(
                UIHelper.getDrawableId(
                    teamName,
                    R.drawable.empty_logo
                )
            ),
            contentDescription = "Team icon", // Descrizione opzionale per l'accessibilità
            modifier = Modifier.size(80.dp),
            contentScale = ContentScale.FillWidth
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = score.toString(),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun PlayersInMatch(modifier: Modifier, viewModel: MatchViewModel, position: MatchViewModel.TeamPosition) {
    LazyColumn(modifier = modifier) {
        val players = if (position == MatchViewModel.TeamPosition.HOME) viewModel.matchModel.value.playersHome else viewModel.matchModel.value.playersAway
        items(players ?: emptyList()) {
            Text(text = UIHelper.minifiyName(it.name), fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }

    }
}


@Composable
fun LineUpSelection(viewModel: MatchViewModel, position: MatchViewModel.TeamPosition) {
    val eleven= if (position == MatchViewModel.TeamPosition.HOME ) viewModel.homeEleven.collectAsState() else viewModel.awayEleven.collectAsState()
    val bench= if (position == MatchViewModel.TeamPosition.HOME ) viewModel.homeBench.collectAsState() else viewModel.awayBench.collectAsState()

    val isRoleSelection = remember { mutableStateOf(false) }
    val isModuleSelection = remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(8.dp)) {
        val teamName = if (position == MatchViewModel.TeamPosition.HOME) viewModel.homeTeam.value?.name else viewModel.awayTeam.value?.name
        val module =  if (position == MatchViewModel.TeamPosition.HOME) viewModel.homeTeam.value?.module else viewModel.awayTeam.value?.module
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = teamName ?: "" , modifier = Modifier
                .weight(0.5f)
                .padding(start = 8.dp),
                fontSize = 16.sp,
                color = OrangeYellowD)

            Text(text = SportAlbumApplication.instance.getString(module?.text ?: R.string.module), modifier = Modifier.clickable { isModuleSelection.value = true  }, fontSize = 16.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {viewModel.nextScreen()},
                colors = ButtonDefaults.buttonColors(containerColor = OrangeYellowD, contentColor = Black),
                modifier = Modifier
                    .weight(0.25f)
                    .padding(2.dp)
                    .height(35.dp)) {
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
                                .padding(4.dp)
                                .clickable {
                                    viewModel.changeModule(position, it)
                                    isModuleSelection.value = false
                                })
                        Spacer(modifier = Modifier.width(2.dp))
                    }
                }
            }
        }
        Text(SportAlbumApplication.instance.getString(R.string.titolars), modifier = Modifier
            .padding(start = 8.dp))

        LazyColumn(modifier = Modifier
            .weight(1.15f)
            .padding(8.dp)) {
            items(eleven.value) {
                PlayerLineUpItem(it, BlueD,
                    object : OnPlayerMatchClickHandler {
                        override fun onPlayerClick(playerModel: PlayerMatchModel) {
                            Log.i("BUPI",  viewModel.playerSelected.value?.name ?: "Not selected")
                            if (viewModel.playerSelected.value != null) {
                                viewModel.substitutePlayer(viewModel.playerSelected.value!!, playerModel, position)
                            } else {
                                viewModel.playerSelected.value = playerModel
                            }
                        }
                    },
                    object: OnPlayerMatchClickHandler {
                        override fun onPlayerClick(playerModel: PlayerMatchModel) {
                            viewModel.playerToChangeRole = playerModel
                            isRoleSelection.value = true
                        }
                    }
                )
            }
        }
        if (isRoleSelection.value) {
            LazyRow(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                items(Enums.RoleLineUp.values()) {
                    Text(text = SportAlbumApplication.instance.getString(it.text),
                        modifier = Modifier
                            .background(BlueD)
                            .padding(4.dp)
                            .clickable {
                                viewModel.playerToChangeRole?.roleMatch = it
                                viewModel.changePlayerRole(position)
                                isRoleSelection.value = false
                            })
                    Spacer(modifier = Modifier.width(2.dp))
                }
            }
        }
        Text(SportAlbumApplication.instance.getString(R.string.bench), modifier = Modifier
            .padding(start = 8.dp))
        LazyColumn(
            modifier = Modifier
                .weight(0.85f)
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(bench.value) {
                PlayerLineUpItem(
                    it, LightBlue,
                    object : OnPlayerMatchClickHandler {
                        override fun onPlayerClick(playerModel: PlayerMatchModel) {
                            Log.i(
                                "BUPI",
                                viewModel.playerSelected.value?.name ?: "Not selected"
                            )
                            if (viewModel.playerSelected.value != null) {
                                viewModel.substitutePlayer(
                                    viewModel.playerSelected.value!!,
                                    playerModel,
                                    position
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

@Composable
fun PlayerLineUpItem(player: PlayerMatchModel, backgroundColor: Color, onPlayerClickHandler: OnPlayerMatchClickHandler, onRoleClick: OnPlayerMatchClickHandler?)  {
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
