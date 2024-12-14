package com.example.workouttracker.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.workouttracker.data.datasource.ExercisesDatabase.exerciseDb
import com.example.workouttracker.data.model.Exercise
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private var foundExercises = mutableStateListOf<Exercise>()

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
        searchedExercise = newSearchedExercise
    }

    private fun resetSearchedExercise() {
        Log.d("WorkoutTrackerViewModel", "Setting the searched exercise to empty string...")
        searchedExercise = ""
    }

    private fun resetFoundExercisesList() {
        Log.d("WorkoutTrackerViewModel", "Clearing foundExercises list...")
        foundExercises.clear()

        Log.d("WorkoutTrackerViewModel", "Adding all exercises from exerciseDB (${exerciseDb.size}) to foundExercises")
        foundExercises.addAll(exerciseDb)
        _uiState.update { currentState -> currentState.copy(foundExercises = foundExercises) }
    }

    fun resetSearchDialogState() {
        Log.d("WorkoutTrackerViewModel", "resetSearchDialogState() called")
        resetSearchedExercise()
        resetFoundExercisesList()
    }

    fun updateExercisesList() {
        Log.d("WorkoutTrackerViewModel", "updateExercisesList() called")
        updateFoundExercisesList(searchedExercise)
        Log.d("WorkoutTrackerViewModel", "updateExercisesList() foundExercises: ${foundExercises.size}")
        _uiState.update { currentState -> currentState.copy(foundExercises = foundExercises) }
    }

}


private fun updateFoundExercisesList(searchedExercise: String): List<Exercise> {
    foundExercises.clear()
    val exercisesList = exerciseDb

    for (exercise in exercisesList) {
        if (isMatchingExercise(exercise, searchedExercise)) {
            foundExercises.add(exercise)
        }
    }
    return foundExercises
}

private fun isMatchingExercise(exercise: Exercise, searchedExercise: String): Boolean {
    return exercise.name.contains(searchedExercise, ignoreCase = true)
}