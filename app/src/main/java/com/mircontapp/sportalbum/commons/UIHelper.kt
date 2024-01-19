package com.mircontapp.sportalbum.commons

import android.content.Context
import androidx.constraintlayout.widget.Placeholder
import com.mircontapp.sportalbum.R

class UIHelper {
    companion object {
        fun getDrawableId(context: Context, resourceName: String, placeholder: Int): Int {
            return try {
                context.resources.getIdentifier(resourceName, "drawable", context.packageName)
            } catch (e: Exception) {
                placeholder
            }
        }
    }
}