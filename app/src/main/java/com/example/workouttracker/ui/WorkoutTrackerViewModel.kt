package com.example.workouttracker.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.workouttracker.data.model.Exercise
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@RequiresApi(Build.VERSION_CODES.O)
class WorkoutTrackerViewModel : ViewModel() {

    //variable for the searched exercise text in the dialog
    var searchedExercise by mutableStateOf("")
        private set

    private val _uiState = MutableStateFlow(WorkoutTrackerUiState())
    val uiState: StateFlow<WorkoutTrackerUiState> = _uiState.asStateFlow()

    fun updateExerciseListDialogState(newShowExerciseListDialogState: Boolean) {
        _uiState.update { currentState -> currentState.copy(showExerciseListDialog = newShowExerciseListDialogState) }
    }

    fun updateExerciseDetailsDialogState(newShowExerciseDetailsDialogState: Boolean) {
        _uiState.update { currentState -> currentState.copy(showExerciseDetailsDialog = newShowExerciseDetailsDialogState) }
    }

    fun updateSelectedExercise(newSelectedExercise: Exercise) {
        _uiState.update { currentState -> currentState.copy(selectedExercise = newSelectedExercise) }
    }

    fun updateSearchedExercise(newSearchedExercise: String) {
        Log.d("WorkoutTrackerViewModel", "Changing searched exercise from '$searchedExercise' to '$newSearchedExercise'...")
        searchedExercise = newSearchedExercise
    }

    fun resetSearchedExercise() {
        Log.d("WorkoutTrackerViewModel", "Setting the searched exercise to empty string...")
        searchedExercise = ""
    }
}