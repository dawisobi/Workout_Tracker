package com.example.workouttracker.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.workouttracker.data.model.Exercise
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercises ORDER BY name ASC")
    fun getAllExercises(): Flow<List<Exercise>>

    @Query("SELECT * FROM exercises WHERE name LIKE '%' || :searchQuery || '%' ORDER BY name ASC")
    fun searchExercises(searchQuery: String): Flow<List<Exercise>>

    @Query("SELECT * FROM exercises WHERE exerciseId = :id")
    suspend fun getExerciseById(id: Int): Exercise
}