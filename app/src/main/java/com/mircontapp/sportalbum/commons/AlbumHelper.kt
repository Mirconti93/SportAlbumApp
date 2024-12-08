package com.mircontapp.sportalbum.commons

import com.mirco.sportalbum.utils.Enums
import com.mirco.sportalbum.utils.Enums.RoleLineUp
import com.mircontapp.sportalbum.domain.models.BupiPlayerModel
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel


class AlbumHelper {

    companion object {

        fun roleFromString(roleString: String): Enums.Role? {
            for (role in Enums.Role.values()) {
                if (roleString.equals(role.toString(), ignoreCase = true)) {
                    return role
                }
            }
            return Enums.Role.PP
        }

        fun roleLineUpFromString(roleString: String?): Enums.RoleLineUp? {
            for (role in Enums.RoleLineUp.values()) {
                if (roleString.equals(role.toString(), ignoreCase = true)) {
                    return role
                }
            }
            return Enums.RoleLineUp.PPM
        }

        fun genderFromString(genderString: String?): Enums.Gender? {
            for (gender in Enums.Gender.values()) {
                if (genderString.equals(gender.toString(), ignoreCase = true)) {
                    return gender
                }
            }
            return Enums.Gender.OTHER
        }

        fun styleFromString(styleString: String?): Enums.PlayStyle? {
            for (style in Enums.PlayStyle.values()) {
                if (styleString.equals(style.toString(), ignoreCase = true)) {
                    return style
                }
            }
            return Enums.PlayStyle.NORMAL
        }

        fun getDefaultRoles(): Array<RoleLineUp> {
            return arrayOf(
                RoleLineUp.PTC,
                RoleLineUp.DCL,
                RoleLineUp.DCS,
                RoleLineUp.TDD,
                RoleLineUp.TSD,
                RoleLineUp.MED,
                RoleLineUp.REG,
                RoleLineUp.ALD,
                RoleLineUp.ALS,
                RoleLineUp.SPP,
                RoleLineUp.PPM
            )
        }

        fun emptyPlayerModel(name: String): PlayerModel {
            return PlayerModel(name, Enums.Role.PP, null, null, null, null, null, null, null, null, null, Enums.RoleLineUp.PPM, Enums.PlayStyle.NORMAL)
        }

        fun emptyBupiPlayerModel(name: String): BupiPlayerModel {
            return BupiPlayerModel(name, "Free", "Free", 0)
        }

        fun getMatchValue(stat: Int, value: Int) : Double {
            //the value is a weight for the specified stat
            return value*(stat/100.0)

        }

        fun emptyTeamModel(name: String): TeamModel {
            return TeamModel("New", "", "", "", "", "", "", Enums.Area.OTHER, Enums.Area.OTHER,false,"", "", Enums.MatchModule.M442)
        }



    }





}