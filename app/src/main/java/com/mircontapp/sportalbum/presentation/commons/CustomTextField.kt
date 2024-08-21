package com.mircontapp.sportalbum.presentation.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mircontapp.sportalbum.commons.UIHelper
import com.mircontapp.sportalbum.commons.customTextEdit
import com.mircontapp.sportalbum.presentation.ui.theme.BlueD
import com.mircontapp.sportalbum.presentation.ui.theme.DarkBlueD

@Composable
fun CustomTextField(value: TextFieldValue,
                    onValueChange: (TextFieldValue) -> Unit, imageVector: ImageVector?) {

    Box(modifier = Modifier.padding(8.dp, 4.dp)) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
            cursorBrush = SolidColor(Color.White),
            decorationBox = { innerTextField ->
                // Decorate the inner text field with a rounded background and padding
                Layout(
                    content = {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))  // Angoli arrotondati
                                .background(BlueD)  // Sfondo
                                .padding(16.dp)  // Padding interno
                                .fillMaxWidth(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            imageVector?.let {
                                Icon(
                                    imageVector = imageVector,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .width(20.dp)
                                        .height(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            if (value.text.isEmpty()) {
                                Text(
                                    text = "Enter text",
                                    style = TextStyle(color = Color.Gray, fontSize = 16.sp)
                                )
                            }
                            innerTextField()  // Inserisci il campo di testo
                        }
                    },
                    measurePolicy = { measurables, constraints ->
                        val placeable = measurables.first().measure(constraints)
                        layout(placeable.width, placeable.height) {
                            placeable.placeRelative(0, 0)
                        }
                    }
                )
            }
        )
    }


}

@Composable
fun CustomTextField(value: TextFieldValue,
                    onValueChange: (TextFieldValue) -> Unit) {
    CustomTextField(value = value, onValueChange = onValueChange, imageVector = null)
}

@Preview(showBackground = true)
@Composable
fun CustomTextFieldPreview() {
    CustomTextField(TextFieldValue("Ibra"), {})
}
