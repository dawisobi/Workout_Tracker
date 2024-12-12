package com.example.workouttracker.ui.exerciseDetailsDialog

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.workouttracker.data.model.SetDetails
import java.time.LocalDateTime


@RequiresApi(Build.VERSION_CODES.O)
data class ExerciseDetailsUiState(
    val currentDateTime: LocalDateTime = LocalDateTime.now(),

    val setsCount: Int = 1, // size of the setsList
    val setsDetails: MutableList<SetDetails> = mutableListOf(SetDetails(0, 0.0)),
)