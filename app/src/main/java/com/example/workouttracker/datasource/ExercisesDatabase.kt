package com.example.workouttracker.datasource

import com.example.workouttracker.model.Exercise

object ExercisesDatabase {
    val exercisesDB = listOf(
        Exercise(exerciseId = 1, type = "Athletics", name = "Running", description = "Lorem Ipsum Dolor Sit Amet"),
        Exercise(exerciseId = 2, type = "Athletics", name = "Cycling", description = "Lorem Ipsum Dolor Sit Amet"),
        Exercise(exerciseId = 3, type = "Athletics", name = "Walking", description = "Lorem Ipsum Dolor Sit Amet"),
        Exercise(exerciseId = 4, type = "Gym", name = "Bench Press", description = "Lorem Ipsum Dolor Sit Amet"),
        Exercise(exerciseId = 5, type = "Gym", name = "Squat", description = "Lorem Ipsum Dolor Sit Amet"),
        Exercise(exerciseId = 6, type = "Gym", name = "Butterflies", description = "Lorem Ipsum Dolor Sit Amet"),
    )
}