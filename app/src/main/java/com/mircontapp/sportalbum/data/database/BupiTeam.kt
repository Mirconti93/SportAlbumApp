package com.mircontapp.sportalbum.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mircontapp.sportalbum.domain.models.BupiTeamModel

@Entity(tableName = "bupi_team")
class BupiTeam (
    @PrimaryKey val name: String,
    var area: String
)

fun BupiTeam.entityFromBupiTeam() : BupiTeamModel {
    return BupiTeamModel(this.name, this.area)
}


