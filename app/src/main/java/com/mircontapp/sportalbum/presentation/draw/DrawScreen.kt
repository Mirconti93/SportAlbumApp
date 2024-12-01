package com.mircontapp.sportalbum.presentation.draw

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mircontapp.sportalbum.presentation.commons.AutoCompleteEditText

@Composable
fun DrawScreen(navController: NavController) {
    val drawViewModel: DrawViewModel = hiltViewModel()

    val name = remember { mutableStateOf(TextFieldValue("")) }

    val options = remember { drawViewModel.options.value }

    AutoCompleteEditText(
        value = name.value,
        onValueChange = { name.value = it },
        onOptionSelected = {},
        suggestions = options
    )
}