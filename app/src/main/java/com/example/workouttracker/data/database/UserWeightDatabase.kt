package com.example.workouttracker.data.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.workouttracker.data.dao.UserWeightDao
import com.example.workouttracker.data.model.UserWeightData

@Database(entities = [UserWeightData::class], version = 1, exportSchema = false)
abstract class UserWeightDatabase : RoomDatabase() {
    abstract fun userWeightDataDao(): UserWeightDao

    companion object {
        private var Instance: UserWeightDatabase? = null

        fun getDatabase(context: Context): UserWeightDatabase {
            Log.d("UserWeightDatabase", "getDatabase() called")
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext, UserWeightDatabase::class.java, "user_weight_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}