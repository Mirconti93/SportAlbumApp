package com.example.demoapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BupiTeamDao {
    @Insert
    fun insertAll(vararg teams: BupiTeam)

    @Delete
    fun delete(team: BupiTeam)

    @Query("select * from bupi_team")
    fun getTeams(): List<BupiTeam>
}