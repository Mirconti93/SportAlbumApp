package com.mircontapp.sportalbum.commons

import android.content.Context
import android.content.res.Resources
import androidx.constraintlayout.widget.Placeholder
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import java.util.ResourceBundle

class UIHelper {
    companion object {
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
    }
}