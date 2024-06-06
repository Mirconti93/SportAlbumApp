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
    val style: Enums.PlayStyle,
    val att: Double,
    val dif: Double,
    val tec: Double,
    val dri: Double,
    val fin: Double,
    val bal: Double,
    val fis: Double,
    val vel: Double,
    val rig: Double,
    val por: Double,
    var roleMatch: Enums.RoleLineUp,
    var isEspulso: Boolean,
    var isAmmonito: Boolean,
    var energy: Double,

)
