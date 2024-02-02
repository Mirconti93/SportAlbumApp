package com.mircontapp.sportalbum.data.datasource

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.commons.PlayerHelper
import com.mircontapp.sportalbum.commons.TeamHelper
import com.mircontapp.sportalbum.data.database.Player
import com.mircontapp.sportalbum.data.database.Team
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
                att = player.att,
                dif = player.dif,
                tec = player.tec,
                dri = player.dri,
                fin = player.fin,
                bal = player.bal,
                fis = player.fis,
                vel = player.vel,
                rig = player.rig,
                por = player.por,
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
                value = Integer.parseInt(player.value),
                valueleg = Integer.parseInt(player.valueleg),
                teamLegend = player.teamLegend,
                national = player.national,
                nationalLegend = player.nationalLegend,
                roleLineUp = PlayerHelper.roleLineUpFromString(player.roleLineUp),
                att = player.att,
                dif = player.dif,
                tec = player.tec,
                dri = player.dri,
                fin = player.fin,
                bal = player.bal,
                fis = player.fis,
                vel = player.vel,
                rig = player.rig,
                por = player.por,
                Enums.RoleLineUp.PAN
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
    }
}