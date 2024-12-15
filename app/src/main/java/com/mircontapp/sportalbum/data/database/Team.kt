package com.mircontapp.sportalbum.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mircontapp.sportalbum.commons.ext.findAreaEnum
import com.mircontapp.sportalbum.commons.ext.findModuleEnum
import com.mircontapp.sportalbum.domain.models.TeamModel

@Entity(tableName = "team")
class Team (
    @PrimaryKey val name: String,
    val city: String?,
    val country: String?,
    val type: String?,
    val color1: String?,
    val color2: String?,
    val stadium: String?,
    val area: String?,
    val arealegend: String?,
    val isMatch: Boolean?,
    val coach: String?,
    val coachlegend: String?,
    val module: String?,
)

fun Team.teamModelFromEntity() : TeamModel {
    return TeamModel(
        name = this.name,
        city = this.city,
        country = this.country,
        type = this.type,
        color1 =  this.color1,
        color2 = this.color2,
        stadium = this.stadium,
        area = this.area.findAreaEnum(),
        arealegend = this.arealegend.findAreaEnum(),
        isMatch = this.isMatch,
        coach = this.coach,
        coachlegend = this.coachlegend,
        module = this.module.findModuleEnum()
    )
}


