package com.mircontapp.sportalbum.commons

import com.mirco.sportalbum.utils.Enums

class TeamHelper {

    companion object {
        fun findAreaEnum(keyText: String): Enums.Area {
            for (a in Enums.Area.values()) {
                if (keyText.equals(a.toString(), ignoreCase = true)) {
                    return a
                }
            }
            return Enums.Area.OTHER
        }

    }



}