package com.example.workouttracker.ui

import androidx.lifecycle.ViewModel
import com.example.workouttracker.model.Exercise
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WorkoutTrackerViewModel : ViewModel() {

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

}