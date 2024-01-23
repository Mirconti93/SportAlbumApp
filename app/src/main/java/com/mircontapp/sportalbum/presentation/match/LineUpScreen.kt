package com.mircontapp.sportalbum.presentation.match

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mircontapp.sportalbum.presentation.viewmodels.MainViewModel

@Composable
fun LineUpScreen(navController: NavController, mainViewModel: MainViewModel) {
    val viewModel: MatchViewModel = hiltViewModel()
    Text(text = viewModel.homeTeam.value?.name ?: "")
    Text(text = viewModel.awayTeam.value?.name ?: "")
}
