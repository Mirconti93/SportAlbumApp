package com.mircontapp.sportalbum

import android.app.Application
import com.mircontapp.sportalbum.data.database.AppDatabase
import com.mircontapp.sportalbum.data.datasource.AlbumDataSource
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class SportAlbumApplication: Application() {

    companion object {
        lateinit var instance: SportAlbumApplication
    }

    init {
        instance = this
        val database = AppDatabase.getInstance(this)
    }

    override fun onCreate() {
        super.onCreate()
    }

    fun getStringById(id: Int): String {
        return getString(id)
    }


}