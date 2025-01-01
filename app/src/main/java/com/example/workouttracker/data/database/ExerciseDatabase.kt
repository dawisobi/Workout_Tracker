package com.example.workouttracker.data.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.workouttracker.data.dao.ExerciseDao
import com.example.workouttracker.data.dao.TrainingSessionDao
import com.example.workouttracker.data.model.Exercise

@Database(entities = [Exercise::class], version = 1, exportSchema = false)
abstract class ExerciseDatabase : RoomDatabase() {

    abstract fun exerciseDao(): ExerciseDao

    companion object {
        private var Instance: ExerciseDatabase? = null

        fun getDatabase(context: Context): ExerciseDatabase {
            Log.d("ExerciseDatabase", "getDatabase() called")
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext, ExerciseDatabase::class.java, "exercise_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}