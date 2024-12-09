package com.example.workouttracker.data.model

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

//class Exercise(
//    val exerciseId: Int,
//    val type: String,
//    val muscle: String,
//    val name: String,
//    val description: String? = null,
//    @DrawableRes val imageExercise: Int? = null
//)

@Entity(tableName = "exercises")
class Exercise(
    @PrimaryKey val exerciseId: Int,
    val type: String,
    val muscle: String,
    val name: String,
    val description: String? = null,
//    @Ignore @DrawableRes val imageExercise: Int? = null
)