package com.example.workouttracker.ui

import com.example.workouttracker.model.Exercise

data class WorkoutTrackerUiState(

    val showExerciseListDialog: Boolean = false,
    val showExerciseDetailsDialog: Boolean = false,

    val selectedExercise: Exercise? = null,


)
