package com.moucan.business.db

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.moucan.business.db.entity.Movie

interface MovieDao {
    @Insert
    fun insert(vararg movieTable: Movie?) : LongArray?

    @Delete
    fun delete(movieTable: Movie?): Int

    @Update
    fun update(vararg movieTable: Movie?): Int

    @get:Query("SELECT * FROM movie")
    val allMovieTable: LiveData<List<Movie?>?>
}