package com.Evelyndamayantiambarita.challengechapter5.data.local.movie

import androidx.room.*

@Dao
interface MovieDAO {
    @Insert
    suspend fun addMovieLocal(movieEntity: MovieEntity)

    @Delete
    suspend fun deleteMovieLocal(movieEntity: MovieEntity)

    @Query("SELECT * FROM movie")
    suspend fun getMovieLocal(): List<MovieEntity>

    @Query("SELECT * FROM movie WHERE id=:id LIMIT 1")
    fun getOneMovie(id: String): MovieEntity?

    @Query("DELETE FROM movie WHERE id = :id")
    fun deleteMovie(id: String): Int
}