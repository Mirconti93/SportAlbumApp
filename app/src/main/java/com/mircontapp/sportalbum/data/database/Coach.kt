package com.mircontapp.sportalbum.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coach")
class Coach (
    @PrimaryKey val name: String,
    var module: String,
)


