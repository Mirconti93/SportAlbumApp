package com.mircontapp.sportalbum.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.commons.AlbumHelper
import com.mircontapp.sportalbum.commons.ext.genderFromString
import com.mircontapp.sportalbum.commons.ext.roleFromString
import com.mircontapp.sportalbum.commons.ext.roleLineUpFromString
import com.mircontapp.sportalbum.commons.ext.styleFromString
import com.mircontapp.sportalbum.domain.models.PlayerModel


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
    val style: String?
)

fun Player.playerModelFromEntity() : PlayerModel {
    return PlayerModel(
        name = this.name,
        role = this.role.roleFromString() ?: Enums.Role.PP,
        gender = this.gender?.genderFromString() ?: Enums.Gender.OTHER,
        team = this.team,
        country = this.country,
        birthyear = this.birthyear,
        value = try {
            Integer.parseInt(this.value ?: "50")} catch (e: Exception) {50},
        valueleg = try {
            Integer.parseInt(this.valueleg ?: "50")} catch (e: Exception) {50},
        teamLegend = this.teamLegend,
        national = this.national,
        nationalLegend = this.nationalLegend,
        roleLineUp = this.roleLineUp?.roleLineUpFromString() ?: Enums.RoleLineUp.PTC,
        style = this.style?.styleFromString()
    )
}

