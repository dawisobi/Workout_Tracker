package com.example.workouttracker.model

import androidx.annotation.DrawableRes

class Exercise(
    val exerciseId: Int,
    val type: String,
    val name: String,
    val description: String? = null,
    @DrawableRes val imageExercise: Int? = null
)