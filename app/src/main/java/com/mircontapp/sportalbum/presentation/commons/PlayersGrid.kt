package com.mircontapp.sportalbum.presentation.album

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.commons.ext.getDrawableId
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.presentation.commons.OnEditClickHandler
import com.mircontapp.sportalbum.presentation.commons.OnPlayerClickHandler
import com.mircontapp.sportalbum.presentation.commons.OnTeamClickHandler
import com.mircontapp.sportalbum.presentation.ui.theme.OrangeYellowD
import java.lang.Exception

@Composable
fun PlayersGrid(playersState: PlayersState) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        playersState.players.forEach {
            item {
                PlayerItem(it, onEditClickHandler = playersState.onEditClickHandler, modifier = Modifier
                    .padding(8.dp)
                    .shadow(2.dp)
                    .clickable {
                        playersState.onPlayerClickHandler.onPlayerClick(it)
                    })
            }
        }

    }
}

@Composable
fun PlayerItem(player: PlayerModel, onEditClickHandler: OnEditClickHandler, modifier: Modifier) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        shape = RoundedCornerShape(4.dp)

    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            val idDrawable = player.name.getDrawableId(R.drawable.no_photo_icon)
            Image(
                painter = painterResource(idDrawable),
                contentDescription = "Team icon", // Descrizione opzionale per l'accessibilità
                modifier = Modifier.size(140.dp, 200.dp),
                contentScale = ContentScale.FillHeight
            )
            Text(modifier = Modifier, text = player.name, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Row(modifier = Modifier.padding(8.dp, 2.dp).clickable { onEditClickHandler.onPlayerClick(player) }) {
                Text(modifier = Modifier.weight(1f), text = SportAlbumApplication.instance.getString(player.role.code), color = OrangeYellowD)
                Text(modifier = Modifier, text = player.valueleg.toString(), color = OrangeYellowD)
            }
        }

    }

}

@Stable
data class PlayersState(val players: List<PlayerModel>, val onPlayerClickHandler: OnPlayerClickHandler, val onEditClickHandler: OnEditClickHandler)