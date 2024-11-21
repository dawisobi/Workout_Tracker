package com.example.workouttracker.ui

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.workouttracker.datasource.ExercisesDatabase.exerciseDb
import com.example.workouttracker.model.Exercise
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
data class WorkoutTrackerUiState(

    val showExerciseListDialog: Boolean = false,
    val showExerciseDetailsDialog: Boolean = false,
//    val currentDateTime: LocalDateTime = LocalDateTime.now(),

    val foundExercises: MutableList<Exercise> = exerciseDb.toMutableList(), //List of exercises matching inserted text; if no text is inserted, it is the same as exerciseDb
    val selectedExercise: Exercise? = null, //Exercise selected from the list to be displayed in the details dialog

//  exercise details variables
//    val setsCount: Int = 1, // size of the setsList
    //val setsList: MutableList<Pair<Int, Double>> = mutableListOf(Pair(0, 0.0)),

//    val repsCount: Int = 0,
//    val weight: Double = 0.0,
)
