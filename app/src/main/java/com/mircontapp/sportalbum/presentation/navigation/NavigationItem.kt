package com.mircontapp.sportalbum.presentation.navigation

import android.graphics.drawable.Icon

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Album : NavigationItem("album", Icon.TYPE_DATA, "Album")
    object Dashboard : NavigationItem("dashboard", Icon.TYPE_DATA, "Dashboard")
    object Games : NavigationItem("games", Icon.TYPE_DATA, "Games")
}
