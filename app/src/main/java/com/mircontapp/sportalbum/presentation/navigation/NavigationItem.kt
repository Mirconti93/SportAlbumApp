package com.mircontapp.sportalbum.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(var route: String, var icon: ImageVector, var title: String) {
    object Album : NavigationItem("album", Icons.Default.Home, "Album")
    object Dashboard : NavigationItem("dashboard", Icons.Default.List, "Dashboard")
    object Games : NavigationItem("games", Icons.Default.Favorite, "Games")

}
