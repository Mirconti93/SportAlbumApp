package com.mircontapp.sportalbum.presentation.match.match_game

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
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
import com.mircontapp.sportalbum.commons.ext.getColorByString
import com.mircontapp.sportalbum.commons.ext.getDrawableId
import com.mircontapp.sportalbum.commons.ext.getTeamTextColor
import com.mircontapp.sportalbum.commons.ext.minifiyName
import com.mircontapp.sportalbum.domain.models.MatchModel
import com.mircontapp.sportalbum.domain.models.PlayerMatchModel
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.presentation.commons.CustomCircularProgress
import com.mircontapp.sportalbum.presentation.commons.OnPlayerMatchClickHandler
import com.mircontapp.sportalbum.presentation.ui.theme.BlueD
import com.mircontapp.sportalbum.presentation.ui.theme.Green
import com.mircontapp.sportalbum.presentation.ui.theme.LightBlue
import com.mircontapp.sportalbum.presentation.ui.theme.OrangeYellowD
import com.mircontapp.sportalbum.presentation.ui.theme.YellowD

@Composable
fun MatchGameScreen(navController: NavController, homeTeamArg: String, awayTeamArg: String) {
    val viewModel: MatchViewModel = hiltViewModel()


    val homeTeam = Gson().fromJson(homeTeamArg, TeamModel::class.java)
    val awayTeam = Gson().fromJson(awayTeamArg, TeamModel::class.java)
    LaunchedEffect((Unit), block = {
        viewModel.initLineUp(homeTeam, awayTeam)
    })

    val state = viewModel.state.collectAsState()

    if (state.value.isLoading) CustomCircularProgress(modifier = Modifier.fillMaxWidth().fillMaxHeight())
    else when (state.value.screen) {
        Enums.GameScreen.LINE_UP -> LineUpSelection(viewModel = viewModel, position = state.value.teamPosition)
        else -> Match(viewModel)
    }

}

@Composable
fun Match(viewModel: MatchViewModel) {
    val state = viewModel.state.collectAsState()

    Column(modifier = Modifier.fillMaxHeight(), horizontalAlignment = CenterHorizontally) {
        Row(modifier = Modifier.padding(16.dp, 8.dp)) {
            MatchScore(modifier = Modifier.padding(4.dp, 2.dp).weight(1f), state.value.matchModel, viewModel.homeTeam, position = Enums.TeamPosition.HOME)
            Text(text = state.value.matchModel.minute.toString() + "'", modifier = Modifier.padding(8.dp, 20.dp))
            MatchScore(modifier = Modifier.weight(1f), state.value.matchModel, viewModel.awayTeam, position = Enums.TeamPosition.AWAY)
        }
        val matchModel =  state.value.matchModel
        Row(modifier = Modifier.padding(8.dp, 0.dp)) {

            PlayersInMatch(modifier = Modifier.weight(0.35f), state.value.matchModel.playersHome, viewModel.homeTeam) {
                viewModel.onAction(MatchGameAction.ShowChangeLineUp(Enums.TeamPosition.HOME))
            }
            Column(modifier = Modifier
                .weight(0.3f)
                .padding(2.dp), horizontalAlignment = CenterHorizontally) {
                Spacer(modifier = Modifier.height(8.dp))

                Image(
                    painter = painterResource(matchModel.protagonista.getDrawableId(R.drawable.giocatore)),
                    contentDescription = "Team icon",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.height(100.dp)
                )
                val idDrawable = matchModel.coprotagonista.getDrawableId(R.drawable.giocatore)
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

            PlayersInMatch(modifier = Modifier.weight(0.35f), state.value.matchModel.playersAway, viewModel.awayTeam) {
                viewModel.onAction(MatchGameAction.ShowChangeLineUp(Enums.TeamPosition.AWAY))
            }
        }

        Column(modifier = Modifier
            .weight(1f)
            .padding(8.dp)) {
            matchModel.comment.let {
                if (it.size>0) {
                    val comment = matchModel.comment[it.size-1]
                    val bckColor = if (comment.possesso == Enums.TeamPosition.HOME) viewModel.homeTeam.color1 else viewModel.awayTeam.color1
                    val textColor = bckColor.getTeamTextColor()
                    Text(text = "${comment.minute}' ${comment.text}", modifier = Modifier
                        .fillMaxWidth()
                        .background(bckColor.getColorByString(), RoundedCornerShape(2.dp))
                        .padding(8.dp, 2.dp),
                        color = textColor)
                }
                if (it.size>1) {
                    Spacer(modifier = Modifier.height(2.dp))
                    val comment = matchModel.comment[it.size-2]
                    val bckColor = if (comment.possesso == Enums.TeamPosition.HOME) viewModel.homeTeam.color1 else viewModel.awayTeam.color1
                    val textColor = bckColor.getTeamTextColor()
                    Text(text = "${comment.minute}' ${comment.text}", modifier = Modifier
                        .fillMaxWidth()
                        .background(bckColor.getColorByString(), RoundedCornerShape(2.dp))
                        .padding(8.dp, 2.dp),
                        color = textColor)

                }

            }
        }

        Button(onClick = { viewModel.nextAction() }) {
            Text(text = SportAlbumApplication.instance.applicationContext.getString(R.string.next))
        }


    }


}

@Composable
fun MatchScore(modifier: Modifier, matchModel: MatchModel, team: TeamModel, position: Enums.TeamPosition) {
    Column(modifier = modifier, horizontalAlignment = CenterHorizontally) {
        val teamName =  team.name
        val score = if (position == Enums.TeamPosition.HOME) matchModel.homeScore else matchModel.awayScore
        Text(text = teamName, fontSize = 16.sp, color = YellowD)
        Spacer(modifier = Modifier.height(8.dp))
        Image(
            painter = painterResource(
                teamName.getDrawableId(
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
                Text(text = it.minute.toString() + "' " + it.text.minifiyName(), fontSize = 10.sp)
            }

        }
    }
}

@Composable
fun PlayersInMatch(modifier: Modifier, players: List<PlayerMatchModel>, team: TeamModel, onClick: ()->Unit) {
    Column(modifier = modifier) {
        team.coach?.let {
            Text(text = SportAlbumApplication.instance.getString(R.string.coach) + " " + it.minifiyName(), fontSize = 10.sp,  maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        LazyColumn {
            val bgColor = team.color1
            items(players) {
                Text(text = it.name.minifiyName(), fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier
                    .padding(2.dp)
                    .background(bgColor.getColorByString())
                    .fillMaxWidth(), color = bgColor.getTeamTextColor())
                Spacer(modifier = Modifier.height(0.5.dp))
            }

        }
        Button(onClick = {

        }, shape = RoundedCornerShape(4.dp)) {
            Text(text = SportAlbumApplication.instance.getString(R.string.change))
        }
    }


}


@Composable
fun LineUpSelection(viewModel: MatchViewModel, position: Enums.TeamPosition) {
    val state = viewModel.state.collectAsState()

    val eleven= if (position == Enums.TeamPosition.HOME ) state.value.matchModel.playersHome else state.value.matchModel.playersAway
    val bench= if (position == Enums.TeamPosition.HOME ) state.value.homeBench else state.value.awayBench

    val playerSelected = viewModel.playerSelected.collectAsState()
    //todo use in view model with MVI
    val isRoleSelection = remember { mutableStateOf(false) }
    val isModuleSelection = remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(8.dp)) {
        val teamName = if (position == Enums.TeamPosition.HOME) viewModel.homeTeam.name else viewModel.awayTeam.name
        val coach = if (position == Enums.TeamPosition.HOME) viewModel.homeTeam.coach else viewModel.awayTeam.coach
        val module =  if (position == Enums.TeamPosition.HOME) viewModel.homeTeam.module else viewModel.awayTeam.module
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = teamName ?: "" , modifier = Modifier
                .weight(0.5f)
                .padding(start = 8.dp, end = 8.dp),
                fontSize = 16.sp,
                color = OrangeYellowD)

            Text(text = SportAlbumApplication.instance.getString(R.string.coach) + " " + coach?.minifiyName() , modifier = Modifier
                .weight(0.3f)
                .padding(start = 8.dp),
                fontSize = 12.sp,
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
                LazyRow {
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
            .weight(0.5f)
            .padding(8.dp)) {
            items(eleven) {
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
                .weight(0.47f)
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(bench) {
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

        val roleText = if (player.roleMatch.text == R.string.pan) SportAlbumApplication.getString(player.roleLineUp.text) else SportAlbumApplication.getString(player.roleMatch.text)
        Text(text = roleText,
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
