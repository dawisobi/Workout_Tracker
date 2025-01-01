package com.example.workouttracker.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workouttracker.data.model.ExerciseTrainingSession
import com.example.workouttracker.data.repository.TrainingSessionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class TrainingSessionViewModel(private val trainingSessionRepository: TrainingSessionsRepository) : ViewModel() {

    val distinctDates: StateFlow<List<String>> = trainingSessionRepository.getDistinctDates().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private val _searchResults = MutableStateFlow<List<ExerciseTrainingSession>>(emptyList())
    val searchResults: Flow<List<ExerciseTrainingSession>> = _searchResults


    fun getTrainingSessionsByDate(date: String) {
        Log.d("TrainingSessionViewModel", "Getting training sessions for date: $date")
        viewModelScope.launch {
            trainingSessionRepository.getTrainingSessionsByDate(date).collect { list ->
                //Log.d("TrainingSessionViewModel", "Collected training sessions: ${list.forEach({ it.idSession.toString() }) }")
                _searchResults.value = list
            }
        }
    }

    fun insertTrainingSession(trainingSession: ExerciseTrainingSession) {
        Log.d("TrainingSessionViewModel", "Inserting training session for exerciseID: ${trainingSession.idExercise}")
        viewModelScope.launch {
            trainingSessionRepository.insertTrainingSession(trainingSession)
        }
    }

    fun deleteTrainingSession(trainingSession: ExerciseTrainingSession) {
        Log.d("TrainingSessionViewModel", "Deleting training session with ID: ${trainingSession.idSession}")
        viewModelScope.launch {
            trainingSessionRepository.deleteTrainingSession(trainingSession)
        }
    }

    fun deleteTrainingSessionById(id: Int) {
        Log.d("TrainingSessionViewModel", "Deleting training session by ID: $id")
        viewModelScope.launch {
            trainingSessionRepository.deleteTrainingSessionById(id)
        }
    }

    //for debug purposes
    fun getAllTrainingSessions() {
        viewModelScope.launch {
            trainingSessionRepository.getAllTrainingSessions().collect { list ->
                _searchResults.value = list
            }
        }
    }
}