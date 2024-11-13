package com.mircontapp.sportalbum.presentation.album

import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.commons.UIHelper
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.toPlayerMatchModel
import com.mircontapp.sportalbum.presentation.ui.theme.BlueL
import com.mircontapp.sportalbum.presentation.ui.theme.OrangeYellowD
import com.mircontapp.sportalbum.presentation.ui.theme.YellowD
import com.mircontapp.sportalbum.presentation.viewmodels.MainViewModel

@Composable
fun StickerScreen(navController: NavController, playerArg: String) {
    val playerModel = Gson().fromJson<PlayerModel>(playerArg, PlayerModel::class.java)
    playerModel?.toPlayerMatchModel(true)?.let { player->
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            val idDrawable = UIHelper.getDrawableId(player.name, R.drawable.no_photo_icon)
            Text(modifier = Modifier, text = player.name, maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 20.sp)
            Image(
                painter = painterResource(idDrawable),
                contentDescription = "Team icon", // Descrizione opzionale per l'accessibilità
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp, 8.dp),
                contentScale = ContentScale.FillWidth
            )
            Row(modifier = Modifier.padding(32.dp, 2.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(modifier = Modifier.weight(1f), text = player.teamLegend ?: "", color = Color.White, fontSize = 16.sp, maxLines = 1)
                Text(modifier = Modifier.padding(8.dp, 2.dp), text = SportAlbumApplication.instance.getString(player.role.code), color = OrangeYellowD)
            }
            Row(modifier = Modifier.padding(32.dp, 2.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(modifier = Modifier.weight(1f), text = player.country ?: "", color = YellowD)
                ValueCard(label = null, value = player.valueleg?.toDouble() ?: 50.0, true)
            }
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                ValueCard(label = SportAlbumApplication.instance.getString(R.string.att), value = player.att, false)
                ValueCard(label = SportAlbumApplication.instance.getString(R.string.dif), value = player.dif, false)
                ValueCard(label = SportAlbumApplication.instance.getString(R.string.tec), value = player.tec, false)
                ValueCard(label = SportAlbumApplication.instance.getString(R.string.dri), value = player.dri, false)
                ValueCard(label = SportAlbumApplication.instance.getString(R.string.fin), value = player.fin, false)
            }
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                ValueCard(label = SportAlbumApplication.instance.getString(R.string.bal), value = player.bal, false)
                ValueCard(label = SportAlbumApplication.instance.getString(R.string.fis), value = player.fis, false)
                ValueCard(label = SportAlbumApplication.instance.getString(R.string.vel), value = player.vel, false)
                ValueCard(label = SportAlbumApplication.instance.getString(R.string.rig), value = player.rig, false)
                ValueCard(label = SportAlbumApplication.instance.getString(R.string.por), value = player.por, false)
            }
        }
    }

}

@Composable
fun ValueCard(label: String?, value: Double, isEditable: Boolean) {
    val isEditing = remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(4.dp)
            .clickable { if (isEditable) isEditing.value = !isEditing.value },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp, 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            label?.let { Text(text = label, color = Color.White, maxLines = 1)}
            if (isEditing.value) {
                BasicTextField(
                    value = value.toString(),
                    onValueChange = { newValue -> },
                    textStyle = TextStyle(fontSize = 14.sp, color = Color.White, textAlign = TextAlign.Center),
                    modifier = Modifier
                        .background(color = BlueL, shape = RoundedCornerShape(4.dp))
                        .size(32.dp)

                )
                IconButton(onClick = { isEditing.value = false }, modifier = Modifier.size(16.dp)) {
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                }
            } else {
                Text(text = value.toInt().toString(), color = OrangeYellowD, fontSize = 16.sp)
            }
        }
    }

}
