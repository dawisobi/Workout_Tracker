package com.example.workouttracker.databaseTests

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workouttracker.data.dao.TrainingSessionDao
import com.example.workouttracker.data.database.TrainingSessionsDatabase
import com.example.workouttracker.data.model.ExerciseTrainingSession
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import java.io.IOException
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var trainingSessionDao: TrainingSessionDao
    private lateinit var trainingSessionDb: TrainingSessionsDatabase

    // Create dummy database in memory
    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        trainingSessionDb = Room.inMemoryDatabaseBuilder(context, TrainingSessionsDatabase::class.java).allowMainThreadQueries().build()
        trainingSessionDao = trainingSessionDb.trainingSessionDao()
    }

    // Remove dummy database from memory
    @After
    @Throws(IOException::class)
    fun closeDb() {
        trainingSessionDb.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndReadTrainingSession() = runBlocking {
        // Insert dummy session into database
        val trainingSession = ExerciseTrainingSession( 100, 21, "2025-02-15", "10:45")
        trainingSessionDao.insertTrainingSession(trainingSession)

        // Read session from database
        val getSessions = trainingSessionDao.getTrainingSessionById(100)
        assertTrue(trainingSession.idSession == getSessions.first().idSession)
    }

    @Test
    @Throws(Exception::class)
    fun deleteTrainingSessionFromDb() = runBlocking {
        // Insert dummy session into database
        val trainingSession = ExerciseTrainingSession( 100, 21, "2025-02-15", "10:45")
        trainingSessionDao.insertTrainingSession(trainingSession)

        // Verify session is in database
        val getSessions = trainingSessionDao.getTrainingSessionById(100)
        assertTrue(trainingSession.idSession == getSessions.first().idSession)

        // Delete session from database
        trainingSessionDao.deleteTrainingSession(trainingSession)
        val sessionsAfterDelete = trainingSessionDao.getAllTrainingSessions().firstOrNull()?.size ?: 0
        assertTrue(sessionsAfterDelete == 0)
    }
}