package com.mircontapp.sportalbum.presentation.album

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

@Composable
fun AlbumScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val viewModel: AlbumViewModel = hiltViewModel()
        Text(text = SportAlbumApplication.instance.getString(R.string.teams))

        if (viewModel.teams.value != null) {
            TeamsGrid(teams = viewModel.teams.value!!)
        } else {
            Text(text = SportAlbumApplication.instance.getString(R.string.noTeams))
        }
    }

}

@Composable
fun TeamsGrid(teams: List<TeamModel>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {


        items(teams.size) { i ->
            if (teams.get(i) != null)
                TeamChoiceItem(name = teams.get(i).name, modifier = Modifier.padding(8.dp))
        }
    }
}