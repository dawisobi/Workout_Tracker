package com.example.workouttracker.ui.exerciseListDialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workouttracker.data.model.Exercise
import com.example.workouttracker.data.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ExerciseViewModel(private val exerciseRepository: ExerciseRepository) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<Exercise>>(emptyList())
    val searchResults: Flow<List<Exercise>> = _searchResults

    fun getExercisesBySearchQuery(searchQuery: String) {
        viewModelScope.launch {
            exerciseRepository.searchExercises(searchQuery).collect { list ->
                _searchResults.value = list
            }
        }
    }

    suspend fun getExerciseById(id: Int) : Exercise {
        return exerciseRepository.getExerciseById(id)
//        viewModelScope.launch {
//            exerciseRepository.getExerciseById(id)
//        }
    }

    fun getAllExercises() {
        viewModelScope.launch {
            exerciseRepository.getAllExercises().collect { list ->
                _searchResults.value = list
            }
        }
    }
}

//class ExerciseViewModelFactory(private val repository: ExerciseRepository) : ViewModelProvider.Factory {
//
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(ExerciseViewModel::class.java)) {
//            return ExerciseViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}