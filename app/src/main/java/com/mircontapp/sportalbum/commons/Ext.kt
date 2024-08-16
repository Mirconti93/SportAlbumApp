package com.mircontapp.sportalbum.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.mircontapp.sportalbum.presentation.ui.theme.DarkBlueD
import java.time.format.TextStyle


fun Modifier.customTextEdit() : Modifier {
    return this.fillMaxWidth().height(80.dp)
        .padding(10.dp)
        .background(DarkBlueD, shape = RoundedCornerShape(15.dp))
}