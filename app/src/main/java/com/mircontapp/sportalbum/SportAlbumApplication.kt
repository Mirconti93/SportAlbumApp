package com.mircontapp.sportalbum

import android.app.Application
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SportAlbumApplication: Application() {

    companion object {
        lateinit var instance: SportAlbumApplication
    }

    init {
        instance = this
    }

    fun getStringById(id: Int): String {
        return getString(id)
    }


}