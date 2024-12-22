package com.example.workouttracker.ui.exerciseDetailsDialog

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.workouttracker.R
import com.example.workouttracker.data.model.Exercise
import com.example.workouttracker.data.model.ExerciseTrainingSession
import com.example.workouttracker.ui.TrainingSessionViewModel
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExerciseDetailsDialog(
    exerciseDetailsViewModel: ExerciseDetailsViewModel = ExerciseDetailsViewModel(),
    onDismiss: () -> Unit,
    onConfirmClick: () -> Unit,
    exercise: Exercise,
    trainingSessionViewModel: TrainingSessionViewModel? = null //null for preview
){
    Log.d("AddExerciseDialog", "ExerciseDetailsDialog Opened")

    val exerciseDetailsUiState by exerciseDetailsViewModel.uiState.collectAsState()
    val contentModifier = Modifier
        .fillMaxWidth()
        .padding(top = 10.dp)

    Dialog( onDismissRequest = { onDismiss() } )
    {
        Card( shape = RoundedCornerShape(16.dp))
        {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = exercise.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)

                if(exercise.type == "Gym") Text(text = "Muscle: ${exercise.muscle}", style = MaterialTheme.typography.titleMedium)
                else if (exercise.type == "Athletics") Text(text = "Type: ${exercise.type}", style = MaterialTheme.typography.titleMedium)

                DateAndTimeRow(
                    currentDateTime = exerciseDetailsUiState.currentDateTime,
                    onDateTimeUpdate = { exerciseDetailsViewModel.updateCurrentDateTime() },
                    modifier = contentModifier,
                )

                if(exercise.type == "Gym"){
                    SetsAndRepsList(
                        modifier = contentModifier,
                        onSetAdd = { exerciseDetailsViewModel.addSet() },
                        onSetRemoval = { exerciseDetailsViewModel.removeLastSet() },
                        setCounter = exerciseDetailsUiState.setsCount,
                        repsTextList = exerciseDetailsViewModel.setsRepsList,
                        weightTextList = exerciseDetailsViewModel.setsWeightList
                    )
                } else if(exercise.type == "Athletics") {
                    AthleticsSessionDetails(
                        modifier = contentModifier,
                        distance = exerciseDetailsViewModel.distance,
                        duration = exerciseDetailsViewModel.duration,
                        onDistanceChange = { exerciseDetailsViewModel.updateDistance(it) },
                        onDurationChange = { exerciseDetailsViewModel.updateDuration(it) }
                    )
                }

                CancelAndConfirmButtons(
                    modifier = contentModifier,
                    onCancelClick = { onDismiss(); Log.d("ExerciseDetailsDialog", "Cancel button clicked") },
                    onConfirmClick = {
                        exerciseDetailsViewModel.updateSetsDetailsOnConfirm()

                        val trainingSessionToAdd = ExerciseTrainingSession(
                            date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(exerciseDetailsUiState.currentDateTime).toString(),
                            time = DateTimeFormatter.ofPattern("HH:mm").format(exerciseDetailsUiState.currentDateTime).toString(),
                            idExercise = exercise.exerciseId,
                            sets = exerciseDetailsUiState.setsCount.toString(),
                            reps = exerciseDetailsViewModel.convertRepsToString(exerciseDetailsUiState.setsDetails),
                            weight = exerciseDetailsViewModel.convertWeightToString(exerciseDetailsUiState.setsDetails),
                            distance = exerciseDetailsViewModel.distance,
                            duration = exerciseDetailsViewModel.duration
                        )

                        trainingSessionViewModel?.insertTrainingSession(trainingSessionToAdd)
                        onConfirmClick()
                        Log.d("ExerciseDetailsDialog", "Confirm button clicked")
                    }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateAndTimeRow(
    currentDateTime: LocalDateTime,
    onDateTimeUpdate: () -> Unit,
    modifier: Modifier = Modifier
){
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
        ){
            IconButton(
                onClick = { onDateTimeUpdate() },
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
            ){
                Text(
                    text = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(currentDateTime),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                )
            }

        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(color = Color.LightGray, shape = RoundedCornerShape(4.dp))
                .weight(1f)
        ){
            IconButton(
                onClick = { onDateTimeUpdate() },
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
            ){
                Text(
                    text = DateTimeFormatter.ofPattern("HH:mm").format(currentDateTime),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(end = 17.dp)
                )
            }
        }
    }
}

@Composable
fun AthleticsSessionDetails(
    distance: String,
    duration: String,
    modifier: Modifier,
    onDistanceChange: (String) -> Unit,
    onDurationChange: (String) -> Unit,
) {
    Column(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Distance", fontWeight = FontWeight.Bold, textAlign = Center, modifier = Modifier.weight(1f))
            Text(text = "Duration", fontWeight = FontWeight.Bold, textAlign = Center, modifier = Modifier.weight(1f))
        }

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f).fillMaxWidth()
            ){
                BasicTextField(
                    value = distance,
                    onValueChange = { onDistanceChange(it) },
                    textStyle = TextStyle(color = Color.DarkGray, fontSize = 16.sp, textAlign = Center), //MaterialTheme.colorScheme.onSurface, fontSize = 14.sp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 24.dp, end = 4.dp)
                        .background(color = Color.LightGray, shape = RoundedCornerShape(4.dp)),
                )
                Text(text = "km", modifier = Modifier.padding(end = 24.dp))
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f).fillMaxWidth()
            ){
                BasicTextField(
                    value = duration,
                    onValueChange = { onDurationChange(it) },
                    textStyle = TextStyle(color = Color.DarkGray, fontSize = 16.sp, textAlign = Center), //MaterialTheme.colorScheme.onSurface, fontSize = 14.sp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 24.dp, end = 4.dp)
                        .background(color = Color.LightGray, shape = RoundedCornerShape(4.dp)),
                )
                Text(text = "min", modifier = Modifier.padding(end = 24.dp))
            }
        }
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
    }
}


@Composable
fun SetsAndRepsList(
    modifier: Modifier = Modifier,
    setCounter: Int,
    repsTextList: MutableList<String>,
    weightTextList: MutableList<String>,
    onSetAdd: () -> Unit,
    onSetRemoval: () -> Unit
) {

    Column(
        modifier = modifier,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Set", fontWeight = FontWeight.Bold, textAlign = Center, modifier = Modifier.weight(1f))
            Text(text = "Reps", fontWeight = FontWeight.Bold, textAlign = Center, modifier = Modifier.weight(1f))
            Text(text = "Weight", fontWeight = FontWeight.Bold, textAlign = Center, modifier = Modifier.weight(1f))
            Icon(painter = painterResource(id = R.drawable.icon_bin), contentDescription = null, tint = Color.Transparent) //Just to make headers align with respective columns
        }

        repeat(setCounter) { index ->
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = (index + 1).toString(),
                    textAlign = Center,
                    modifier = Modifier.weight(1f)
                )
                BasicTextField(
                    value = repsTextList[index],
                    onValueChange = { newText -> repsTextList[index] = newText },
                    textStyle = TextStyle(color = Color.DarkGray, fontSize = 16.sp, textAlign = Center), //MaterialTheme.colorScheme.onSurface, fontSize = 14.sp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 10.dp)
                        .background(color = Color.LightGray, shape = RoundedCornerShape(4.dp))
                )

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ){
                    BasicTextField(
                        value = weightTextList[index],
                        onValueChange = { newText -> weightTextList[index] = newText },
                        textStyle = TextStyle(color = Color.DarkGray, fontSize = 16.sp, textAlign = Center), //MaterialTheme.colorScheme.onSurface, fontSize = 14.sp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                            .background(color = Color.LightGray, shape = RoundedCornerShape(4.dp))
                    )
                    Text(text = "kg")
                }


                if(index == setCounter - 1 && setCounter > 1) {
                    IconButton(
                        onClick = {
                            repsTextList.removeLast()
                            weightTextList.removeLast()
                            onSetRemoval()
                            Log.d("ExerciseDetailsDialog", "Remove set button clicked")},
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_bin),
                            contentDescription = null,
                            tint = Color.DarkGray,
                        )
                    }
                } else {
                    Icon(painter = painterResource(id = R.drawable.icon_bin), contentDescription = null, tint = Color.Transparent)
                }
            }
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
        }

        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .padding(start = 10.dp, top = 5.dp)
                .clickable {
                    repsTextList.add("0")
                    weightTextList.add("0")
                    onSetAdd()
                    Log.d("ExerciseDetailsDialog", "Add set button clicked")
                    Log.d("ExerciseDetailsDialog", "repsTextList: ${repsTextList.toString()}")
                }
        ){
            Icon(painter = painterResource(id = R.drawable.rounded_add_circle_24), contentDescription = "Add")
            Text(text = "Add set", modifier = Modifier.padding(start = 5.dp))
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
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { onCancelClick() },
            colors = ButtonDefaults.buttonColors(
                containerColor = cancelColor,
                contentColor = Color.Black
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ExerciseDetailsDialogPreview() {
    WorkoutTrackerTheme(dynamicColor = false) {
        ExerciseDetailsDialog(
            onDismiss = { },
            exercise = Exercise(exerciseId = 1, type = "Athletics", muscle = "Cardio", name = "Running", description = "Lorem Ipsum Dolor Sit Amet"),
//            exercise = Exercise(exerciseId = 1, type = "Gym", muscle = "Chest", name = "Bench Press", description = "Lorem Ipsum Dolor Sit Amet"),
            onConfirmClick = { },
        )
    }
}