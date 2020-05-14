package com.example.data.local.dao

import androidx.room.*
import com.example.data.local.entity.FavoriteMovieDataEntity
import io.reactivex.Single

@Dao
interface FavoriteMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieEntity(movieEntity: FavoriteMovieDataEntity)

    @Delete
    fun deleteMovieEntity(movieEntity: FavoriteMovieDataEntity)

    @Query("Select * from favorite_movies")
    fun getFaviriteMovieEntities(): Single<List<FavoriteMovieDataEntity>>

    @Query("Select * from favorite_movies where title like :q")
    fun queryFavoriteMovieEntities(q: String): Single<List<FavoriteMovieDataEntity>>
}