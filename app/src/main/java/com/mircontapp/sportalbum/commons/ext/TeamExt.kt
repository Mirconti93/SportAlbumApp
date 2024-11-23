package com.mircontapp.sportalbum.commons.ext

import com.mirco.sportalbum.utils.Enums

fun String?.findAreaEnum(): Enums.Area {
    for (a in Enums.Area.values()) {
        if (this.equals(a.toString(), ignoreCase = true)) {
            return a
        }
    }
    return Enums.Area.OTHER
}

fun String?.findModuleEnum(): Enums.MatchModule {
    for (m in Enums.MatchModule.values()) {
        val text = "M" + this
        if (text.equals(m.name, ignoreCase = true)) {
            return m
        }
    }
    return Enums.MatchModule.M442
}