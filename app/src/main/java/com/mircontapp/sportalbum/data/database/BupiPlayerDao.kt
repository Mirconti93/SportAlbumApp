package com.mircontapp.sportalbum.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BupiPlayerDao {
    @Insert
    fun insertAll(players: List<BupiPlayer>)

    @Delete
    fun delete(player: BupiPlayer)

    @Query("select * from bupi_player")
    fun getBupi(): List<BupiPlayer>

}