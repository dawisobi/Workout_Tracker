package com.example.workouttracker.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WorkoutTrackerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(WorkoutTrackerUiState())
    val uiState: StateFlow<WorkoutTrackerUiState> = _uiState.asStateFlow()


    fun updateShowDialog(newShowDialog: Boolean) {
        _uiState.update { currentState -> currentState.copy(showDialog = newShowDialog) }
    }
}