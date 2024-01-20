package com.mircontapp.sportalbum.presentation.album

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.commons.UIHelper
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.presentation.commons.OnTeamClickHandler
import java.lang.Exception

@Composable
fun TeamsGrid(teamsState: TeamsState) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        teamsState.teams.forEach {
            item {
                TeamChoiceItem(name = it.name, modifier = Modifier.padding(8.dp))
            }
        }

    }
}

@Composable
fun TeamChoiceItem(name: String, modifier: Modifier) {
    Card(
        modifier = modifier.shadow(2.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        shape = MaterialTheme.shapes.large,

    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            val idDrawable = UIHelper.getDrawableId(name, R.drawable.inter)
            Image(
                painter = painterResource(idDrawable),
                contentDescription = "Team icon", // Descrizione opzionale per l'accessibilità
                modifier = Modifier.size(40.dp),
                contentScale = ContentScale.Crop
            )
            Text(modifier = Modifier, text = name)
        }

    }

}

@Stable
data class TeamsState(val teams: List<TeamModel>, val onTeamClickHandler: OnTeamClickHandler)