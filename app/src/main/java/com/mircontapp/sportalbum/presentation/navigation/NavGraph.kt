package com.mircontapp.sportalbum.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mircontapp.sportalbum.presentation.album.AlbumScreen
import com.mircontapp.sportalbum.presentation.album.StickerScreen
import com.mircontapp.sportalbum.presentation.album.TeamAlbumScreen
import com.mircontapp.sportalbum.presentation.dashboard.DashboardScreen
import com.mircontapp.sportalbum.presentation.dashboard.EditPlayerScreen
import com.mircontapp.sportalbum.presentation.dashboard.EditTeamScreen
import com.mircontapp.sportalbum.presentation.match.MatchGameScreen
import com.mircontapp.sportalbum.presentation.match.MatchStartScreen
import com.mircontapp.sportalbum.presentation.viewmodels.MainViewModel

@ExperimentalMaterial3Api
@Composable
fun NavGraph(navController: NavHostController, mainViewModel: MainViewModel){
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Album.route)
    {
        composable(route = NavigationItem.Album.route){
            AlbumScreen(navController = navController, mainViewModel)
        }
        composable(route = NavigationItem.Dashboard.route){
            DashboardScreen(navController = navController, mainViewModel)
        }
        composable(route = NavigationItem.Games.route){
            MatchStartScreen(navController = navController, mainViewModel)
        }
        composable(route = NavigationItem.LineUps.route){
            MatchGameScreen(navController = navController, mainViewModel)
        }
        composable(route = NavigationItem.TeamAlbum.route){
            TeamAlbumScreen(navController = navController, mainViewModel)
        }
        composable(route = NavigationItem.Sticker.route){
            StickerScreen(navController = navController, mainViewModel)
        }
        composable(route = NavigationItem.EditTeam.route){
            EditTeamScreen(navController = navController, mainViewModel)
        }
        composable(route = NavigationItem.EditPlayer.route){
            EditPlayerScreen(navController = navController, mainViewModel)
        }
        composable(route = NavigationItem.Match.route){
            MatchStartScreen(navController = navController, mainViewModel)
        }
    }
}
