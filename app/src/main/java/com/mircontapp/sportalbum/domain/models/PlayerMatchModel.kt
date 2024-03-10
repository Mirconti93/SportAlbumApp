package com.mircontapp.sportalbum.domain.models

import com.mirco.sportalbum.utils.Enums

data class PlayerMatchModel(
    val name: String,
    var role: Enums.Role,
    var gender: Enums.Gender?,
    var team: String?,
    val country: String?,
    val birthyear: String?,
    val value: Int?,
    val valueleg: Int?,
    val teamLegend: String?,
    val national: Int?,
    val nationalLegend: Int?,
    val roleLineUp: Enums.RoleLineUp,
    val att: Int,
    val dif: Int,
    val tec: Int,
    val dri: Int,
    val fin: Int,
    val bal: Int,
    val fis: Int,
    val vel: Int,
    val rig: Int,
    val por: Int,
    var roleMatch: Enums.RoleLineUp,
    var isEspulso: Boolean,
    var isAmmonito: Boolean,
)
