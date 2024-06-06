package com.mircontapp.sportalbum.domain.models

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.data.database.Team

data class TeamModel(
    val name: String,
    val city: String?,
    val country: String?,
    val type: String?,
    val color1: String?,
    val color2: String?,
    val stadium: String?,
    val area: Enums.Area?,
    val arealegend: Enums.Area?,
    val superlega: Boolean?,
    val coach: String?,
    val coachlegend: String?,
    val module: Enums.MatchModule
)

fun TeamModel.entityFromTeamModel() : Team {
    return Team(
        name = this.name,
        city = this.city,
        country = this.country,
        type = this.type,
        color1 =  this.color1,
        color2 = this.color2,
        stadium = this.stadium,
        coach = this.coach,
        area = this.area?.name,
        arealegend = this.arealegend?.name,
        superlega = this.superlega,
        coachlegend = this.coachlegend,
        module = this.module.name.substring(1)
    )
}