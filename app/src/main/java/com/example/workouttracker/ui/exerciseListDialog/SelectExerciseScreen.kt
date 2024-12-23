package com.example.workouttracker.ui.exerciseListDialog

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.workouttracker.data.database.ExerciseDatabase
import com.example.workouttracker.data.model.Exercise
import com.example.workouttracker.data.repository.ExerciseRepository
import com.example.workouttracker.ui.TrainingSessionViewModel
import com.example.workouttracker.ui.WorkoutTrackerViewModel
import com.example.workouttracker.ui.exerciseDetailsDialog.ExerciseDetailsDialog
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme
import kotlinx.coroutines.time.debounce

//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun SelectExerciseScreen(
//    onDismiss: () -> Unit,
//    exerciseViewModel: ExerciseViewModel,
//    workoutTrackerViewModel: WorkoutTrackerViewModel,
//    modifier: Modifier
//) {
//    val searchedExercise = workoutTrackerViewModel.searchedExercise
//    val foundExercisesList by exerciseViewModel.searchResults.collectAsState(initial = emptyList())
//
//    LaunchedEffect(key1 = searchedExercise) {
//
//        if (searchedExercise.isBlank()) {
//            Log.d("SelectExerciseScreen", "LaunchedEffect triggered with blank search query")
//            exerciseViewModel.getAllExercises()
//        } else {
//            Log.d("SelectExerciseScreen", "LaunchedEffect triggered with search query: '$searchedExercise'")
//            exerciseViewModel.getExercisesBySearchQuery(searchedExercise)
//        }
//    }
//
//
//
//    Log.d("SelectExerciseScreen", "Composing the SelectExerciseScreen with query: '$searchedExercise'")
//    Column(
//        verticalArrangement = Arrangement.Top,
//        modifier = modifier,
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text(
//                text = "Select Exercise",
//                style = MaterialTheme.typography.titleLarge,
//                fontWeight = FontWeight.Bold
//            )
//
//            IconButton( onClick = {  } )
//            {
//                Icon(imageVector = Icons.Filled.Close, contentDescription = "Close")
//            }
//        }
//
//        OutlinedTextField(
//            value = searchedExercise,
//            singleLine = true,
//            onValueChange = {
//                workoutTrackerViewModel.updateSearchedExercise(it)
////                exerciseViewModel.getExercisesBySearchQuery(workoutTrackerViewModel.searchedExercise)
//                            },
//            label = { Text("Search") },
//            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
////            trailingIcon = {
////                if(searchedExercise.isNotEmpty()){
////                    IconButton(onClick = { onSearchClear() }) {
////                        Icon(Icons.Filled.Clear, contentDescription = "Clear") } } },
//            shape = RoundedCornerShape(16.dp),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 4.dp),
//            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
//            keyboardActions = KeyboardActions(
//                onSearch = {
////                    focusManager.clearFocus()
////                    onKeyboardSearch()
//                }
//            )
//        )
//
//        ExercisesListComponent(
//            exerciseList = foundExercisesList,
//            workoutTrackerViewModel = workoutTrackerViewModel
//        )
//    }
//}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SelectExerciseScreen(
    onDismiss: () -> Unit,
    workoutTrackerViewModel: WorkoutTrackerViewModel,
    exerciseListViewModel: ExerciseViewModel,
    trainingSessionViewModel: TrainingSessionViewModel,
    modifier: Modifier
){
    // Used to trigger dialog popup
    val workoutTrackerUiState by workoutTrackerViewModel.uiState.collectAsState()
    val showExerciseDetailsDialog = workoutTrackerUiState.showExerciseDetailsDialog


    val searchedExercise = workoutTrackerViewModel.searchedExercise //one recomposition triggered always
    val foundExercisesList by exerciseListViewModel.searchResults.collectAsState(initial = emptyList()) // second recomposition triggered only when the searchResults changes

//    LaunchedEffect(key1 = searchedExercise) {
//        if (searchedExercise.isBlank()) {
//            Log.d("SelectExercise_LaunchEffect", "LaunchedEffect triggered with blank search query")
//            exerciseListViewModel.getAllExercises()
//        }
//        else {
//            Log.d("SelectExercise_LaunchEffect", "LaunchedEffect triggered with search query: '$searchedExercise'")
//            exerciseListViewModel.getExercisesBySearchQuery(searchedExercise)
//        }
//    }

    Log.d("SelectExerciseScreen", "Composing the SelectExerciseScreen")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
    ) {
        SelectExerciseHeaderContent(
            onDismiss = { onDismiss() },
            workoutTrackerViewModel = workoutTrackerViewModel,
            searchedExerciseName = searchedExercise,
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
            exerciseList = foundExercisesList
        )
        ExercisesListComponent(exerciseList = foundExercisesList, workoutTrackerViewModel = workoutTrackerViewModel)
    }
    if(showExerciseDetailsDialog) {
        ExerciseDetailsDialog(
            onDismiss = { workoutTrackerViewModel.updateExerciseDetailsDialogState(false) },
            exercise = workoutTrackerUiState.selectedExercise!!,
            onConfirmClick = {
                // Hide both dialogs on confirm
                workoutTrackerViewModel.updateExerciseDetailsDialogState(false)
                onDismiss()
//                workoutTrackerViewModel.updateExerciseListDialogState(false)
            },
            trainingSessionViewModel = trainingSessionViewModel
        )
    }
        }
//    }
//}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SelectExerciseHeaderContent(
    onDismiss: () -> Unit,
    workoutTrackerViewModel: WorkoutTrackerViewModel,
    searchedExerciseName: String,
    onSearchedExerciseChange: (String) -> Unit,
    onSearchClear: () -> Unit,
//    onKeyboardSearch: () -> Unit,
    exerciseList: List<Exercise>
) {
    val focusManager = LocalFocusManager.current

    Log.d("SE_HeaderContent", "Recomposing the header with query: '$searchedExerciseName'")

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()//.background(color = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Text(
                text = "Select Exercise",
                style = MaterialTheme.typography.titleLarge,
                //textAlign = TextAlign.Center,
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
//                    onKeyboardSearch()
                }
            )
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExercisesListComponent(exerciseList: List<Exercise>, workoutTrackerViewModel: WorkoutTrackerViewModel) {

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

//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showBackground = true)
//@Composable
//fun SelectExerciseScreenPreview(){
//    WorkoutTrackerTheme(dynamicColor = false) {
//        SelectExerciseScreen(
//            onDismiss = {  },
//            exerciseListViewModel = ExerciseViewModel(ExerciseRepository(ExerciseDatabase.getDatabase(LocalContext.current).exerciseDao())),
//            workoutTrackerViewModel = WorkoutTrackerViewModel(),
//            trainingSessionViewModel = TrainingSessionViewModel(),
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//        )
//    }
//}