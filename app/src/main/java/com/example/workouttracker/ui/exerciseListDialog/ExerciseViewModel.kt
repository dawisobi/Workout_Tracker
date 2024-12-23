package com.example.workouttracker.ui.exerciseListDialog

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workouttracker.data.model.Exercise
import com.example.workouttracker.data.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import java.time.Duration
import kotlin.time.Duration.Companion.seconds

class ExerciseViewModel(private val exerciseRepository: ExerciseRepository) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<Exercise>>(emptyList())
    val searchResults: Flow<List<Exercise>> = _searchResults

    @RequiresApi(Build.VERSION_CODES.O)
    fun getExercisesBySearchQuery(searchQuery: String) {


        Log.d("ExerciseViewModel", "Searching for exercises with query: '$searchQuery'")

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
        Log.d("ExerciseViewModel", "Getting all exercises...")

        viewModelScope.launch {
            exerciseRepository.getAllExercises().collect { list ->
                _searchResults.value = list
            }
        }
    }
}