package com.mircontapp.sportalbum.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlayerDao {
    @Insert
    fun insertAll(vararg users: Player)

    @Delete
    fun delete(user: BupiPlayer)

    @Query("select * from player")
    fun getBupi(): List<BupiPlayer>

}