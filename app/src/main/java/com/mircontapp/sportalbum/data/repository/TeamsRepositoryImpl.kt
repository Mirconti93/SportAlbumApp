package com.mircontapp.sportalbum.data.repository

import com.mircontapp.sportalbum.domain.models.TeamModel
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

class TeamsRepositoryImpl() {
    val teams: MutableList<TeamModel> = ArrayList()





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
            if (it.area?.name.equals(area)) {
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