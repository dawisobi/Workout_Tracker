package com.example.workouttracker.model

class Exercise(
    val type: String,
    val name: String,
    val description: String? = null,
    val series: Int? = null,
    val reps: Int? = null,
    val weight: Double? = null, // in kilograms
    val distance: Double? = null, // in kilometers
    val duration: Double? = null // in minutes
)