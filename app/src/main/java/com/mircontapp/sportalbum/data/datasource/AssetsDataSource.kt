package com.mircontapp.sportalbum.data.datasource

import android.content.res.AssetManager
import android.util.Log
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.commons.PlayerHelper
import com.mircontapp.sportalbum.commons.TeamHelper
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel
import java.io.File


class AssetsDataSource(val assets: AssetManager) : AlbumDataSource {
    val players: MutableList<PlayerModel> = ArrayList()
    val teams: MutableList<TeamModel> = ArrayList()
    val PLAYERS_FILE_NAME = "players.txt"
    val TEAMS_FILE_NAME = "teams.txt"

    override suspend fun fetchPlayers(): List<PlayerModel>? {
        assets?.open(PLAYERS_FILE_NAME)?.bufferedReader()?.forEachLine {
            players.add(playerFactory(it))
        }
        return players
    }


    override suspend fun fetchTeams(): List<TeamModel> {
        assets?.open(TEAMS_FILE_NAME)?.bufferedReader()?.forEachLine {
            teams.add(teamFactory(it))
        }
        return teams
    }

    override suspend fun updatePlayer(playerModel: PlayerModel) {
        Log.i("BUPI", "Operation not available")
    }

    override suspend fun updateTeam(teamModel: TeamModel) {
        Log.i("BUPI", "Operation not available")
    }

    override suspend fun insertTeam(teamModel: TeamModel) {
        Log.i("BUPI", "Operation not available")
    }

    override suspend fun insertPlayer(playerModel: PlayerModel) {
        Log.i("BUPI", "Operation not available")
    }
    private fun readFileLines(fileName: String): MutableList<String> = File(fileName).bufferedReader().readLines().toMutableList()

    fun playerFactory(row: String) : PlayerModel {
        val fields = row.split("_")
        if (fields.size>=22) {
            return PlayerModel(
                fields[0], PlayerHelper.roleFromString(fields[1]) ?: Enums.Role.PP,
                PlayerHelper.genderFromString(fields[2]),
                fields[3],
                fields[4],
                fields[5],
                Integer.parseInt(fields[6]),
                Integer.parseInt(fields[7]),
                fields[8],
                Integer.parseInt(fields[9]),
                Integer.parseInt(fields[10]),
                PlayerHelper.roleLineUpFromString(fields[11]) ?: Enums.RoleLineUp.PTC,
                Integer.parseInt(fields[12]),
                Integer.parseInt(fields[13]),
                Integer.parseInt(fields[14]),
                Integer.parseInt(fields[15]),
                Integer.parseInt(fields[16]),
                Integer.parseInt(fields[17]),
                Integer.parseInt(fields[18]),
                Integer.parseInt(fields[19]),
                Integer.parseInt(fields[20]),
                Integer.parseInt(fields[21])
            )
        } else {
            return PlayerHelper.buildPlayerModel(fields[0])
        }

    }

    fun teamFactory(row: String) : TeamModel {
        val fields = row.split("_")
        if (fields.size >= 2) {
            return TeamModel(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5], fields[6], PlayerHelper.findAreaEnum(fields[8]), Enums.Area.OTHER, false, fields[7], "", Enums.MatchModule.M442)
        } else {
            return TeamModel("Team", "", "", "", "", "", "", Enums.Area.OTHER,Enums.Area.OTHER,false,"",  "",Enums.MatchModule.M442)
        }
    }






}