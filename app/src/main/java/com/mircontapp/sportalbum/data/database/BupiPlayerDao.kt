package com.mircontapp.sportalbum.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BupiPlayerDao {
    @Insert
    fun insertAll(players: List<BupiPlayer>)

    @Insert
    fun insert(player : BupiPlayer)


    @Update
    fun update(player : BupiPlayer)

    @Delete
    fun delete(player: BupiPlayer)

    @Query("select * from bupi_player")
    fun getBupi(): List<BupiPlayer>

}