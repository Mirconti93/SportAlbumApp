package com.mircontapp.sportalbum.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.mircontapp.sportalbum.presentation.album.AlbumScreen
import com.mircontapp.sportalbum.presentation.album.StickerScreen
import com.mircontapp.sportalbum.presentation.album.TeamAlbumScreen
import com.mircontapp.sportalbum.presentation.bupi.BupiScreen
import com.mircontapp.sportalbum.presentation.bupi.EditBupiPlayerScreen
import com.mircontapp.sportalbum.presentation.bupi.EditBupiTeamScreen
import com.mircontapp.sportalbum.presentation.dashboard.DashboardScreen
import com.mircontapp.sportalbum.presentation.dashboard.EditPlayerScreen
import com.mircontapp.sportalbum.presentation.dashboard.EditTeamScreen
import com.mircontapp.sportalbum.presentation.draw.DrawScreen
import com.mircontapp.sportalbum.presentation.match.MatchGameScreen
import com.mircontapp.sportalbum.presentation.match.MatchStartScreen
import com.mircontapp.sportalbum.presentation.viewmodels.MainViewModel
import kotlinx.serialization.Serializable

@ExperimentalMaterial3Api
@Composable
fun NavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Album.route)
    {
        composable<AlbumRoute> {
            AlbumScreen(navController = navController)
        }
        composable<DashboardRoute> {
            DashboardScreen(navController = navController)
        }
        composable<MatchStartRoute> {
            MatchStartScreen(navController = navController)
        }
        composable<MatchGameRoute> {
            MatchGameScreen(navController = navController,
                homeTeam = it.toRoute<MatchGameRoute>().homeTeam,
                awayTeam = it.toRoute<MatchGameRoute>().awayTeam)
        }
        composable<TeamAlbumRoute>{
            TeamAlbumScreen(navController = navController,
                teamName = it.toRoute<TeamAlbumRoute>().team)
        }
        composable<StickerRoute>{
            StickerScreen(navController = navController)
        }
        composable<EditTeamRoute>{
            EditTeamScreen(navController = navController)
        }
        composable<EditPlayerRoute>{
            EditPlayerScreen(navController = navController)
        }
        composable<BupiRoute>{
            BupiScreen(navController = navController)
        }
        composable<EditPlayerRoute>{
            EditBupiPlayerScreen(navController = navController)
        }
        composable<EditBupiTeamRoute>{
            EditBupiTeamScreen(navController = navController)
        }
        composable<DrawRoute>{
            DrawScreen(navController = navController)
        }
    }
}

@Serializable object AlbumRoute

@Serializable object DashboardRoute

@Serializable object MatchStartRoute

@Serializable data class MatchGameRoute(val homeTeam: String, val awayTeam: String)

@Serializable data class TeamAlbumRoute(val team: String)

@Serializable object StickerRoute

@Serializable object EditTeamRoute

@Serializable object EditPlayerRoute

@Serializable object BupiRoute

@Serializable object EditBupiPlayerRoute

@Serializable object EditBupiTeamRoute

@Serializable object DrawRoute



