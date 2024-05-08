package com.mircontapp.sportalbum.presentation.album

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.commons.UIHelper
import com.mircontapp.sportalbum.presentation.ui.theme.OrangeYellowD
import com.mircontapp.sportalbum.presentation.viewmodels.MainViewModel

@Composable
fun StickerScreen(navController: NavController, mainViewModel: MainViewModel) {
    mainViewModel.playerModel?.let {player->
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            val idDrawable = UIHelper.getDrawableId(player.name, R.drawable.no_photo_icon)
            Image(
                painter = painterResource(idDrawable),
                contentDescription = "Team icon", // Descrizione opzionale per l'accessibilità
                modifier = Modifier.fillMaxWidth().padding(16.dp, 8.dp),
                contentScale = ContentScale.FillWidth
            )
            Text(modifier = Modifier, text = player.name, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Row(modifier = Modifier.padding(8.dp, 2.dp)) {
                Text(modifier = Modifier.weight(1f), text = SportAlbumApplication.instance.getString(player.role.code), color = OrangeYellowD)
                Text(modifier = Modifier, text = player.valueleg.toString(), color = OrangeYellowD)
            }
        }
    }

}