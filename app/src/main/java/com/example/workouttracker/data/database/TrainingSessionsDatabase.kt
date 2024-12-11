package com.example.workouttracker.data.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.workouttracker.data.dao.TrainingSessionDao
import com.example.workouttracker.data.model.ExerciseTrainingSession


@Database(entities = [ExerciseTrainingSession::class], version = 1, exportSchema = false)
abstract class TrainingSessionsDatabase : RoomDatabase() {
    abstract fun trainingSessionDao() : TrainingSessionDao

    companion object {
        private var Instance: TrainingSessionsDatabase? = null

        fun getDatabase(context: Context): TrainingSessionsDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext, TrainingSessionsDatabase::class.java, "performed_training_sessions_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}