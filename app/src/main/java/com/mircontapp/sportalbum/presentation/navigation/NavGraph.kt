package com.mircontapp.sportalbum.presentation.navigation

import android.content.Context
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mircontapp.sportalbum.presentation.album.AlbumScreen
import com.mircontapp.sportalbum.presentation.album.TeamAlbumScreen
import com.mircontapp.sportalbum.presentation.dashboard.DashboardScreen
import com.mircontapp.sportalbum.presentation.dashboard.EditPlayerScreen
import com.mircontapp.sportalbum.presentation.dashboard.EditTeamScreen
import com.mircontapp.sportalbum.presentation.match.LineUpScreen
import com.mircontapp.sportalbum.presentation.match.MatchScreen
import com.mircontapp.sportalbum.presentation.viewmodels.MainViewModel


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
            MatchScreen(navController = navController, mainViewModel)
        }
        composable(route = NavigationItem.LineUps.route){
            LineUpScreen(navController = navController, mainViewModel)
        }
        composable(route = NavigationItem.TeamAlbum.route){
            TeamAlbumScreen(navController = navController, mainViewModel)
        }
        composable(route = NavigationItem.EditTeam.route){
            EditTeamScreen(navController = navController, mainViewModel)
        }
        composable(route = NavigationItem.EditPlayer.route){
            EditPlayerScreen(navController = navController, mainViewModel)
        }
        composable(route = NavigationItem.Match.route){
            MatchScreen(navController = navController, mainViewModel)
        }
    }
}
