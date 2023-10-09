package com.mircontapp.sportalbum.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlayerDao {

    @Insert
    fun insertAll(vararg players: Player)

    @Delete
    fun delete(user: Player)

    @Query("select * from player")
    fun getAll(): List<Player>

}