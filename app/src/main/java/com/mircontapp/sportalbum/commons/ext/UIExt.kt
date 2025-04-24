package com.mircontapp.sportalbum.commons.ext

import androidx.compose.ui.graphics.Color
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.presentation.ui.theme.BlueD
import com.mircontapp.sportalbum.presentation.ui.theme.Brown
import com.mircontapp.sportalbum.presentation.ui.theme.Green
import com.mircontapp.sportalbum.presentation.ui.theme.LightBlue
import com.mircontapp.sportalbum.presentation.ui.theme.OrangeYellowD
import com.mircontapp.sportalbum.presentation.ui.theme.Pink80
import com.mircontapp.sportalbum.presentation.ui.theme.Purple40
import com.mircontapp.sportalbum.presentation.ui.theme.Red

const val WHITE = "bianco"
const val BLACK = "nero"
const val RED = "rosso"
const val BLUE = "blu"
const val YELLOW = "giallo"
const val GREEN = "verde"
const val LIGHT_BLUE = "azzurro"
const val BROWN = "marrone"
const val VIOLET = "viola"
const val PINK = "rosa"

fun String?.getDrawableId(placeholder: Int): Int {
    val name = this?.replace(" ", "_")?.lowercase()
    val id = try {
        SportAlbumApplication.instance.resources.getIdentifier(name, "drawable", SportAlbumApplication.instance.packageName)
    } catch (e: Exception) {
        e.printStackTrace()
        placeholder
    }
    return if (id > 0) id  else placeholder
}

fun String?.getColorByString(): Color {
    return when (this?.lowercase()) {
        WHITE -> Color.White
        BLACK -> Color.Black
        RED -> Red
        BLUE -> BlueD
        YELLOW -> OrangeYellowD
        GREEN -> Green
        LIGHT_BLUE -> LightBlue
        BROWN -> Brown
        PINK -> Pink80
        VIOLET -> Purple40
        else -> BlueD
    }

}

fun String.minifiyName(): String {
    this.split(" ").let { list ->
        var minified = ""
        for ( i in 0..list.size-1) {
            if (i != list.size-1 && list[i].length > 3) {
                minified += list[i].substring(0,1) + ". "
            } else {
                minified += list[i]
            }

        }
        return minified

    }
}

fun String?.getTeamTextColor(): Color {
    return when (this) {
        YELLOW, WHITE -> Color.Black
        else -> Color.White
    }
}

fun Enums.Evento.getTelecronaca(name: String, diff: Double): String {
    return when (this) {
        Enums.Evento.NONE -> ""
        Enums.Evento.PALLEGGIO -> {
            if (diff > -8) {
                String.format(SportAlbumApplication.getString(R.string.telecronacaCen1), name)
            } else if (diff > -15) {
                String.format(SportAlbumApplication.getString(R.string.telecronacaCen2), name)
            } else {
                String.format(SportAlbumApplication.getString(R.string.telecronacaCen3), name)
            }
        }
        Enums.Evento.PRESSING -> {
            if (diff > 15) {
                String.format(SportAlbumApplication.getString(R.string.telecronacaBal3), name)
            } else if (diff > 8) {
                String.format(SportAlbumApplication.getString(R.string.telecronacaBal2), name)
            } else {
                String.format(SportAlbumApplication.getString(R.string.telecronacaBal1), name)
            }
        }
        Enums.Evento.ATTACCO -> {
             if (diff < 10) {
                String.format(SportAlbumApplication.getString(R.string.telecronacaAtt1), name)
            } else {
                String.format(SportAlbumApplication.getString(R.string.telecronacaAtt2), name)
            }
        }
        Enums.Evento.RECUPERO -> {
            if (diff < 5) {
                String.format(SportAlbumApplication.getString(R.string.telecronacaDif1), name)
            } else if (diff < 10) {
                String.format(SportAlbumApplication.getString(R.string.telecronacaDif2), name)
            } else {
                String.format(SportAlbumApplication.getString(R.string.telecronacaDif3), name)
            }
        }
        Enums.Evento.GOAL -> {
            if (diff < 0 && diff >= -4) {
                String.format(
                    SportAlbumApplication.getString(R.string.telecronacaGol1),
                )
            } else if (diff < -4 && diff >= -8) {
                String.format(
                    SportAlbumApplication.getString(R.string.telecronacaGol2),
                )
            } else {
                String.format(
                    SportAlbumApplication.getString(R.string.telecronacaGol3),
                )
            }
        }
        Enums.Evento.RESPINTA -> {
            if (diff >= 1 && diff < 2) {
                String.format(
                    SportAlbumApplication.getString(R.string.telecronacaPal),
                )
            } else if (diff >= 2 && diff < 3) {
                String.format(
                    SportAlbumApplication.getString(R.string.telecronacaTra),
                )
            } else if (diff >= 3 && diff < 5) {
                String.format(
                    SportAlbumApplication.getString(R.string.telecronacaPar1),

                )
            } else if (diff >= 5 && diff < 7) {
                String.format(
                    SportAlbumApplication.getString(R.string.telecronacaPar2),
                )
            } else if (diff >= 7 && diff < 9) {
                String.format(
                    SportAlbumApplication.getString(R.string.telecronacaOut1),
                )
            } else {
                String.format(
                    SportAlbumApplication.getString(R.string.telecronacaOut2),
                )
            }
        }
        Enums.Evento.PARATA -> {
            if (diff >= 1 && diff < 2) {
                String.format(
                    SportAlbumApplication.getString(R.string.telecronacaPal),
                )
            } else if (diff >= 2 && diff < 3) {
                String.format(
                    SportAlbumApplication.getString(R.string.telecronacaTra),
                )
            } else if (diff >= 3 && diff < 5) {
                String.format(
                    SportAlbumApplication.getString(R.string.telecronacaPar1),

                    )
            } else if (diff >= 5 && diff < 7) {
                String.format(
                    SportAlbumApplication.getString(R.string.telecronacaPar2),
                )
            } else if (diff >= 7 && diff < 9) {
                String.format(
                    SportAlbumApplication.getString(R.string.telecronacaOut1),
                )
            } else {
                String.format(
                    SportAlbumApplication.getString(R.string.telecronacaOut2),
                )
            }
        }
        Enums.Evento.AMMONIZIONE -> String.format(SportAlbumApplication.getString(R.string.telecronacaAmm), name)
        Enums.Evento.ESPULSIONE -> String.format(SportAlbumApplication.getString(R.string.telecronacaEsp), name)
        Enums.Evento.PUNIZIONE_DIRETTA -> ""
        Enums.Evento.PUNIZIONE -> ""
        Enums.Evento.GOAL_RIGORE -> ""
    }
}