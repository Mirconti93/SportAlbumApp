package com.mircontapp.sportalbum

import android.app.Application
import android.util.Log
import com.mircontapp.sportalbum.data.datasource.AssetsDataSource
import com.mircontapp.sportalbum.data.datasource.DatabaseDataSource
import com.mircontapp.sportalbum.data.repository.PlayersRepositoryImpl
import com.mircontapp.sportalbum.data.repository.TeamsRepositoryImpl
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltAndroidApp
class SportAlbumApplication: Application() {

    companion object {
        lateinit var instance: SportAlbumApplication

    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
    }

    private fun insertToDB() {
        CoroutineScope(Dispatchers.IO).launch {
            val dataSource = AssetsDataSource(assets)
            var repoT = TeamsRepositoryImpl(dataSource)
            val teams = repoT.getAllTeams()
            Log.i("BUPI", "Teams:" + teams.size.toString())
            var repoP = PlayersRepositoryImpl(dataSource)
            val players = repoP.getAllPlayers()
            Log.i("BUPI", "Players:" + players.size.toString())

            val databaseDS = DatabaseDataSource()
            databaseDS.insertAllTeams(teams)
            databaseDS.insertAllPlayers(players)

        }
    }
}