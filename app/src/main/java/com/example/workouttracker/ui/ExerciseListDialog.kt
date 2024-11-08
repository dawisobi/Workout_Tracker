package com.example.workouttracker.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.workouttracker.model.Exercise
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme
import com.example.workouttracker.datasource.ExercisesDatabase.exerciseDb


@Composable
fun AddExerciseDialog(
    onDismiss: () -> Unit,
    workoutTrackerViewModel: WorkoutTrackerViewModel,
    exerciseList: MutableList<Exercise>
){
    val configuration = LocalConfiguration.current
    val deviceScreenWidth = configuration.screenWidthDp
    val deviceScreenHeight = configuration.screenHeightDp

    Log.d("AddExerciseDialog", "ExerciseListDialog Opened")

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        ),
    ) {
        Card(
            modifier = Modifier
//                .fillMaxWidth()
//                .fillMaxSize()
                .size((deviceScreenWidth * 0.9).dp, (deviceScreenHeight * 0.9).dp)
//                .width((deviceScreenWidth * 0.9).dp)
//                .height(375.dp)
//                .padding(30.dp),
            ,shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                //horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AddExerciseContent(
                    onDismiss = {
                        onDismiss()
                        workoutTrackerViewModel.resetSearchDialogState()
                        },
                    workoutTrackerViewModel = workoutTrackerViewModel,
                    searchedExerciseName = workoutTrackerViewModel.searchedExercise,
                    onSearchedExerciseChange = {
                        workoutTrackerViewModel.updateSearchedExercise(it)
                        workoutTrackerViewModel.updateExercisesList()
                                               },
                    onKeyboardSearch = { workoutTrackerViewModel.updateExercisesList() },
//                    exerciseList = workoutTrackerViewModel.getExercisesList(),
                    exerciseList = exerciseList
                )
            }
        }
    }
}

@Composable
fun AddExerciseContent(
    onDismiss: () -> Unit,
    workoutTrackerViewModel: WorkoutTrackerViewModel,
    searchedExerciseName: String,
    onSearchedExerciseChange: (String) -> Unit,
    onKeyboardSearch: () -> Unit,
    exerciseList: MutableList<Exercise>
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ){
            Text(
                text = "Select Exercise",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            IconButton(
                onClick = {
                    onDismiss()

                    Log.d("AddExerciseDialog", "Close button clicked")
                }
            ){
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Close"
                )
            }
        }

        OutlinedTextField(
            value = searchedExerciseName,
            singleLine = true,
            onValueChange = onSearchedExerciseChange,
            label = { Text("Search") },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    Log.d("AddExerciseDialog", "Search button clicked: $searchedExerciseName")
                    onKeyboardSearch()

                }
            )
        )
        DisplayExercisesList(exerciseList = exerciseList, workoutTrackerViewModel = workoutTrackerViewModel)
    }
}

@Composable
fun DisplayExercisesList(exerciseList: List<Exercise>, workoutTrackerViewModel: WorkoutTrackerViewModel) {

    if(exerciseList.isEmpty()){
        Log.d("AddExerciseDialog", "No exercises found")
        Text(
            text = "No exercises matching the provided phrase were found.",
            color = Color.Gray,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp).fillMaxWidth()
        )
    } else {
        Log.d("AddExerciseDialog", "Exercises found: ${exerciseList.size}")

        val exerciseListSorted = exerciseList.sortedBy { it.name }
        val listState = rememberLazyListState()

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(exerciseListSorted) { exercise ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            Log.d("AddExerciseDialog", "Exercise selected: ${exercise.name}")
                            workoutTrackerViewModel.updateSelectedExercise(exercise)
                            workoutTrackerViewModel.updateExerciseDetailsDialogState(true)
                        }
                ) {
                    Text(
                        text = exercise.name,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(12.dp)
                    )
                }
                HorizontalDivider()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddExerciseDialogPreview(){
    WorkoutTrackerTheme(dynamicColor = false) {
        AddExerciseDialog( onDismiss = {  }, workoutTrackerViewModel = WorkoutTrackerViewModel(), exerciseList = mutableListOf())//exerciseDb.toMutableList())
    }
}