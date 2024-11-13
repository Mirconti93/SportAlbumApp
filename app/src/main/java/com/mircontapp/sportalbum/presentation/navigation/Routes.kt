package com.mircontapp.sportalbum.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    object Album

    @Serializable
    data class TeamAlbum(val team: String)

    @Serializable
    data class Sticker(val player: String)

    @Serializable
    object Dashboard

    @Serializable
    data class EditTeam(val team: String?)

    @Serializable
    data class EditPlayer(val player: String?)

    @Serializable
    object Game

    @Serializable
    data class Match(val homeTeam: String, val awayTeam: String)

    @Serializable
    object Bupi

    @Serializable
    data class EditBupiPlayer(val bupiPlayer: String?)

    @Serializable
    data class EditBupiTeam(val bupiTeam: String?)

    @Serializable
    object Draw
}