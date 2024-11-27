package com.mircontapp.sportalbum.commons.ext

import androidx.compose.ui.graphics.Color
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