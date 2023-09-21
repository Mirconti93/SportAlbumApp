package com.mircontapp.sportalbum.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mircontapp.sportalbum.presentation.album.AlbumScreen


@Composable
fun NavGraph (navController: NavHostController){

    NavHost(
        navController = navController,
        startDestination = NavigationItem.Album.route)
    {
        composable(route = NavigationItem.Album.route){
            AlbumScreen()
        }
        composable(route = NavigationItem.Dashboard.route){
            Text(text = "DashBoard")
        }
        composable(route = NavigationItem.Games.route){
            Text(text = "Games")
        }
    }
}
