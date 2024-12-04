package com.example.workouttracker.data.repository

import com.example.workouttracker.data.dao.ExerciseDao
import com.example.workouttracker.data.model.Exercise

class ExerciseRepository(private val exerciseDao: ExerciseDao) {

    suspend fun searchExercises(searchQuery: String): List<Exercise> {
        return exerciseDao.searchExercises("%$searchQuery%")
    }
}
