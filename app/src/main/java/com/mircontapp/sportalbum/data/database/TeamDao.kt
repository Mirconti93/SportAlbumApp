package com.mircontapp.sportalbum.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TeamDao {

    @Insert
    fun insertAll(vararg team: Team)

    @Delete
    fun delete(team: Team)

    @Query("select * from team")
    fun getAll(): List<Team>

}