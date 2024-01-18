package com.mircontapp.sportalbum.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TeamDao {

    @Insert
    fun insert(vararg team: Team)

    @Insert
    fun insertAll(vararg teams: List<Team>)

    @Delete
    fun delete(team: Team)

    @Query("select * from team")
    fun getAll(): List<Team>

}