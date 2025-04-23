package com.mircontapp.sportalbum.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BupiPlayer::class, BupiTeam::class, Player::class, Team::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bupiDao(): BupiPlayerDao
    abstract fun bupiTeamDao(): BupiTeamDao
    abstract fun playerDao(): PlayerDao
    abstract fun teamDao(): TeamDao

    companion object {
        private val DATABASE_NAME = "albumdb.sqlite"

        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        DATABASE_NAME
                    ).createFromAsset("db/$DATABASE_NAME")
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
