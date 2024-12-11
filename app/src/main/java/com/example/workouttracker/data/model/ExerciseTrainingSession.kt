package com.example.workouttracker.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


//TrainingSessionDone ????
//add date variable
//change exercise variables to single variable with type Exercise????
//class ExerciseTrainingSession(
//    val idExercise: Int,
////    val date: String,
////    val time: String,
//    val type: String,
//    val name: String,
//    val description: String? = null,
//    val series: Int? = null,
//    val reps: Int? = null,
//    val weight: Double? = null, // in kilograms
//    val distance: Double? = null, // in kilometers
//    val duration: Double? = null // in minutes
//)

@Entity(
    tableName = "training_sessions",
    //primaryKeys = ["idSession"],
//    foreignKeys = [ForeignKey(entity = Exercise::class, parentColumns = ["exerciseId"], childColumns = ["idExercise"], onDelete = ForeignKey.CASCADE)],
//    indices = [Index("idExercise")]
)
class ExerciseTrainingSession(
    @PrimaryKey(autoGenerate = true) val idSession: Int = 0,
    val idExercise: Int,
    val date: String,
    val time: String,
    val sets: String? = null, //only for type = gym
    val reps: String? = null, // only for type = gym
    val weight: String? = null, // in kilograms, only for type = gym
    val distance: Double? = null, // in kilometers, only for type = athletics
    val duration: Double? = null // in minutes, only for type = athletics
)