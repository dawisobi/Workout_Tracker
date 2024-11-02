package com.example.workouttracker.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.workouttracker.model.Exercise
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme

@Composable
fun ExerciseDetailsDialog(
    onDismiss: () -> Unit,
    exercise: Exercise
){
//    var reps = 0
//    var series = 0

    Log.d("AddExerciseDialog", "ExerciseDetailsDialog Opened")

    Dialog(
        onDismissRequest = { onDismiss() },
    ) {
        Card(
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "Exercise: ${exercise.name}")
                exercise.description?.let { Text(text = "Description: ${exercise.description}") }



            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExerciseDetailsDialogPreview() {
    WorkoutTrackerTheme(dynamicColor = false) {
        ExerciseDetailsDialog(onDismiss = { }, exercise = Exercise(exerciseId = 1, type = "Athletics", name = "Running", description = "Lorem Ipsum Dolor Sit Amet"),)
    }
}