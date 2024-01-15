package com.mircontapp.sportalbum.presentation.album

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.presentation.commons.OnTeamClickHandler
import java.lang.Exception

@Composable
fun TeamsGrid(teams: List<TeamModel>, onTeamClickHandler: OnTeamClickHandler) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        teams.forEach {
            item {
                TeamChoiceItem(name = it.name, modifier = Modifier.padding(8.dp))
            }
        }

    }
}

@Composable
fun TeamChoiceItem(name: String, modifier: Modifier) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            val context = SportAlbumApplication.instance.applicationContext
            val idDrawable = try {
                context.resources.getIdentifier(name, "drawable", context.getPackageName());
            } catch (e: Exception) {
                R.drawable.ic_launcher_foreground
            }
            Image(
                painter = painterResource(id = R.drawable.empty_logo),
                contentDescription = null, // Descrizione opzionale per l'accessibilità
                modifier = Modifier
                    .size(40.dp).shadow(2.dp)
            )
            Text(text = name)
        }

    }

}