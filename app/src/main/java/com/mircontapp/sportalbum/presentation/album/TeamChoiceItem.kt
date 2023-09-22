package com.mircontapp.sportalbum.presentation.album

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp

@Composable
fun TeamChoiceItem(name: String, modifier: Modifier) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column {
            Icon(bitmap = ImageBitmap(40, 40), contentDescription = null)
            Text(text = name)
        }

    }

}