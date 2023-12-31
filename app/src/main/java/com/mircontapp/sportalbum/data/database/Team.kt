package com.mircontapp.sportalbum.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "team")
class Team (
    @PrimaryKey val name: String,
    val city: String?,
    val country: String?,
    val type: String?,
    val color1: String?,
    val color2: String?,
    val stadium: String?,
    val coach: String?,
    val area: String?
)


