package com.example.workouttracker.datasource

import com.example.workouttracker.model.Exercise

object TodayTrainingDataSource {

    //PLACEHOLDER DATA
    val todayTrainingSessions = listOf(
        Pair( Exercise(idExercise = 1, type = "Gym", name = "Bench Press", series = 3, reps = 12, weight = 65.0), "8:30"),
        Pair( Exercise(idExercise = 2, type = "Gym", name = "Squat", series = 3, reps = 12, weight = 112.5), "8:45"),
        Pair( Exercise(idExercise = 3, type = "Gym", name = "Butterflies", series = 3, reps = 12, weight = 35.0), "8:55"),
        Pair( Exercise(idExercise = 4, type = "Athletics", name = "Running", distance = 2.5, duration = 30.0), "9:05"),
        Pair( Exercise(idExercise = 5, type = "Athletics", name = "Cycling", distance = 7.0, duration = 60.0), "17:00"),

        Pair( Exercise(idExercise = 5, type = "Gym", name = "Bench Press", series = 3, reps = 12, weight = 65.0), "8:30"),
        Pair( Exercise(idExercise = 5, type = "Gym", name = "Squat", series = 3, reps = 12, weight = 112.5), "8:45"),
        Pair( Exercise(idExercise = 5, type = "Gym", name = "Butterflies", series = 3, reps = 12, weight = 35.0), "8:55"),
        Pair( Exercise(idExercise = 5, type = "Athletics", name = "Running", distance = 2.5, duration = 30.0), "9:05"),
        Pair( Exercise(idExercise = 5, type = "Athletics", name = "Cycling", distance = 7.0, duration = 60.0), "17:00")
    )
}