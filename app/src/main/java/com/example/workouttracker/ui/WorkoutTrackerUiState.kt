package com.example.workouttracker.ui

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.workouttracker.data.datasource.ExercisesDatabase.exerciseDb
import com.example.workouttracker.data.model.Exercise
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
data class WorkoutTrackerUiState(

//    val showExerciseListDialog: Boolean = false, // seems redundant after changing the list to separate screen

    // Whether to show the dialog for exercise details or not
    val showExerciseDetailsDialog: Boolean = false,

    //Exercise selected from the list to be displayed in the details dialog
    val selectedExercise: Exercise? = null,
)
