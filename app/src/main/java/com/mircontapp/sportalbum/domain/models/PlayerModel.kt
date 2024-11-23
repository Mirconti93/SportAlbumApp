package com.mircontapp.sportalbum.domain.models

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.commons.PlayerHelper
import com.mircontapp.sportalbum.data.database.Player
import com.mircontapp.sportalbum.presentation.commons.OnEditClickHandler
import com.mircontapp.sportalbum.presentation.commons.OnPlayerClickHandler
import com.mircontapp.sportalbum.presentation.commons.ShortListItem

data class PlayerModel(
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
    val style: Enums.PlayStyle?,
)



