package com.mircontapp.sportalbum.domain.models

import com.mircontapp.sportalbum.data.database.BupiTeam

data class BupiTeamModel (
    val name: String,
    val area: String
)

fun BupiTeamModel.entityFromBupiTeam() : BupiTeam {
    return BupiTeam(this.name, this.area)
}