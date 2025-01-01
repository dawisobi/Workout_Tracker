package com.example.workouttracker.data.repository

import android.util.Log
import com.example.workouttracker.data.dao.TrainingSessionDao
import com.example.workouttracker.data.model.ExerciseTrainingSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.distinctUntilChangedBy

class TrainingSessionsRepository(private val trainingSessionDao: TrainingSessionDao) {

    // Obtain training sessions
    fun getTrainingSessionsByDate(date: String): Flow<List<ExerciseTrainingSession>> {
        Log.d("TrainingSessionsRepository", "getTrainingSessionsByDate() called, date: $date")
        return trainingSessionDao.getTrainingSessionsByDate(date).distinctUntilChanged()
    }

    // Obtain all training sessions for debug purposes
    fun getAllTrainingSessions(): Flow<List<ExerciseTrainingSession>> {
        Log.d("TrainingSessionsRepository", "getAllTrainingSessions() called")
        return trainingSessionDao.getAllTrainingSessions()
    }

    //Insert training session into database
    suspend fun insertTrainingSession(trainingSession: ExerciseTrainingSession) {
        Log.d("TrainingSessionsRepository", "insertTrainingSession() called")
        trainingSessionDao.insertTrainingSession(trainingSession)
    }

    //Delete training session from database
    suspend fun deleteTrainingSession(trainingSession: ExerciseTrainingSession) {
        Log.d("TrainingSessionsRepository", "deleteTrainingSession() called")
        trainingSessionDao.deleteTrainingSession(trainingSession)
    }

    suspend fun deleteTrainingSessionById(id: Int) {
        Log.d("TrainingSessionsRepository", "deleteTrainingSessionById() called with id: $id")
        trainingSessionDao.deleteTrainingSessionById(id)
    }


    fun getDistinctDates(): Flow<List<String>> {
        Log.d("TrainingSessionsRepository", "getDistinctDates() called")
        return trainingSessionDao.getDistinctDates()
    }

//    fun getTodayTrainingSessions(date: String): Flow<List<ExerciseTrainingSession>> {
//        Log.d("TrainingSessionsRepository", "getTodayTrainingSessions() called")
//        return trainingSessionDao.getTodayTrainingSessions()
//    }

}