package com.mircontapp.sportalbum.presentation.album

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.commons.ext.getDrawableId
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.presentation.commons.OnTeamClickHandler
import java.lang.Exception

@Composable
fun TeamsGrid(teamsState: TeamsState, modifier: Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        teamsState.teams.forEach {
            item {
                TeamChoiceItem(team = it, modifier = Modifier.padding(4.dp).shadow(2.dp).clickable {
                    teamsState.onTeamClickHandler.onTeamClick(it)
                })
            }
        }

    }
}

@Composable
fun TeamChoiceItem(team: TeamModel, modifier: Modifier) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        shape = RoundedCornerShape(4.dp),

    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            val idDrawable = team.name.getDrawableId(R.drawable.empty_logo)
            Image(
                painter = painterResource(idDrawable),
                contentDescription = "Team icon", // Descrizione opzionale per l'accessibilità
                modifier = Modifier.size(75.dp).padding(8.dp),
                contentScale = ContentScale.FillHeight
            )
            Text(modifier = Modifier, text = team.name, fontSize = 14.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }

    }

}

@Stable
data class TeamsState(val teams: List<TeamModel>, val onTeamClickHandler: OnTeamClickHandler)