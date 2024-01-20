package com.mircontapp.sportalbum.presentation.match

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LineUpScreen() {
    val viewModel: MatchViewModel = hiltViewModel()
    Text(text = viewModel.homeTeam.value?.name ?: "")
    Text(text = viewModel.awayTeam.value?.name ?: "")
}
