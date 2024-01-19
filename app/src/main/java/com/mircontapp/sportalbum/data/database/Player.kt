package com.mircontapp.sportalbum.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "player")
data class Player (
    @PrimaryKey val name: String,
    var role: String,
    var gender: String?,
    var team: String?,
    val country: String?,
    val birthyear: String?,
    val value: String?,
    val valueleg: String?,
    val teamLegend: String?,
    val national: Int?,
    val nationalLegend: Int?,
    val roleLineUp: String?,
    val att: Int?,
    val dif: Int?,
    val tec: Int?,
    val dri: Int?,
    val fin: Int?,
    val bal: Int?,
    val fis: Int?,
    val vel: Int?,
    val rig: Int?,
    val por: Int?,
)

