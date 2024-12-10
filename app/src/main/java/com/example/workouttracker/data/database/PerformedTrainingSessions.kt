package com.example.workouttracker.data.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.workouttracker.data.dao.TrainingSessionDao
import com.example.workouttracker.data.model.ExerciseTrainingSession


@Database(entities = [ExerciseTrainingSession::class], version = 1, exportSchema = false)
abstract class PerformedTrainingSessionsDatabase : RoomDatabase() {
    abstract fun trainingSessionDao(): TrainingSessionDao

    companion object {
        private var INSTANCE: PerformedTrainingSessionsDatabase? = null

        fun getDatabase(context: Context): PerformedTrainingSessionsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PerformedTrainingSessionsDatabase::class.java,
                    "performed_training_sessions_database").build()

                Log.d("PerformedTrainingSessions.kt", "PerformedTrainingSessionsDatabase::getDatabase() called, instance: $instance")

                INSTANCE = instance
                instance
            }
        }
    }
}