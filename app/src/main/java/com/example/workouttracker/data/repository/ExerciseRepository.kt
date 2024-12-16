package com.example.workouttracker.data.repository

import android.util.Log
import com.example.workouttracker.data.dao.ExerciseDao
import com.example.workouttracker.data.model.Exercise
import kotlinx.coroutines.flow.Flow

class ExerciseRepository(private val exerciseDao: ExerciseDao) {

    // Obtain all exercises from database
    fun getAllExercises(): Flow<List<Exercise>> {
        Log.d("ExerciseRepository", "getAllExercises() called")
        return exerciseDao.getAllExercises()
    }

    // Search exercises using searchQuery
    fun searchExercises(searchQuery: String): Flow<List<Exercise>> {
        Log.d("ExerciseRepository", "searchExercises() called, searchQuery: '$searchQuery'")
        return exerciseDao.searchExercises(searchQuery)
    }

    // Obtain exercise by id
    suspend fun getExerciseById(id: Int): Exercise {
        Log.d("ExerciseRepository", "getExerciseById() called, id: $id")
        return exerciseDao.getExerciseById(id)
    }
}