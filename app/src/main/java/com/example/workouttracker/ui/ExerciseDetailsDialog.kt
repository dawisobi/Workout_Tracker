package com.example.workouttracker.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.workouttracker.R
import com.example.workouttracker.model.Exercise
import com.example.workouttracker.model.SetDetails
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExerciseDetailsDialog(
    exerciseDetailsViewModel: ExerciseDetailsViewModel = ExerciseDetailsViewModel(),

    onDismiss: () -> Unit,
    onConfirmClick: () -> Unit,
//    onDateUpdate: () -> Unit,
//    onTimeUpdate: () -> Unit,
    currentDateTime: LocalDateTime = LocalDateTime.now(),
    exercise: Exercise,
//    setsCount: Int,
//    setsList: List<Pair<Int, Double>>,
    onSetAdd: () -> Unit,
    onSetRemoval: () -> Unit,
){

    val exerciseDetailsUiState by exerciseDetailsViewModel.uiState.collectAsState()
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

                val contentModifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)

                DateAndTimeRow(
                    currentDateTime = currentDateTime,
                    onDateUpdate = { /*onDateUpdate()*/ },
                    onTimeUpdate = { /*onTimeUpdate()*/ },
                    modifier = contentModifier,
                )

                SetsAndRepsList(
                    modifier = contentModifier,
//                    setsList = exerciseDetailsUiState.setsDetails,
//                    onSetAdd = { onSetAdd() },
//                    onSetRemoval = { onSetRemoval() }
                    onSetAdd = { exerciseDetailsViewModel.addSet() },
                    onSetRemoval = { exerciseDetailsViewModel.removeLastSet() },
                    setCounter = exerciseDetailsUiState.setsCount,
                    repsTextList = exerciseDetailsViewModel.setsRepsList,
                    weightTextList = exerciseDetailsViewModel.setsWeightList
                )

                CancelAndConfirmButtons(
                    modifier = contentModifier,
                    onCancelClick = {
                        //exerciseDetailsViewModel.resetExerciseDetails()
                        onDismiss()
                        Log.d("ExerciseDetailsDialog", "Cancel button clicked")
                    },
                    onConfirmClick = { onConfirmClick() }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateAndTimeRow(
    currentDateTime: LocalDateTime,
    onDateUpdate: () -> Unit,
    onTimeUpdate: () -> Unit,
    modifier: Modifier = Modifier
){

    //val datePickerState = rememberDatePickerState()

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
                onClick = { onDateUpdate() },
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
//                    .clickable {
//
//                        Log.d("ExerciseDetailsDialog", "Date picker should show up here\nProbably not tbh")
//                    }
            ){
                Text(
                    text = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(currentDateTime),
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
                onClick = { onTimeUpdate() },
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
//                    .clickable { Log.d("ExerciseDetailsDialog", "Time picker should show up here\nProbably not tbh") }
            ){
                Text(
                    text = DateTimeFormatter.ofPattern("HH:mm").format(currentDateTime),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetsAndRepsList(
    modifier: Modifier = Modifier,
//    setsCount: Int = 3,
//    setsList: List<Pair<Int, Double>> = listOf(Pair(0, 0.0)),
//    setsList: MutableList<SetDetails>,
    setCounter: Int,
    repsTextList: MutableList<String>,
    weightTextList: MutableList<String>,
    onSetAdd: () -> Unit,
    onSetRemoval: () -> Unit
) {
//    var setsList = mutableListOf(Pair(10, 60), Pair(8, 62.5), Pair(6, 65))
//    var setCount = setCounter
//    Log.d("ExerciseDetailsDialog", "setCount at SetsAndRepsList called: $setCount")


//    var setCounter by remember { mutableStateOf(setCounter) }

//    var repsTextList = remember { repsTextList.toMutableStateList() }

//    Log.d("ExerciseDetailsDialog", "textList: $repsTextList")

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
            Icon(painter = painterResource(id = R.drawable.icon_bin), contentDescription = null, tint = Color.Transparent)

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
                    textStyle = androidx.compose.ui.text.TextStyle(color = Color.DarkGray, fontSize = 16.sp, textAlign = Center), //MaterialTheme.colorScheme.onSurface, fontSize = 14.sp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 10.dp)
                        .background(color = Color.LightGray, shape = RoundedCornerShape(4.dp))
                )

                Text(
                    text = "${weightTextList[index]} kg",
                    textAlign = Center,
                    modifier = Modifier.weight(1f)
                )
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
//                    textList.add("0")
                    repsTextList.add("0")
                    weightTextList.add("0.0")
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
            //shape = RoundedCornerShape(10.dp),
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
            exercise = Exercise(exerciseId = 1, type = "Athletics", name = "Running", description = "Lorem Ipsum Dolor Sit Amet"),
            onConfirmClick = { },
//            onDateUpdate = { },
//            onTimeUpdate = { },
            currentDateTime = LocalDateTime.now(),
//            setsList = listOf(Pair(10, 60.0), Pair(8, 62.5)),
            onSetAdd = { },
            onSetRemoval = { }
        )
    }
}