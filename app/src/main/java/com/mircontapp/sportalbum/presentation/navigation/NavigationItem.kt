package com.mircontapp.sportalbum.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(var route: String, var icon: ImageVector, var title: String) {
    object Album : NavigationItem("album{args}", Icons.Default.Home, "Album")
    object TeamAlbum : NavigationItem("team_album\\${NavConsants.TEAM}", Icons.Default.Home, "Album")
    object Sticker : NavigationItem("sticker", Icons.Default.Home, "Album")
    object Dashboard : NavigationItem("dashboard", Icons.Default.Create, "Dashboard")
    object Games : NavigationItem("games", Icons.Default.Favorite, "Games")
    object EditTeam : NavigationItem("edit_team", Icons.Default.Create, "Edit team")
    object EditPlayer : NavigationItem("edit_player", Icons.Default.Create, "Edit players")
    object LineUps : NavigationItem("line_up", Icons.Default.Favorite, "Line Up")
    object Match : NavigationItem("match", Icons.Default.Favorite, "Match")
    object Bupi : NavigationItem("bupi",Icons.Default.Face, "Bupi")
    object BupiPlayerEdit : NavigationItem("bupi_player_edit", Icons.Default.Face, "BupiPlayerEdit")
    object BupiTeamEdit : NavigationItem("bupi_team_edit", Icons.Default.Face, "BupiTeamEdit")
    object Draw : NavigationItem("draw", Icons.Default.List, "Draw")

}

