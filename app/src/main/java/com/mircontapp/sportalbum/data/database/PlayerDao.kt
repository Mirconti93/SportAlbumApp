package com.mircontapp.sportalbum.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlayerDao {

    @Insert
    fun insert(vararg players: Player)

    @Insert
    fun insertAll(vararg players: List<Player>)

    @Delete
    fun delete(player: Player)

    @Query("select * from player")
    fun getAll(): List<Player>

}