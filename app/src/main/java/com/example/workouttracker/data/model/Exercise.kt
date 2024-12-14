package com.example.workouttracker.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
class Exercise(
    @PrimaryKey @ColumnInfo(name = "exerciseId") val exerciseId: Int,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "muscle") val muscle: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String? = null,
)