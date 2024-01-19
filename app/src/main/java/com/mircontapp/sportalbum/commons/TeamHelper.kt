package com.mircontapp.sportalbum.commons

import com.mirco.sportalbum.utils.Enums

class TeamHelper {

    companion object {
        fun findAreaEnum(keyText: String?): Enums.Area {
            for (a in Enums.Area.values()) {
                if (keyText.equals(a.toString(), ignoreCase = true)) {
                    return a
                }
            }
            return Enums.Area.OTHER
        }

        fun findModuleEnum(keyText: String?): Enums.MatchModule {
            for (m in Enums.MatchModule.values()) {
                val text = "M" + keyText
                if (text.equals(m.toString(), ignoreCase = true)) {
                    return m
                }
            }
            return Enums.MatchModule.M442
        }

    }



}