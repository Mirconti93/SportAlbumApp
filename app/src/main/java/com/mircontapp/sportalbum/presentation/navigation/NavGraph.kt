package com.mircontapp.sportalbum.presentation.navigation

import android.content.Context
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mircontapp.sportalbum.presentation.album.AlbumScreen
import com.mircontapp.sportalbum.presentation.dashboard.DashboardScreen
import com.mircontapp.sportalbum.presentation.dashboard.EditTeamScreen
import com.mircontapp.sportalbum.presentation.match.LineUpScreen
import com.mircontapp.sportalbum.presentation.match.MatchScreen


@Composable
fun NavGraph(navController: NavHostController){

    NavHost(
        navController = navController,
        startDestination = NavigationItem.Album.route)
    {
        composable(route = NavigationItem.Album.route){
            AlbumScreen()
        }
        composable(route = NavigationItem.Dashboard.route){
            DashboardScreen()
        }
        composable(route = NavigationItem.Games.route){
            MatchScreen()
        }

        composable(route = NavigationItem.LineUps.route){
            LineUpScreen()
        }

        composable(route = NavigationItem.EditTeam.route){
            EditTeamScreen()
        }
    }
}
