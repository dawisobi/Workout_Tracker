package com.example.workouttracker.ui.exerciseList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.workouttracker.data.model.Exercise
import com.example.workouttracker.data.repository.ExerciseRepository
import kotlinx.coroutines.launch

class ExerciseViewModel(private val repository: ExerciseRepository) : ViewModel() {

    private val _searchResults = MutableLiveData<List<Exercise>>()
    val searchResults: LiveData<List<Exercise>> = _searchResults


    fun searchExercises(query: String) {
        viewModelScope.launch {
            val results = repository.searchExercises(query)
            _searchResults.postValue(results)
        }
    }
}

class ExerciseViewModelFactory(private val repository: ExerciseRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExerciseViewModel::class.java)) {
            return ExerciseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}