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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import com.mircontapp.sportalbum.commons.UIHelper
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.presentation.commons.OnPlayerClickHandler
import com.mircontapp.sportalbum.presentation.commons.OnTeamClickHandler
import com.mircontapp.sportalbum.presentation.ui.theme.OrangeYellowD
import java.lang.Exception

@Composable
fun PlayersShortList(playersState: PlayersState) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(1.dp),
    ) {
        playersState.players.forEach {
            item {
                PlayerListItem(it, modifier = Modifier
                    .padding(2.dp)
                    .shadow(2.dp)
                    .clickable {
                        playersState.onPlayerClickHandler.onPlayerClick(it)
                    })
            }
        }

    }
}

@Composable
fun PlayerListItem(player: PlayerModel, modifier: Modifier) {
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
            Row(modifier = Modifier.padding(2.dp)) {
                Text(modifier = Modifier.weight(1f), text = player.name, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(modifier = Modifier, text = player.team.toString(), color = OrangeYellowD, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }

    }

}
