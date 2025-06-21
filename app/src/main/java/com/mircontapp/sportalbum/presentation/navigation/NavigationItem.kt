package com.mircontapp.sportalbum.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication

sealed class NavigationItem<T>(var route: T, var icon: ImageVector, var title: String) {
    object Album : NavigationItem<Routes.Album>(Routes.Album, Icons.Default.Home, SportAlbumApplication.getString(R.string.album))
    object Dashboard : NavigationItem<Routes.Dashboard>(Routes.Dashboard, Icons.Default.Create, SportAlbumApplication.getString(R.string.dashboard))
    object Games : NavigationItem<Routes.StartMatch>(Routes.StartMatch, Icons.Default.Favorite, SportAlbumApplication.getString(R.string.games))
    object Bupi : NavigationItem<Routes.Bupi>(Routes.Bupi,Icons.Default.Face, SportAlbumApplication.getString(R.string.bupi))
    object Draw : NavigationItem<Routes.Draw>(Routes.Draw, Icons.Default.List, SportAlbumApplication.getString(R.string.draw))
}

