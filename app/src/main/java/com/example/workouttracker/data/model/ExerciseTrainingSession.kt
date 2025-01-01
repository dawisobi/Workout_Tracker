package com.example.workouttracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "training_sessions")
class ExerciseTrainingSession(
    @PrimaryKey(autoGenerate = true) val idSession: Int = 0,
    val idExercise: Int,
    val date: String,
    val time: String,
    val sets: String? = null, //only for type = gym
    val reps: String? = null, // only for type = gym
    val weight: String? = null, // in kilograms, only for type = gym
    val distance: String? = null, // in kilometers, only for type = athletics
    val duration: String? = null // in minutes, only for type = athletics
)