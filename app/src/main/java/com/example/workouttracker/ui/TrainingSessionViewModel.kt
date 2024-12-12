package com.example.workouttracker.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workouttracker.data.model.ExerciseTrainingSession
import com.example.workouttracker.data.repository.TrainingSessionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TrainingSessionViewModel(private val trainingSessionRepository: TrainingSessionsRepository) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<ExerciseTrainingSession>>(emptyList())
    val searchResults: Flow<List<ExerciseTrainingSession>> = _searchResults

    fun getTrainingSessionsByDate(date: String) {
        viewModelScope.launch {
            trainingSessionRepository.getTrainingSessionsByDate(date).collect { list ->
                _searchResults.value = list
            }
        }
    }

    fun insertTrainingSession(trainingSession: ExerciseTrainingSession) {
        viewModelScope.launch {
            trainingSessionRepository.insertTrainingSession(trainingSession)
        }
    }

    fun deleteTrainingSession(trainingSession: ExerciseTrainingSession) {
        viewModelScope.launch {
            trainingSessionRepository.deleteTrainingSession(trainingSession)
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