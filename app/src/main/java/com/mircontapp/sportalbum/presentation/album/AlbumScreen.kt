package com.mircontapp.sportalbum.presentation.album

import android.content.Context
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontVariation.width
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.commons.UIHelper
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.presentation.commons.OnTeamClickHandler
import com.mircontapp.sportalbum.presentation.navigation.NavigationItem
import com.mircontapp.sportalbum.presentation.viewmodels.MainViewModel

@Composable
fun AlbumScreen(navController: NavController, mainViewModel: MainViewModel) {
    val viewModel: AlbumViewModel = hiltViewModel()
    LaunchedEffect((Unit), block = {
        viewModel.getTeamsFromArea(Enums.Area.SERIEA)
    })

    val teams = viewModel.teams.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Text(text = SportAlbumApplication.instance.getString(R.string.teams))
        Row(
            Modifier
                .padding(2.dp)
                .horizontalScroll(rememberScrollState())) {
            Enums.Area.values().forEach { 
                Button(onClick = { 
                    viewModel.getTeamsFromArea(it)
                }) {
                    Text(text = it.text)
                }

                Spacer(modifier = Modifier.width(4.dp))
            }
        }
        if (teams.value != null) {
            TeamsGrid(TeamsState(teams.value, object : OnTeamClickHandler {
                override fun onTeamClick(teamModel: TeamModel) {
                    mainViewModel.teamModel = teamModel
                    navController.navigate(NavigationItem.TeamAlbum.route)
                }
            }), Modifier.fillMaxHeight().padding(4.dp))


        } else {
            Text(text = SportAlbumApplication.instance.getString(R.string.noTeams))
        }

    }

}

