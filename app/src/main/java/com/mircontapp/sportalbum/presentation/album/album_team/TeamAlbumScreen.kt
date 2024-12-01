package com.mircontapp.sportalbum.presentation.album.album_team

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.commons.ext.getColorByString
import com.mircontapp.sportalbum.commons.ext.getDrawableId
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.presentation.album.PlayersGrid
import com.mircontapp.sportalbum.presentation.album.album_choose.AlbumChooseAction
import com.mircontapp.sportalbum.presentation.commons.CustomCircularProgress
import com.mircontapp.sportalbum.presentation.commons.OnEditClickHandler
import com.mircontapp.sportalbum.presentation.commons.OnPlayerClickHandler
import com.mircontapp.sportalbum.presentation.navigation.Routes
import com.mircontapp.sportalbum.presentation.ui.theme.OrangeYellowD

@Composable
fun TeamAlbumScreen(navController: NavController, teamArg: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val team = Gson().fromJson(teamArg, TeamModel::class.java)
        val viewModel: TeamAlbumViewModel = hiltViewModel()

        LaunchedEffect((Unit), block = {
            viewModel.onAction(AlbumTeamAction.ShowPlayersByTeam(team))
        })

        team?.let { team->
            Card(
                modifier = Modifier,
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                shape = RoundedCornerShape(4.dp)

            ) {
                Column(modifier = Modifier
                    .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(modifier = Modifier, text = team.name, fontSize = 20.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)

                    val idDrawable = team.name.getDrawableId(R.drawable.empty_logo)
                    Row(modifier = Modifier
                        .padding(8.dp, 8.dp)
                        .fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                        Image(
                            painter = painterResource(idDrawable),
                            contentDescription = "Team icon", // Descrizione opzionale per l'accessibilità
                            modifier = Modifier.size(70.dp, 70.dp),
                            contentScale = ContentScale.FillHeight
                        )
                        
                        Spacer(modifier = Modifier.width(20.dp))
                        Column {
                            DescriptionText(label = SportAlbumApplication.instance.getString(R.string.city), value = team.city)
                            DescriptionText(label = SportAlbumApplication.instance.getString(R.string.stadium), value = team.stadium)
                            DescriptionText(label = SportAlbumApplication.instance.getString(R.string.coach), value = team.coachlegend)
                        }
                    }
                    Spacer(modifier = Modifier
                        .height(2.dp)
                        .background(color = team.color1.getColorByString())
                        .fillMaxWidth())
                    Spacer(modifier = Modifier
                        .height(4.dp)
                        .background(color = team.color2.getColorByString())
                        .fillMaxWidth())
                    Spacer(modifier = Modifier
                        .height(2.dp)
                        .background(color = team.color1.getColorByString())
                        .fillMaxWidth())
                }
            }
        }

        viewModel.state.collectAsState().value.let {
            when {
                it.isLoading-> CustomCircularProgress(modifier = Modifier.fillMaxWidth())
                it.message != null -> Text(text = it.message)
                it.players.isEmpty() -> {
                    PlayersGrid(
                        it.players,
                        object : OnPlayerClickHandler {
                            override fun onPlayerClick(playerModel: PlayerModel) {
                                val arg = Gson().toJson(playerModel)
                                navController.navigate(Routes.Sticker(arg))
                            }
                        },
                        object : OnEditClickHandler {
                            override fun onPlayerClick(playerModel: PlayerModel) {
                                val arg = Gson().toJson(playerModel)
                                navController.navigate(Routes.EditPlayer(arg))
                            }
                        },
                    )
                }
                else -> Text(text = SportAlbumApplication.instance.getString(R.string.genericError))
            }

        }

    }

}

@Composable
fun DescriptionText(label: String, value: String?) {
    Row {
        value?.let { text ->
            Text(modifier = Modifier, text = "$label: ", color = Color.White)
            Text(modifier = Modifier, text = text, color = OrangeYellowD)
        }
    }

}

