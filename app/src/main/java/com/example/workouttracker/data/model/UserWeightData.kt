package com.example.workouttracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userWeightDatabase")
class UserWeightData(
    @PrimaryKey(autoGenerate = true) val entryId: Int = 0,
    val date: String,
    val userWeight: Double,
)