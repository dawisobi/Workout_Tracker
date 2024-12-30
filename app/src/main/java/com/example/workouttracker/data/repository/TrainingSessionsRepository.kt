package com.example.workouttracker.data.repository

import android.util.Log
import com.example.workouttracker.data.dao.TrainingSessionDao
import com.example.workouttracker.data.model.ExerciseTrainingSession
import kotlinx.coroutines.flow.Flow

class TrainingSessionsRepository(private val trainingSessionDao: TrainingSessionDao) {

    // Obtain training sessions
    fun getTrainingSessionsByDate(date: String): Flow<List<ExerciseTrainingSession>> {
        Log.d("TrainingSessionsRepository", "getTrainingSessionsByDate() called, date: $date")
        return trainingSessionDao.getTrainingSessionsByDate(date)
    }

    // Obtain all training sessions for debug purposes
    fun getAllTrainingSessions(): Flow<List<ExerciseTrainingSession>> {
        Log.d("TrainingSessionsRepository", "getAllTrainingSessions() called")
        return trainingSessionDao.getAllTrainingSessions()
    }

    //Insert training session into database
    suspend fun insertTrainingSession(trainingSession: ExerciseTrainingSession) {
        trainingSessionDao.insertTrainingSession(trainingSession)
    }

    //Delete training session from database
    suspend fun deleteTrainingSession(trainingSession: ExerciseTrainingSession) {
        trainingSessionDao.deleteTrainingSession(trainingSession)
    }

    suspend fun deleteTrainingSessionById(id: Int) {
        trainingSessionDao.deleteTrainingSessionById(id)
    }
}