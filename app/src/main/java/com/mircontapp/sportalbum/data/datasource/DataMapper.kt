package com.mircontapp.sportalbum.data.datasource

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.commons.PlayerHelper
import com.mircontapp.sportalbum.commons.TeamHelper
import com.mircontapp.sportalbum.data.database.BupiPlayer
import com.mircontapp.sportalbum.data.database.BupiTeam
import com.mircontapp.sportalbum.data.database.Player
import com.mircontapp.sportalbum.data.database.Team
import com.mircontapp.sportalbum.domain.models.BupiPlayerModel
import com.mircontapp.sportalbum.domain.models.BupiTeamModel
import com.mircontapp.sportalbum.domain.models.PlayerMatchModel
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel

class DataMapper {
    companion object {
        fun entityFromPlayerModel(player: PlayerModel) : Player {
            return Player(
                name = player.name,
                role = player.role.name,
                gender = player.gender?.name,
                team = player.team,
                country = player.country,
                birthyear = player.birthyear,
                value = player.value.toString(),
                valueleg = player.valueleg.toString(),
                teamLegend = player.teamLegend,
                national = player.national,
                nationalLegend = player.nationalLegend,
                roleLineUp = player.roleLineUp?.name,
                style = player.style?.name
            )
        }

        fun playerModelFromEntity(player: Player) : PlayerModel {
            return PlayerModel(
                name = player.name,
                role = PlayerHelper.roleFromString(player.role) ?: Enums.Role.PP,
                gender = PlayerHelper.genderFromString(player.gender) ?: Enums.Gender.OTHER,
                team = player.team,
                country = player.country,
                birthyear = player.birthyear,
                value = try {Integer.parseInt(player.value ?: "50")} catch (e: Exception) {50},
                valueleg = try {Integer.parseInt(player.valueleg ?: "50")} catch (e: Exception) {50},
                teamLegend = player.teamLegend,
                national = player.national,
                nationalLegend = player.nationalLegend,
                roleLineUp = PlayerHelper.roleLineUpFromString(player.roleLineUp) ?:Enums.RoleLineUp.PTC,
                style = player.
            )
        }

        fun teamModelFromEntity(team: Team) : TeamModel {
            return TeamModel(
                name = team.name,
                city = team.city,
                country = team.country,
                type = team.type,
                color1 =  team.color1,
                color2 = team.color2,
                stadium = team.stadium,
                area = TeamHelper.findAreaEnum(team.area),
                arealegend = TeamHelper.findAreaEnum(team.arealegend),
                superlega = team.superlega,
                coach = team.coach,
                coachlegend = team.coachlegend,
                module = TeamHelper.findModuleEnum(team.module)
            )
        }

        fun entityFromTeamModel(team: TeamModel) : Team {
            return Team(
                name = team.name,
                city = team.city,
                country = team.country,
                type = team.type,
                color1 =  team.color1,
                color2 = team.color2,
                stadium = team.stadium,
                coach = team.coach,
                area = team.area?.name,
                arealegend = team.arealegend?.name,
                superlega = team.superlega,
                coachlegend = team.coachlegend,
                module = team.module.name.substring(1)
            )
        }

        fun playerMatchFromPlayer(player: PlayerModel): PlayerMatchModel {
            return PlayerMatchModel(
                name = player.name,
                role = player.role,
                gender = player.gender ?: Enums.Gender.OTHER,
                team = player.team,
                country = player.country,
                birthyear = player.birthyear,
                value = player.value,
                valueleg = player.valueleg,
                teamLegend = player.teamLegend,
                national = player.national,
                nationalLegend = player.nationalLegend,
                roleLineUp = player.roleLineUp,
                att = player.att?: 50,
                dif = player.dif?: 50,
                tec = player.tec?: 50,
                dri = player.dri?: 50,
                fin = player.fin?: 50,
                bal = player.bal?: 50,
                fis = player.fis?: 50,
                vel = player.vel?: 50,
                rig = player.rig?: 50,
                por = player.por?: 50,
                roleMatch = player.roleLineUp,
                isAmmonito = false,
                isEspulso = false,
                energy = 100.0
            )
        }

        fun bupiPlayerFromEntity(bupiPlayer: BupiPlayer): BupiPlayerModel{
            return BupiPlayerModel(bupiPlayer.name, bupiPlayer.team ?: "Free", bupiPlayer.role?.toIntOrNull())
        }

        fun entityFromBupiPlayer(bupiPlayerModel: BupiPlayerModel) : BupiPlayer {
            return BupiPlayer(bupiPlayerModel.name, bupiPlayerModel.team, bupiPlayerModel.role.toString())
        }

        fun bupiTeamFromEntity(bupiTeam: BupiTeam): BupiTeamModel{
            return BupiTeamModel(bupiTeam.name, bupiTeam.area)
        }

        fun entityFromBupiTeam(bupiTeamModel: BupiTeamModel) : BupiTeam {
            return BupiTeam(bupiTeamModel.name, bupiTeamModel.area)
        }




    }

}