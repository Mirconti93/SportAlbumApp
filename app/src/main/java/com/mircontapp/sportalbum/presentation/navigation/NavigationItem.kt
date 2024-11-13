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
    object Album : NavigationItem<Routes.Album>(Routes.Album, Icons.Default.Home, SportAlbumApplication.instance.getString(R.string.album))
    object Dashboard : NavigationItem<Routes.Dashboard>(Routes.Dashboard, Icons.Default.Create, SportAlbumApplication.instance.getString(R.string.dashboard))
    object Games : NavigationItem<Routes.Game>(Routes.Game, Icons.Default.Favorite, SportAlbumApplication.instance.getString(R.string.games))
    object Bupi : NavigationItem<Routes.Bupi>(Routes.Bupi,Icons.Default.Face, SportAlbumApplication.instance.getString(R.string.bupi))
    object Draw : NavigationItem<Routes.Draw>(Routes.Draw, Icons.Default.List, SportAlbumApplication.instance.getString(R.string.draw))
}

