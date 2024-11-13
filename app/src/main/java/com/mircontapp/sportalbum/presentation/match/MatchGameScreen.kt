package com.mircontapp.sportalbum.presentation.match

import android.util.LayoutDirection
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Alignment.Companion.TopCenter
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
import com.google.gson.Gson
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.commons.UIHelper
import com.mircontapp.sportalbum.domain.models.MatchModel
import com.mircontapp.sportalbum.domain.models.PlayerMatchModel
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.presentation.commons.OnPlayerMatchClickHandler
import com.mircontapp.sportalbum.presentation.commons.OnPlayerClickHandler
import com.mircontapp.sportalbum.presentation.ui.theme.BlueD
import com.mircontapp.sportalbum.presentation.ui.theme.Green
import com.mircontapp.sportalbum.presentation.ui.theme.LightBlue
import com.mircontapp.sportalbum.presentation.ui.theme.OrangeYellowD
import com.mircontapp.sportalbum.presentation.ui.theme.YellowD
import com.mircontapp.sportalbum.presentation.viewmodels.MainViewModel

@Composable
fun MatchGameScreen(navController: NavController, homeTeamArg: String, awayTeamArg: String) {
    val viewModel: MatchViewModel = hiltViewModel()


    val homeTeam = Gson().fromJson(homeTeamArg, TeamModel::class.java)
    val awayTeam = Gson().fromJson(awayTeamArg, TeamModel::class.java)
    LaunchedEffect((Unit), block = {
        viewModel.initLineUp(homeTeam, awayTeam)
    })

    when (viewModel.currentScreen.value) {
        MatchViewModel.Screen.LINE_UP_HOME_START, MatchViewModel.Screen.LINE_UP_HOME -> LineUpSelection(viewModel = viewModel, position = Enums.Possesso.HOME)
        MatchViewModel.Screen.LINE_UP_AWAY_START, MatchViewModel.Screen.LINE_UP_AWAY -> LineUpSelection(viewModel = viewModel, position = Enums.Possesso.AWAY)
        else -> Match(viewModel)
    }


}

@Composable
fun Match(matchViewModel: MatchViewModel) {
    val matchModel = matchViewModel.matchModel.collectAsState()

    Column(modifier = Modifier.fillMaxHeight(), horizontalAlignment = CenterHorizontally) {
        Row(modifier = Modifier.padding(16.dp, 8.dp)) {
            MatchScore(modifier = Modifier
                .padding(4.dp, 2.dp)
                .weight(1f), matchModel = matchModel.value, position = Enums.Possesso.HOME )
            Text(text = matchModel.value.minute.toString() + "'", modifier = Modifier.padding(8.dp, 20.dp))
            MatchScore(modifier = Modifier.weight(1f), matchModel = matchModel.value, position = Enums.Possesso.AWAY )
        }
        Row(modifier = Modifier.padding(8.dp, 0.dp)) {

            PlayersInMatch(modifier = Modifier
                .weight(0.35f), viewModel = matchViewModel, Enums.Possesso.HOME)
            Column(modifier = Modifier
                .weight(0.3f)
                .padding(2.dp), horizontalAlignment = CenterHorizontally) {
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = painterResource(UIHelper.getDrawableId(matchModel.value.protagonista ?: "", R.drawable.giocatore)),
                    contentDescription = "Team icon",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.height(100.dp)
                )
                val idDrawable = UIHelper.getDrawableId(matchModel.value.coprotagonista ?: "", R.drawable.giocatore)
                if (idDrawable != R.drawable.giocatore) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Image(
                        painter = painterResource(idDrawable),
                        contentDescription = "Team icon",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.height(80.dp)

                    )
                }
            }


            PlayersInMatch(modifier = Modifier
                .weight(0.35f), viewModel = matchViewModel, Enums.Possesso.AWAY)
        }

        Column(modifier = Modifier
            .weight(1f)
            .padding(8.dp)) {
            matchModel.value.comment.let {
                if (it.size>0) {
                    val comment = matchModel.value.comment[it.size-1]
                    val bckColor = if (comment.possesso == Enums.Possesso.HOME) matchViewModel.homeTeam.value?.color1 else matchViewModel.awayTeam.value?.color1
                    val textColor = UIHelper.getTeamTextColor(bckColor)
                    Text(text = "${comment.minute}' ${comment.text}", modifier = Modifier
                        .fillMaxWidth()
                        .background(UIHelper.getColorByString(bckColor), RoundedCornerShape(2.dp))
                        .padding(8.dp, 2.dp),
                        color = textColor)
                }
                if (it.size>1) {
                    Spacer(modifier = Modifier.height(2.dp))
                    val comment = matchModel.value.comment[it.size-2]
                    val bckColor = if (comment.possesso == Enums.Possesso.HOME) matchViewModel.homeTeam.value?.color1 else matchViewModel.awayTeam.value?.color1
                    val textColor = UIHelper.getTeamTextColor(bckColor)
                    Text(text = "${comment.minute}' ${comment.text}", modifier = Modifier
                        .fillMaxWidth()
                        .background(UIHelper.getColorByString(bckColor), RoundedCornerShape(2.dp))
                        .padding(8.dp, 2.dp),
                        color = textColor)

                }

            }
        }

        Button(onClick = { matchViewModel.nextAction() }) {
            Text(text = SportAlbumApplication.instance.applicationContext.getString(R.string.next))
        }


    }




}

@Composable
fun MatchScore(modifier: Modifier, matchModel: MatchModel, position: Enums.Possesso) {
    Column(modifier = modifier, horizontalAlignment = CenterHorizontally) {
        val teamName =  if (position == Enums.Possesso.HOME) matchModel.home else matchModel.away
        val score = if (position == Enums.Possesso.HOME) matchModel.homeScore else matchModel.awayScore
        Text(text = teamName, fontSize = 16.sp, color = YellowD)
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
            fontWeight = FontWeight.Bold,
            color = OrangeYellowD
        )

        LazyColumn(modifier = Modifier.height(70.dp)) {
            items(matchModel.marcatori.filter { it.possesso == position }) {
                Text(text = it.minute.toString() + "' " + UIHelper.minifiyName(it.text), fontSize = 10.sp)
            }

        }
    }
}

@Composable
fun PlayersInMatch(modifier: Modifier, viewModel: MatchViewModel, position: Enums.Possesso) {
    Column(modifier = modifier) {
        val coach = if (position == Enums.Possesso.HOME) viewModel.homeTeam.value?.coach else viewModel.awayTeam.value?.coach
        coach?.let {
            Text(text = SportAlbumApplication.instance.getString(R.string.coach) + " " + UIHelper.minifiyName(coach), fontSize = 10.sp,  maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        LazyColumn {
            val players = if (position == Enums.Possesso.HOME) viewModel.homeEleven.value else viewModel.awayEleven.value
            val bgColor = if (position == Enums.Possesso.HOME) viewModel.homeTeam.value?.color1 else viewModel.awayTeam.value?.color1
            items(players) {
                Text(text = UIHelper.minifiyName(it.name), fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier
                    .padding(2.dp)
                    .background(UIHelper.getColorByString(bgColor))
                    .fillMaxWidth(), color = UIHelper.getTeamTextColor(bgColor ?: ""))
                Spacer(modifier = Modifier.height(0.5.dp))
            }

        }
        Button(onClick = {
            viewModel.currentScreen.value = if (position == Enums.Possesso.HOME)  MatchViewModel.Screen.LINE_UP_HOME else MatchViewModel.Screen.LINE_UP_AWAY
        }, shape = RoundedCornerShape(4.dp)) {
            Text(text = SportAlbumApplication.instance.getString(R.string.change))
        }
    }


}


@Composable
fun LineUpSelection(viewModel: MatchViewModel, position: Enums.Possesso) {
    val eleven= if (position == Enums.Possesso.HOME ) viewModel.homeEleven.collectAsState() else viewModel.awayEleven.collectAsState()
    val bench= if (position == Enums.Possesso.HOME ) viewModel.homeBench.collectAsState() else viewModel.awayBench.collectAsState()
    val playerSelected = viewModel.playerSelected.collectAsState()
    val isRoleSelection = remember { mutableStateOf(false) }
    val isModuleSelection = remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(8.dp)) {
        val teamName = if (position == Enums.Possesso.HOME) viewModel.homeTeam.value?.name else viewModel.awayTeam.value?.name
        val module =  if (position == Enums.Possesso.HOME) viewModel.homeTeam.value?.module else viewModel.awayTeam.value?.module
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = teamName ?: "" , modifier = Modifier
                .weight(0.5f)
                .padding(start = 8.dp),
                fontSize = 16.sp,
                color = OrangeYellowD)

            Card(colors = CardDefaults.cardColors(
                containerColor = OrangeYellowD,
            ), shape = RoundedCornerShape(20.dp)) {
                Text(text = SportAlbumApplication.instance.getString(module?.text ?: R.string.module),
                    modifier = Modifier
                        .padding(8.dp, 2.dp)
                        .clickable { isModuleSelection.value = true })
            }

            Spacer(modifier = Modifier.width(8.dp))
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
            .weight(1.2f)
            .padding(8.dp)) {
            items(eleven.value) {
                PlayerLineUpItem(it, if (it.name.equals(playerSelected.value?.name)) Green else BlueD,
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
                .weight(0.8f)
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(bench.value) {
                PlayerLineUpItem(
                    it, if (it.name.equals(playerSelected.value?.name)) Green else LightBlue,
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

        Button(onClick = {viewModel.nextScreen()},
            colors = ButtonDefaults.buttonColors(containerColor = OrangeYellowD, contentColor = Black),
            modifier = Modifier
                .padding(2.dp)
                .height(35.dp)
                .align(End)) {
            Text(text = SportAlbumApplication.instance.getString(R.string.next))
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
        Text(text = player.name, color = White, modifier = Modifier.weight(1f))
        Text(text = player.valueleg.toString(),
            modifier = Modifier
                .padding(start = 4.dp),
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
