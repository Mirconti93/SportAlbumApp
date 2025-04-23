package com.mircontapp.sportalbum.domain.models

import com.mirco.sportalbum.utils.Enums

data class MarcatoreModel(
    val text: String,
    val minute: Int,
    val possesso: Enums.TeamPosition
)
