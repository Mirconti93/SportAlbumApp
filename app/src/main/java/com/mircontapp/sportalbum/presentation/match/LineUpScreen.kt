package com.mircontapp.sportalbum.presentation.match

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mircontapp.sportalbum.presentation.viewmodels.MainViewModel

@Composable
fun LineUpScreen(navController: NavController, mainViewModel: MainViewModel) {
    val viewModel: MatchViewModel = hiltViewModel()

    LaunchedEffect((Unit), block = {
        if (mainViewModel.homeTeam != null) {
            viewModel.getAwayPlayers(mainViewModel.homeTeam)
        }
        if (mainViewModel.awayTeam != null) {
            viewModel.getAwayPlayers(mainViewModel.awayTeam)
        }
    })

    Row {
        Column {
            Text(text = mainViewModel.homeTeam?.name ?: "")
            LazyColumn{

            }
        }

        Column {
            Text(text = mainViewModel.awayTeam?.name ?: "")
        }
    }


}
