package com.example.workouttracker.datasource

import com.example.workouttracker.model.Exercise

object TodayTrainingDataSource {

    val todayTrainingStatsRow = listOf(
        Pair("Sessions", "2"),
        Pair("Exercises", "10"),
        Pair("Time", "2h 12min")
    )

    val todayTrainingSessions = listOf(
        Pair( Exercise(type = "Gym", name = "Bench Press", series = 3, reps = 12, weight = 65.0), "8:30"),
        Pair( Exercise(type = "Gym", name = "Squat", series = 3, reps = 12, weight = 112.5), "8:45"),
        Pair( Exercise(type = "Gym", name = "Butterflies", series = 3, reps = 12, weight = 35.0), "8:55"),
        Pair( Exercise(type = "Athletics", name = "Running", distance = 2.5, duration = 30.0), "9:05"),
        Pair( Exercise(type = "Athletics", name = "Cycling", distance = 7.0, duration = 60.0), "17:00"),

        Pair( Exercise(type = "Gym", name = "Bench Press", series = 3, reps = 12, weight = 65.0), "8:30"),
        Pair( Exercise(type = "Gym", name = "Squat", series = 3, reps = 12, weight = 112.5), "8:45"),
        Pair( Exercise(type = "Gym", name = "Butterflies", series = 3, reps = 12, weight = 35.0), "8:55"),
        Pair( Exercise(type = "Athletics", name = "Running", distance = 2.5, duration = 30.0), "9:05"),
        Pair( Exercise(type = "Athletics", name = "Cycling", distance = 7.0, duration = 60.0), "17:00")
    )
}