package com.example.workouttracker.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workouttracker.data.model.ExerciseTrainingSession
import com.example.workouttracker.data.repository.TrainingSessionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class CalendarTrainingSessionViewModel(
    private val trainingSessionRepository: TrainingSessionsRepository
) : ViewModel() {

    private val _searchResultsCalendar = MutableStateFlow<List<ExerciseTrainingSession>>(emptyList())
    val searchResultsCalendar: Flow<List<ExerciseTrainingSession>> = _searchResultsCalendar


    fun getTrainingSessionsByDate(date: String) {
        Log.d("TrainingSessionViewModel", "Getting training sessions for date: $date")
        viewModelScope.launch {
            trainingSessionRepository.getTrainingSessionsByDate(date).collect { list ->
                _searchResultsCalendar.value = list
            }
        }
    }

    fun insertTrainingSession(trainingSession: ExerciseTrainingSession) {
        Log.d("TrainingSessionViewModel", "Inserting training session: $trainingSession")
        viewModelScope.launch {
            trainingSessionRepository.insertTrainingSession(trainingSession)
        }
    }

    fun deleteTrainingSession(trainingSession: ExerciseTrainingSession) {
        Log.d("TrainingSessionViewModel", "Deleting training session: $trainingSession")
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
                _searchResultsCalendar.value = list
            }
        }
    }
}