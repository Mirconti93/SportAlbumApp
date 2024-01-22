package com.mircontapp.sportalbum.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TeamDao {

    @Insert
    fun insert(vararg team: Team)

    @Insert
    fun insertAll(teams: List<Team>)

    @Delete
    fun delete(team: Team)

    @Query("select * from team")
    fun getAll(): List<Team>

    @Update
    fun update(team: Team)

}