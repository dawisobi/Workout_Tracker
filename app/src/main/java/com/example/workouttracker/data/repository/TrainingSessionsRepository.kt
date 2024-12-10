package com.example.workouttracker.data.repository

import android.util.Log
import com.example.workouttracker.data.dao.TrainingSessionDao
import com.example.workouttracker.data.model.ExerciseTrainingSession

class TrainingSessionsRepository(private val trainingSessionDao: TrainingSessionDao) {

    // Obtain training sessions
    suspend fun getTrainingSessionsByDate(date: String): List<ExerciseTrainingSession> {
        Log.d("TrainingSessionsRepository", "getTrainingSessionsByDate() called, date: $date")
        return trainingSessionDao.getTrainingSessionsByDate(date)
    }

    // Obtain all training sessions for debug purposes
    suspend fun getAllTrainingSessions(): List<ExerciseTrainingSession> {
        Log.d("TrainingSessionsRepository", "getAllTrainingSessions() called")
        return trainingSessionDao.getAllTrainingSessions()
    }
}