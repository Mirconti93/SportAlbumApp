package com.mircontapp.sportalbum.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BupiPlayer::class, BupiTeam::class, Player::class, Team::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bupiDao(): BupiPlayerDao
    abstract fun teamDao(): BupiTeamDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase? {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "bupi_database"
                    ).build()

                    INSTANCE = instance
                }
                return instance
            }


        }
    }
}
