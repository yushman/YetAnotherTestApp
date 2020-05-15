package com.example.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.local.dao.FavoriteMoviesDao
import com.example.data.local.database.FavoriteMoviesDatabase.Companion.DATABASE_VERSION
import com.example.data.local.entity.FavoriteMovieDataEntity

@Database(entities = [FavoriteMovieDataEntity::class], version = DATABASE_VERSION)
abstract class FavoriteMoviesDatabase: RoomDatabase(){

    abstract fun favoriteMoviesDao(): FavoriteMoviesDao

    companion object {
        const val DATABASE_VERSION = 1

        @Volatile
        private var INSTANCE: FavoriteMoviesDatabase? = null

        fun getInstance(context: Context): FavoriteMoviesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteMoviesDatabase::class.java,
                    "YATA_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}