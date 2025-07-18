package com.mircontapp.sportalbum.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.google.gson.Gson
import com.mircontapp.sportalbum.presentation.album.sticker.StickerScreen
import com.mircontapp.sportalbum.presentation.album.album_team.TeamAlbumScreen
import com.mircontapp.sportalbum.presentation.bupi.BupiScreen
import com.mircontapp.sportalbum.presentation.bupi.EditBupiPlayerScreen
import com.mircontapp.sportalbum.presentation.bupi.EditBupiTeamScreen
import com.mircontapp.sportalbum.presentation.commons.team_select.TeamSelectView
import com.mircontapp.sportalbum.presentation.dashboard.dashboard_list.DashboardScreen
import com.mircontapp.sportalbum.presentation.dashboard.edit_player.EditPlayerScreen
import com.mircontapp.sportalbum.presentation.dashboard.edit_team.EditTeamScreen
import com.mircontapp.sportalbum.presentation.draw.DrawScreen
import com.mircontapp.sportalbum.presentation.match.match_game.MatchGameScreen
import com.mircontapp.sportalbum.presentation.match.match_start.MatchStartScreen

@ExperimentalMaterial3Api
@Composable
fun NavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Album.route)
    {
        composable<Routes.Album> {
            TeamSelectView(isLegend = true){ teamModel ->
                navController.navigate(Routes.TeamAlbum(Gson().toJson(teamModel)))
            }
        }
        composable<Routes.TeamAlbum>{
            TeamAlbumScreen(navController = navController,
                teamArg = it.toRoute<Routes.TeamAlbum>().team)
        }
        composable<Routes.Sticker>{
            StickerScreen(playerArg = it.toRoute<Routes.Sticker>().player)
        }
        composable<Routes.Dashboard> {
            DashboardScreen(navController = navController)
        }
        composable<Routes.EditTeam>{
            EditTeamScreen(navController = navController,
                teamArg = it.toRoute<Routes.EditTeam>().team)
        }
        composable<Routes.EditPlayer>{
            EditPlayerScreen(navController = navController,
                playerArg = it.toRoute<Routes.EditPlayer>().player)
        }
        composable<Routes.StartMatch> {
            MatchStartScreen(navController = navController)
        }
        composable<Routes.Match> {
            MatchGameScreen(navController = navController,
                homeTeamArg = it.toRoute<Routes.Match>().homeTeam,
                awayTeamArg = it.toRoute<Routes.Match>().awayTeam)
        }
        composable<Routes.Bupi>{
            BupiScreen(navController = navController)
        }
        composable<Routes.EditBupiPlayer>{
            EditBupiPlayerScreen(navController = navController,
                bupiPlayerArg = it.toRoute<Routes.EditBupiPlayer>().bupiPlayer)
        }
        composable<Routes.EditBupiTeam>{
            EditBupiTeamScreen(navController = navController,
                bupiTeamArg = it.toRoute<Routes.EditBupiTeam>().bupiTeam)
        }
        composable<Routes.Draw>{
            DrawScreen(navController = navController)
        }
    }
}



