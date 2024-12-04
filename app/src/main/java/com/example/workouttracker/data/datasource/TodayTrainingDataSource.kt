package com.example.workouttracker.data.datasource

import com.example.workouttracker.data.model.ExerciseTrainingSession

object TodayTrainingDataSource {

    //PLACEHOLDER DATA
    val todayTrainingSessions = listOf(
        Pair( ExerciseTrainingSession(idExercise = 1, type = "Gym", name = "Bench Press", series = 3, reps = 12, weight = 65.0), "8:30"),
        Pair( ExerciseTrainingSession(idExercise = 2, type = "Gym", name = "Squat", series = 3, reps = 12, weight = 112.5), "8:45"),
        Pair( ExerciseTrainingSession(idExercise = 3, type = "Gym", name = "Butterflies", series = 3, reps = 12, weight = 35.0), "8:55"),
        Pair( ExerciseTrainingSession(idExercise = 4, type = "Athletics", name = "Running", distance = 2.5, duration = 30.0), "9:05"),
        Pair( ExerciseTrainingSession(idExercise = 5, type = "Athletics", name = "Cycling", distance = 7.0, duration = 60.0), "17:00"),

        Pair( ExerciseTrainingSession(idExercise = 5, type = "Gym", name = "Bench Press", series = 3, reps = 12, weight = 65.0), "8:30"),
        Pair( ExerciseTrainingSession(idExercise = 5, type = "Gym", name = "Squat", series = 3, reps = 12, weight = 112.5), "8:45"),
        Pair( ExerciseTrainingSession(idExercise = 5, type = "Gym", name = "Butterflies", series = 3, reps = 12, weight = 35.0), "8:55"),
        Pair( ExerciseTrainingSession(idExercise = 5, type = "Athletics", name = "Running", distance = 2.5, duration = 30.0), "9:05"),
        Pair( ExerciseTrainingSession(idExercise = 5, type = "Athletics", name = "Cycling", distance = 7.0, duration = 60.0), "17:00")
    )
}