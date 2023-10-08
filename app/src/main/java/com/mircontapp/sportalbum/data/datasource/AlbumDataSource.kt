package com.mircontapp.sportalbum.data.datasource

import android.content.res.AssetManager
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel
import java.io.File


class AlbumDataSource {
    val players: MutableList<PlayerModel> = ArrayList()
    val teams: MutableList<TeamModel> = ArrayList()
    final val PLAYERS_FILE_NAME = "players.txt"
    final val TEAMS_FILE_NAME = "teams.txt"


    var assets: AssetManager?

    constructor(assets: AssetManager) {
        this.assets = assets
    }

    //todo
    /*fun fetchPlayersByAssets() {
        assets?.open(PLAYERS_FILE_NAME)?.bufferedReader()?.forEachLine {
            players.add(playerFactory(it))
        }
    }

    fun fetchTeamsByAssets() {
        assets?.open(FILE_NAME)?.bufferedReader()?.forEachLine {
            teams.add(teamFactory(it))
        }
    }*/

    private fun readFileLines(fileName: String): MutableList<String> = File(fileName).bufferedReader().readLines().toMutableList()

    companion object {
        var instance: AlbumDataSource? = null;

        fun findAreaEnum(keyText: String): Enums.Area {
            for (a in Enums.Area.values()) {
                if (keyText.equals(a.toString(), ignoreCase = true)) {
                    return a
                }
            }
            return Enums.Area.OTHER
        }

        fun roleFromString(roleString: String): Enums.Role? {
            for (role in Enums.Role.values()) {
                if (roleString.equals(role.toString(), ignoreCase = true)) {
                    return role
                }
            }
            return Enums.Role.PP
        }

        fun roleLineUpFromString(roleString: String): Enums.RoleLineUp? {
            for (role in Enums.RoleLineUp.values()) {
                if (roleString.equals(role.toString(), ignoreCase = true)) {
                    return role
                }
            }
            return Enums.RoleLineUp.PPM
        }

        fun genderFromString(genderString: String): Enums.Gender? {
            for (gender in Enums.Gender.values()) {
                if (genderString.equals(gender.toString(), ignoreCase = true)) {
                    return gender
                }
            }
            return Enums.Gender.OTHER
        }

        fun getGenders(): List<Enums.Gender?>? {
            return ArrayList<Enums.Gender?>().also {
                for (e in Enums.Gender.values()) {
                    it.add(e)
                }
            }
        }

        //todo
        /*fun getRoleModels(): List<RoleModel> {
            val roles = ArrayList<RoleModel>()
            Enums.Role.values().forEach {
                roles.add(RoleModel(it.name, it.code.toString(), it.ordinal))
            }
            return roles

        }

        fun getPlayerStatus(): List<Enums.PlayerStatus?>? {
            return ArrayList<Enums.PlayerStatus?>().also {
                for (e in Enums.PlayerStatus.values()) {
                    it.add(e)
                }
            }
        }


        fun teamsFromArea(area: AreaModel): List<TeamModel>? {
            val teams: MutableList<TeamModel> = ArrayList()
            for (team in DataManager.app.getTeams()) {
                if (team.getArea() != null && area.equals(team.getArea())) {
                    teams.add(team)
                }
            }
            return teams
        }*/

        //todo
        /*fun findAreaModel(area: String): AreaModel? {
        val areaEnum = findAreaEnum(area)
       for (am in DataManager.app.getAreas()) {
            if (am.getArea() != null && am.getArea() === areaEnum) {
                return am
            }
        }
            return null
        }

        fun bupiTeamFactory(row: String) : BupiTeamModel {
            val fields = row.split("_")
            when (fields.size) {
                1 -> {return BupiTeamModel(row, "")}
                2 -> {return BupiTeamModel(fields[0], fields[1])}
                else -> {return BupiTeamModel("", "")}
            }

        }*/
    }





}