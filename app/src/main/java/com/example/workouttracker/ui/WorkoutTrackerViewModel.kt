package com.example.workouttracker.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.workouttracker.datasource.ExercisesDatabase.exerciseDb
import com.example.workouttracker.model.Exercise
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime


//private var foundExercises: MutableList<Exercise> = mutableListOf()

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
        searchedExercise = ""
    }

    private fun resetFoundExercisesList() {
        foundExercises.clear()
        foundExercises.addAll(exerciseDb)
    }

    fun resetSearchDialogState() {
        resetSearchedExercise()
        resetFoundExercisesList()
    }

    fun updateExercisesList() {
        Log.d("WorkoutTrackerViewModel", "updateExercisesList() called")
        updateFoundExercisesList(searchedExercise)
        Log.d("WorkoutTrackerViewModel", "updateExercisesList() foundExercises: ${foundExercises.size}")
        _uiState.update { currentState -> currentState.copy(foundExercises = foundExercises) }
//        for (exercise in foundExercises) {
//            Log.d("WorkoutTrackerViewModel", exercise.name)
//        }

    }

//    fun getExercisesList(): List<Exercise> {
//        return _uiState.value.foundExercises
//    }

//    resets the exercise details to default values
//    fun resetExerciseDetails() {
//        _uiState.update { currentState -> currentState.copy(selectedExercise = null, setsList = mutableListOf(Pair(0, 0.0))) }
//    }
//
//    fun updateCurrentDateTime() {
//        _uiState.update { currentState -> currentState.copy(currentDateTime = LocalDateTime.now()) }
//    }
//
//    fun addSet(reps: Int, weight: Double) {
//        _uiState.update { currentState -> currentState.copy(setsList = currentState.setsList.toMutableList().also { it.add(Pair(reps, weight))} )}
//    }
//
//    fun removeLastSet() {
//        _uiState.update { currentState -> currentState.copy(setsList = currentState.setsList.toMutableList().also { it.removeLast() } )}
//    }


//    fun updateUiSetsCount(newSetsCount: Int) {
//        _uiState.update { currentState -> currentState.copy(setsCount = newSetsCount) }
//    }
//
//    fun setsCountIncreaseUi() {
//        _uiState.update { currentState -> currentState.copy(setsCount = currentState.setsCount + 1) }
//    }
//
//    fun setsCountDecreaseUi() {
//        _uiState.update { currentState -> currentState.copy(setsCount = currentState.setsCount - 1) }
//    }

//    fun updateUiRepsCount(newRepsCount: Int) {
//        _uiState.update { currentState -> currentState.copy(repsCount = newRepsCount) }
//    }


}



private fun updateFoundExercisesList(searchedExercise: String): List<Exercise> {
    foundExercises.clear()
    val exercisesList = exerciseDb

    for (exercise in exercisesList) {
        if (isMatchingExercise(exercise, searchedExercise)) {
            foundExercises.add(exercise)
//            Log.d("WorkoutTrackerViewModel", exercise.name)
        }
    }
    return foundExercises
}

private fun isMatchingExercise(exercise: Exercise, searchedExercise: String): Boolean {
    return exercise.name.contains(searchedExercise, ignoreCase = true)
}