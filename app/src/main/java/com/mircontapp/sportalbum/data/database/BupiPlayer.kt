package com.mircontapp.sportalbum.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mircontapp.sportalbum.domain.models.BupiPlayerModel

@Entity(tableName = "bupi_player")
data class BupiPlayer (
    @PrimaryKey val name: String,
    val team: String?,
    var role: String?
)

fun BupiPlayer.entityFromBupiPlayer() : BupiPlayer {
    return BupiPlayer(this.name, this.team, this.role.toString())
}

