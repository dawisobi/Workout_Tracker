package com.example.workouttracker.ui

import android.util.Log
import androidx.annotation.ColorRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.workouttracker.R
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

                DateAndTimeRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )


                CancelAndConfirmButtons(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    onCancelClick = { Log.d("ExerciseDetailsDialog", "Cancel button clicked") },
                    onConfirmClick = { Log.d("ExerciseDetailsDialog", "Confirm button clicked") }
                )

            }
        }
    }
}

@Composable
fun DateAndTimeRow(modifier: Modifier = Modifier){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .background(color = Color.LightGray, shape = RoundedCornerShape(4.dp))
                .weight(1f)
            //.padding(6.dp)
        ){
            IconButton(
                onClick = { Log.d("ExerciseDetailsDialog", "Date should be set to the current day") },
                modifier = Modifier
                    .size(34.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_calendar),
                    contentDescription = "Date",
                    tint = Color.Black
                )
            }
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .height(34.dp)
                    .clickable { Log.d("ExerciseDetailsDialog", "Date picker should show up here") }
            ){
                Text(
                    text = "2024-11-07",
                    fontWeight = FontWeight.Bold,
//                                textAlign = Center,
                    modifier = Modifier
//                                    .padding(end = 8.dp)
//                                    .weight(1f)
                )
            }

        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(color = Color.LightGray, shape = RoundedCornerShape(4.dp))
                .weight(1f)
            //.padding(6.dp)
        ){
            IconButton(
                onClick = { Log.d("ExerciseDetailsDialog", "Time should be set to the current time") },
                modifier = Modifier
                    .size(34.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_clock),
                    contentDescription = "Time",
                    tint = Color.Black
                )
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .height(34.dp)
                    .clickable { Log.d("ExerciseDetailsDialog", "Time picker should show up here") }
            ){
                Text(
                    text = "23:56",
                    fontWeight = FontWeight.Bold,
//                                textAlign = Center,
                    modifier = Modifier
                        .padding(end = 17.dp)
//                                    .weight(1f)
                )
            }
        }
    }
}

@Composable
fun CancelAndConfirmButtons(
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val cancelColor = Color(0xFFBA1A1A)
    val confirmColor = Color(0xFF3ba858)

//    val cancelColor = MaterialTheme.colorScheme.onPrimary
//    val confirmColor = MaterialTheme.colorScheme.primaryContainer

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { onCancelClick() },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = cancelColor,
                contentColor = Color.Black
//                containerColor = MaterialTheme.colorScheme.error,
//                contentColor = MaterialTheme.colorScheme.onError
            ),
        ) {
            Text(text = "Cancel")
        }

        Button(
            onClick = { onConfirmClick() },
            colors = ButtonDefaults.buttonColors(
                containerColor = confirmColor,
                contentColor = Color.Black
            ),
        ) {
            Text(text = "Confirm")
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