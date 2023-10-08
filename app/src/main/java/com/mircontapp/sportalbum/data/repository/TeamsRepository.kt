package com.mircontapp.sportalbum.data.repository

import com.mircontapp.sportalbum.domain.models.TeamModel
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

class TeamsRepository() {
    val teams: MutableList<TeamModel> = ArrayList()



    fun teamFactory(row: String) : TeamModel {
        val fields = row.split("_")
        if (fields.size >= 2) {
            return TeamModel(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5], fields[6], fields[7], fields[7])
        } else {
            return TeamModel("Team", "", "", "", "", "", "", "", "")
        }
    }

    fun addTeam(TeamModel: TeamModel) {
        if (teams.contains(TeamModel)) {
            teams.set(teams.indexOf(TeamModel), TeamModel)
        } else {
            teams.add(TeamModel)
        }
    }

    fun teamFromArea(area: String) : List<TeamModel> {
        var teamList = ArrayList<TeamModel>()
        teams.forEach {
            if (it.area.equals(area)) {
                teams.add(it)
            }
        }
        return teams
    }

    fun teamFromAreaOrdered(area: String) : List<TeamModel> {
        return teamFromArea(area).also {
            Collections.sort(it, object : Comparator<TeamModel> {
                override fun compare(a: TeamModel, b: TeamModel): Int {
                    return a.name.compareTo(b.name)
                }}
            )
        }
    }


}