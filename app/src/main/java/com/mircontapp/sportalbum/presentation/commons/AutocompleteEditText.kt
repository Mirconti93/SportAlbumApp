package com.mircontapp.sportalbum.presentation.commons

import android.widget.AutoCompleteTextView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.window.PopupProperties
import com.mircontapp.sportalbum.domain.models.TeamModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoCompleteEditText(value: TextFieldValue,
                    onValueChange: (TextFieldValue) -> Unit, onOptionSelected: (String) -> Unit, suggestions: List<String>) {

    Column {
        CustomTextField(
            value = value,
            onValueChange = {onValueChange(value)},
        )
        DropdownMenu(
            expanded = suggestions.isNotEmpty(),
            onDismissRequest = {  },
            modifier = Modifier.fillMaxWidth(),
            properties = PopupProperties(focusable = false)
        ) {
            suggestions.forEach { label ->
                DropdownMenuItem(
                    text = { Text(text = label) },
                    onClick = {
                    onOptionSelected(label)
                })
            }
        }
    }

}