package com.mircontapp.sportalbum.domain.models

import com.mirco.sportalbum.utils.Enums

data class TeamModel(
    val name: String,
    val city: String?,
    val country: String?,
    val type: String?,
    val color1: String?,
    val color2: String?,
    val stadium: String?,
    val coach: String?,
    val area: Enums.Area?
)