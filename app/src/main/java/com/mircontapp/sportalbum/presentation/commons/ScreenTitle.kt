package com.mircontapp.sportalbum.presentation.commons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScreenTitle(text: String) {
    Text(text =text, modifier = Modifier.padding(8.dp).fillMaxWidth(), textAlign = TextAlign.Center, fontSize = 14.sp, fontWeight = FontWeight.Bold)
}