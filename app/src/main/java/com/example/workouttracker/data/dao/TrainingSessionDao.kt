package com.example.workouttracker.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.workouttracker.data.model.ExerciseTrainingSession


@Dao
interface TrainingSessionDao {

    @Query("SELECT * FROM training_sessions")
    suspend fun getAllTrainingSessions(): List<ExerciseTrainingSession>

    @Query("SELECT * FROM training_sessions WHERE idSession = :id")
    suspend fun getTrainingSessionById(id: Int): ExerciseTrainingSession

    @Query("SELECT * FROM training_sessions WHERE date = :date")
    suspend fun getTrainingSessionsByDate(date: String): List<ExerciseTrainingSession>

    @Insert
    suspend fun insertTrainingSession(trainingSession: ExerciseTrainingSession)
}