package com.mircontapp.sportalbum.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "bupi_player")
data class Area (
    @PrimaryKey val name: String,
    val team: String?,
    var role: String?
)

