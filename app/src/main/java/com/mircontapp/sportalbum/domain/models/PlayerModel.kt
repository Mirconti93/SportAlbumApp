package com.mircontapp.sportalbum.domain.models

import com.mirco.sportalbum.utils.Enums

data class PlayerModel(
    val name: String,
    var role: Enums.Role,
    var gender: Enums.Gender?,
    var team: String?,
    val country: String?,
    val birthyear: String?,
    val value: String?,
    val valueleg: String?,
    val teamLegend: String?,
    val national: Int?,
    val nationalLegend: Int?,
    val roleLineUp: String?,
    val att: Int?,
    val dif: Int?,
    val tec: Int?,
    val dri: Int?,
    val fin: Int?,
    val bal: Int?,
    val fis: Int?,
    val vel: Int?,
    val rig: Int?,
    val por: Int?,
)
