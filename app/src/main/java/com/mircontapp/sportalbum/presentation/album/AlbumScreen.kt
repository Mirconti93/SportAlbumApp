package com.mircontapp.sportalbum.presentation.album

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication

@Composable
fun AlbumScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = SportAlbumApplication.instance.getStringById(R.string.teams))
        LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 80.dp)) {

            items(20) {
                TeamChoiceItem(name = it.toString(), modifier = Modifier.padding(8.dp))
            }
        }
    }

}