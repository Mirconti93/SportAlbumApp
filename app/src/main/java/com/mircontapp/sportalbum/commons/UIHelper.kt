package com.mircontapp.sportalbum.commons

import android.content.Context
import android.content.res.Resources
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.widget.Placeholder
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.presentation.ui.theme.BlueD
import com.mircontapp.sportalbum.presentation.ui.theme.Brown
import com.mircontapp.sportalbum.presentation.ui.theme.Green
import com.mircontapp.sportalbum.presentation.ui.theme.OrangeYellowD
import com.mircontapp.sportalbum.presentation.ui.theme.Pink80
import com.mircontapp.sportalbum.presentation.ui.theme.Red
import java.util.ResourceBundle

class UIHelper {

    companion object {

        const val WHITE = "bianco"
        const val BLACK = "nero"
        const val RED = "rosso"
        const val BLUE = "blu"
        const val YELLOW = "giallo"
        const val GREEN = "verde"
        const val BROWN = "marrone"
        const val PINK = "rosa"
        fun getDrawableId(resourceName: String, placeholder: Int): Int {
            val name = resourceName.replace(" ", "_").lowercase()
            val id = try {
                SportAlbumApplication.instance.resources.getIdentifier(name, "drawable", SportAlbumApplication.instance.packageName)
            } catch (e: Exception) {
                e.printStackTrace()
                placeholder
            }
            return if (id > 0) id  else placeholder
        }

        fun getColorByString(colorString: String?): Color {
            return when (colorString?.lowercase()) {
                WHITE -> Color.White
                BLACK -> Color.Black
                RED -> Red
                BLUE -> BlueD
                YELLOW -> OrangeYellowD
                GREEN -> Green
                BROWN -> Brown
                PINK -> Pink80
                else -> BlueD
            }

        }

    }
}