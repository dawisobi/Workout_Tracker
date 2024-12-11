package com.example.workouttracker.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.workouttracker.data.model.ExerciseTrainingSession
import kotlinx.coroutines.flow.Flow


@Dao
interface TrainingSessionDao {

    @Query("SELECT * FROM training_sessions ORDER BY date DESC, time DESC")
    fun getAllTrainingSessions(): Flow<List<ExerciseTrainingSession>>

    @Query("SELECT * FROM training_sessions WHERE idSession = :id")
    fun getTrainingSessionById(id: Int): Flow<ExerciseTrainingSession>

    @Query("SELECT * FROM training_sessions WHERE date = :date ORDER BY time ASC")
    fun getTrainingSessionsByDate(date: String): Flow<List<ExerciseTrainingSession>>

    @Insert
    suspend fun insertTrainingSession(trainingSession: ExerciseTrainingSession)

    @Delete
    suspend fun deleteTrainingSession(session: ExerciseTrainingSession)

}