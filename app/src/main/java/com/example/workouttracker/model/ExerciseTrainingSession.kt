package com.example.workouttracker.model


//TrainingSessionDone ????
//add date variable
//change exercise variables to single variable with type Exercise????
class ExerciseTrainingSession(
    val idExercise: Int,
//    val date: String,
//    val time: String,
    val type: String,
    val name: String,
    val description: String? = null,
    val series: Int? = null,
    val reps: Int? = null,
    val weight: Double? = null, // in kilograms
    val distance: Double? = null, // in kilometers
    val duration: Double? = null // in minutes
)