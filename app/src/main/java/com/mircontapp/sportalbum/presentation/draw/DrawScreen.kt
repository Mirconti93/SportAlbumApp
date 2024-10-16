package com.mircontapp.sportalbum.presentation.draw

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mircontapp.sportalbum.presentation.commons.AutoCompleteEditText
import com.mircontapp.sportalbum.presentation.dashboard.DashboardViewModel
import com.mircontapp.sportalbum.presentation.viewmodels.MainViewModel

@Composable
fun DrawScreen(navController: NavController, mainViewModel: MainViewModel) {
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