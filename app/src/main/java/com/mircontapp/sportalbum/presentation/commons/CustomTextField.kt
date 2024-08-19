package com.mircontapp.sportalbum.presentation.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mircontapp.sportalbum.commons.UIHelper
import com.mircontapp.sportalbum.commons.customTextEdit
import com.mircontapp.sportalbum.presentation.ui.theme.DarkBlueD

@Composable
fun CustomTextField(value: TextFieldValue,
                    onValueChange: (TextFieldValue) -> Unit, imageVector: ImageVector?) {
    Card(
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
    ) {
        Row(
            Modifier
                .height(IntrinsicSize.Min)
        ) {
            imageVector?.let {
                Icon(
                    imageVector = imageVector,
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp)
                )
            }
            BasicTextField(
                value = value,
                textStyle = TextStyle(fontSize = 16.sp, color = Color.White),
                onValueChange = onValueChange,
                modifier = Modifier
                    .weight(1f)
                    .height(58.dp)
                    .padding(10.dp)
                    .background(DarkBlueD, shape = RoundedCornerShape(15.dp))
            )

        }
    }
}

@Composable
fun CustomTextField(value: TextFieldValue,
                    onValueChange: (TextFieldValue) -> Unit) {
    CustomTextField(value = value, onValueChange = onValueChange, imageVector = null)
}
