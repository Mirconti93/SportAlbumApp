package com.mircontapp.sportalbum.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BupiTeamDao {
    @Insert
    fun insertAll(teams: List<BupiTeam>)

    @Insert
    fun insert(team: BupiTeam)


    @Update
    fun update(team: BupiTeam)

    @Delete
    fun delete(team: BupiTeam)

    @Query("select * from bupi_team")
    fun getTeams(): List<BupiTeam>
}