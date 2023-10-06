package com.mircontapp.sportalbum.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bupi_team")
class BupiTeam (
    @PrimaryKey val name: String,
    var description: String
)


