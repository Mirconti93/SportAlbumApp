package com.mircontapp.sportalbum.domain.models

import com.mircontapp.sportalbum.data.database.BupiPlayer

data class BupiPlayerModel(
    val name: String,
    var team: String,
    val role: Int?
)

fun BupiPlayerModel.entityFromBupiPlayer() : BupiPlayer {
    return BupiPlayer(this.name, this.team, this.role.toString())
}

