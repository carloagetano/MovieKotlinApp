package com.example.moviekotlinapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviekotlinapp.data.database.entity.MovieLocal

@Database(entities = [MovieLocal::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun getMovieLocalDao(): MovieLocalDao

    companion object {

        //Volatile annotation means any change to this field
        // are immediately visible to other threads.
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        private const val DB_NAME = "movies_database"

        fun getDatabase(context: Context): MovieDatabase {

            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            // here synchronised used for blocking the other thread
            // from accessing another while in a specific code execution.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    MovieDatabase::class.java,
                    DB_NAME
                ).build()

                INSTANCE = instance

                //return database instance
                instance
            }
        }
    }
}