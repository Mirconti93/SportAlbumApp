package com.mircontapp.sportalbum.presentation.commons

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomCircularProgress(modifier: Modifier) {
    CircularProgressIndicator(
        color = Color.White,
        strokeWidth = 2.dp,
        modifier = modifier)
}