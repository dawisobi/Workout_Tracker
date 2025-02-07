package com.example.workouttracker.ui.exerciseListDialog

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.workouttracker.data.model.Exercise
import com.example.workouttracker.ui.TrainingSessionViewModel
import com.example.workouttracker.ui.WorkoutTrackerViewModel
import com.example.workouttracker.ui.exerciseDetailsDialog.ExerciseDetailsDialog

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SelectExerciseScreen(
    onDismiss: () -> Unit,
    workoutTrackerViewModel: WorkoutTrackerViewModel,
    exerciseListViewModel: ExerciseViewModel,
    trainingSessionViewModel: TrainingSessionViewModel,
    modifier: Modifier
){
    val focusManager = LocalFocusManager.current

    val workoutTrackerUiState by workoutTrackerViewModel.uiState.collectAsState()
    //state of the dialog
    val showExerciseDetailsDialog = workoutTrackerUiState.showExerciseDetailsDialog

    LaunchedEffect(key1 = Unit) {
        Log.d("SelectExercise_LaunchEffect", "LaunchedEffect triggered with blank search query")
        exerciseListViewModel.getExercisesBySearchQuery(workoutTrackerViewModel.searchedExercise)
    }

    val foundExercisesList by exerciseListViewModel.searchResults.collectAsState(initial = emptyList()) // second recomposition triggered only when the searchResults changes
    Log.d("SelectExerciseScreen", "Recomposing the SelectExerciseScreen")


    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
    ) {
        SelectExerciseHeaderContent(
            onDismiss = { onDismiss() },
            searchedExerciseName = workoutTrackerViewModel.searchedExercise,
            onSearchedExerciseChange = {
                Log.d("SelectExerciseScreen", "Search query changed to: '$it'")
                workoutTrackerViewModel.updateSearchedExercise(it)
                exerciseListViewModel.getExercisesBySearchQuery(workoutTrackerViewModel.searchedExercise)
                                       },
            onSearchClear = {
                Log.d("SelectExerciseScreen", "Search query cleared")
                workoutTrackerViewModel.resetSearchedExercise()
                exerciseListViewModel.getAllExercises()
                            },
            focusManager = focusManager
        )
        ExercisesListComponent(
            exerciseList = foundExercisesList,
            workoutTrackerViewModel = workoutTrackerViewModel,
            focusManager = focusManager
        )
    }
    if(showExerciseDetailsDialog) {
        ExerciseDetailsDialog(
            onDismiss = { workoutTrackerViewModel.updateExerciseDetailsDialogState(false) },
            exercise = workoutTrackerUiState.selectedExercise!!,
            onConfirmClick = {
                // Hide the dialog on confirm
                workoutTrackerViewModel.updateExerciseDetailsDialogState(false)
                onDismiss()
            },
            trainingSessionViewModel = trainingSessionViewModel
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SelectExerciseHeaderContent(
    onDismiss: () -> Unit,
    searchedExerciseName: String,
    onSearchedExerciseChange: (String) -> Unit,
    onSearchClear: () -> Unit,
    focusManager: FocusManager
) {

    Log.d("SE_HeaderContent", "Recomposing the header with query: '$searchedExerciseName'")

    Column(
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(onTap = {
                // Clear focus on any tap outside the text field
                focusManager.clearFocus()
            })
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Select Exercise",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            IconButton( onClick = { onDismiss() })
            {
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
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
            trailingIcon = {
                if(searchedExerciseName.isNotEmpty()){
                    IconButton(onClick = { onSearchClear() }) {
                        Icon(Icons.Filled.Clear, contentDescription = "Clear") } } },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    focusManager.clearFocus()
                }
            )
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExercisesListComponent(
    exerciseList: List<Exercise>,
    workoutTrackerViewModel: WorkoutTrackerViewModel,
    focusManager: FocusManager
) {

    //Log.d("ExerciseListComponent", "Composing the list: ${exerciseList.size}")
    if(exerciseList.isEmpty()){
        Log.d("ExerciseListComponent", "No exercises found")
        Text(
            text = "No exercises matching the provided phrase were found.",
            color = Color.Gray,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 20.dp)
                .fillMaxWidth()
        )
    } else {
        Log.d("ExerciseListComponent", "Exercises found: ${exerciseList.size}")

        val listState = rememberLazyListState()

        LaunchedEffect(listState.isScrollInProgress) {
            if (listState.isScrollInProgress) {
                focusManager.clearFocus()
            }
        }

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(exerciseList) { exercise ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            Log.d("SelectExerciseScreen", "Exercise selected: ${exercise.name}")
                            focusManager.clearFocus()
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