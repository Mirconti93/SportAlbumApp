package com.mircontapp.sportalbum.presentation.album

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.commons.UIHelper
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.presentation.commons.OnEditClickHandler
import com.mircontapp.sportalbum.presentation.commons.OnPlayerClickHandler
import com.mircontapp.sportalbum.presentation.commons.OnTeamClickHandler
import com.mircontapp.sportalbum.presentation.navigation.NavigationItem
import com.mircontapp.sportalbum.presentation.ui.theme.OrangeYellowD
import com.mircontapp.sportalbum.presentation.viewmodels.MainViewModel

@Composable
fun TeamAlbumScreen(navController: NavController, mainViewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val team = remember{ mainViewModel.teamModel }
        val viewModel: TeamAlbumViewModel = hiltViewModel()
        remember {
            viewModel.playersFromTeamLegend(team)
        }
        viewModel.players.value

        team?.let {team->
            Card(
                modifier = Modifier,
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                shape = RoundedCornerShape(4.dp)

            ) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(modifier = Modifier, text = team.name, fontSize = 20.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)

                    val idDrawable = UIHelper.getDrawableId(team.name, R.drawable.empty_logo)
                    Row(modifier = Modifier
                        .padding(8.dp, 2.dp)
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
                    Spacer(modifier = Modifier.height(1.dp).background(color = UIHelper.getColorByString(team.color1)).fillMaxWidth())
                    Spacer(modifier = Modifier.height(1.dp).background(color = UIHelper.getColorByString(team.color2)).fillMaxWidth())
                }
            }
        }

        viewModel.players.value.let {players ->
            PlayersGrid(
                PlayersState(
                    players.sortedBy { it.roleLineUp },
                    object : OnPlayerClickHandler {
                        override fun onPlayerClick(playerModel: PlayerModel) {
                            mainViewModel.playerModel = playerModel
                            navController.navigate(NavigationItem.Sticker.route)
                        }
                    },
                    object : OnEditClickHandler {
                        override fun onPlayerClick(playerModel: PlayerModel) {
                            mainViewModel.playerModel = playerModel
                            navController.navigate(NavigationItem.EditPlayer.route)
                        }
                    },
                )
            )
        }

    }

}

@Composable
fun DescriptionText(label: String, value: String?) {
    Row {
        value?.let { text ->
            Text(modifier = Modifier, text = label + ": ", color = Color.White)
            Text(modifier = Modifier, text = text, color = OrangeYellowD)
        }
    }

}

