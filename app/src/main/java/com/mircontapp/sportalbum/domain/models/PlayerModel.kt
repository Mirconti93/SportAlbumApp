package com.example.bupialbum.models

import android.util.TypedValue
import com.mirco.sportalbum.utils.Enums

data class PlayerModel(
    val name: String,
    var role: Enums.Role,
    var Gender: String?,
    var team: String?,
    val country: String?,
    val birthyear: String?,
    val value: String?,
    val valueleg: String?,
    val teamLegend: String?,
    val national: Int?,
    val nationalLegend: Int?,
    val roleLineUp: String?
)
