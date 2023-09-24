package com.example.demoapp.database

import androidx.room.vo.Entity


@Entity(tableName = "bupi_player")
data class BupiPlayer (
    @PrimaryKey val name: String,
    val team: String?,
    var role: String?
)

