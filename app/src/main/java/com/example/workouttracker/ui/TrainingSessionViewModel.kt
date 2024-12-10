package com.example.workouttracker.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workouttracker.data.model.ExerciseTrainingSession
import com.example.workouttracker.data.repository.TrainingSessionsRepository
import kotlinx.coroutines.launch

class TrainingSessionViewModel(private val trainingSessionRepository: TrainingSessionsRepository) : ViewModel() {
    private val _searchResults = MutableLiveData<List<ExerciseTrainingSession>>()
    val searchResults: LiveData<List<ExerciseTrainingSession>> = _searchResults

    suspend fun getTrainingSessionsByDate(date: String) {
        viewModelScope.launch {
            val list = trainingSessionRepository.getTrainingSessionsByDate(date)
            _searchResults.postValue(list)
        }
    }

    //for debug purposes
    suspend fun getAllTrainingSessions() {
        viewModelScope.launch {
            val list = trainingSessionRepository.getAllTrainingSessions()
            _searchResults.postValue(list)
        }
    }
}