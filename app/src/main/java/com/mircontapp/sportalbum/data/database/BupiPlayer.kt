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

fun BupiPlayer.bupiPlayerFromEntity() : BupiPlayerModel {
    return BupiPlayerModel(this.name, this.team ?: "Free", this.role?.toIntOrNull())
}

