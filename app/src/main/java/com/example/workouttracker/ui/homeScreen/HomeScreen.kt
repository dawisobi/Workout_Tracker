package com.example.workouttracker.ui.homeScreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.workouttracker.R
import com.example.workouttracker.data.model.ExerciseTrainingSession
import com.example.workouttracker.ui.TrainingSessionViewModel
import com.example.workouttracker.ui.WorkoutTrackerViewModel
import com.example.workouttracker.ui.exerciseDetailsDialog.ExerciseDetailsDialog
import com.example.workouttracker.ui.exerciseListDialog.AddExerciseDialog
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    workoutTrackerViewModel: WorkoutTrackerViewModel = viewModel(),
    trainingSessionViewModel: TrainingSessionViewModel = viewModel()
) {
    val workoutTrackerUiState by workoutTrackerViewModel.uiState.collectAsState()
    val showExerciseListDialog = workoutTrackerUiState.showExerciseListDialog
    val showExerciseDetailsDialog = workoutTrackerUiState.showExerciseDetailsDialog

    val performedExercises by trainingSessionViewModel.searchResults.collectAsState(initial = emptyList())

    Log.d("HomeScreen", "Obtaining performed exercises... ${performedExercises?.toString()}")

    LaunchedEffect(key1 = Unit) {
        trainingSessionViewModel.getTrainingSessionsByDate(LocalDate.now().toString())
    }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
    ) {

        Text(
            text = stringResource(R.string.welcome_message),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        TodayDateLabel()
        AddExerciseButtonHomeScreen()
        Text(
            text = "Today's Training:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(
                    top = dimensionResource(R.dimen.padding_medium),
                    end = dimensionResource(R.dimen.padding_medium),
                    bottom = dimensionResource(R.dimen.padding_small)
                )
        )

        if(performedExercises.isNullOrEmpty()) {
            Text(text = "No performed exercises found")
            Log.d("HomeScreen", "No performed exercises found")
        } else {
            DayLayout(performedExercises as MutableList<ExerciseTrainingSession>)
        }
    }

    if(showExerciseListDialog) {
        Log.d("ExerciseDetailsDialog", "showExerciseListDialog: $showExerciseListDialog")
        AddExerciseDialog(
            onDismiss = { workoutTrackerViewModel.updateExerciseListDialogState(false) },
            workoutTrackerViewModel = workoutTrackerViewModel,
            exerciseList = workoutTrackerUiState.foundExercises,
//            exerciseListViewModel =
        ) //{ workoutTrackerViewModel.updateShowDialog(false) }
    }
    if(showExerciseDetailsDialog) {
        Log.d("ExerciseDetailsDialog", "showExerciseDetailsDialog: $showExerciseDetailsDialog")
        ExerciseDetailsDialog(
            onDismiss = { workoutTrackerViewModel.updateExerciseDetailsDialogState(false) },
            exercise = workoutTrackerUiState.selectedExercise!!,
            onConfirmClick = {
                // Hide both dialogs
                workoutTrackerViewModel.updateExerciseDetailsDialogState(false)
                workoutTrackerViewModel.updateExerciseListDialogState(false) },
            trainingSessionViewModel = trainingSessionViewModel
        )
    }
}

@Composable
fun TodayDateLabel() {
    val currentDate = Date()
    val formatter = SimpleDateFormat("EEEE, dd MMMM", Locale.getDefault())

    Text(
        text = formatter.format(currentDate),
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.padding_small))
    )
}

@Composable
fun AddExerciseButtonHomeScreen(modifier: Modifier = Modifier) {

    Card(
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_small))
        ){
            IconButton(
                onClick = { },
                modifier = Modifier
                    .size(48.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.rounded_add_circle_24),
                    contentDescription = null,
                    tint =  MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(48.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.add_exercise),
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}


@Composable
fun DayLayout(
    exerciseList: MutableList<ExerciseTrainingSession>,// = TodayTrainingDataSource.todayTrainingSessions,
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ){
        exerciseList.forEach { trainingSession ->
            Column {
                Row {
                    Text(
                        text = trainingSession.time,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .padding(horizontal = dimensionResource(R.dimen.padding_small))
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(end = dimensionResource(R.dimen.padding_small))
                    )
                }
                ExerciseCard(exercise = trainingSession)
            }
        }
        //add space at the bottom of the list so FAB does not block content at the bottom
        Spacer(Modifier.height(56.dp))
    }
}

@Composable
fun ExerciseCard(exercise: ExerciseTrainingSession){
    Card(
        border = BorderStroke(4.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)),
        shape = RoundedCornerShape(30),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f)
        ),
        modifier = Modifier
            .offset(y = (-10).dp)
            .padding(start = 52.dp, end = dimensionResource(R.dimen.padding_medium))
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Text(text = "exercise ID: ${exercise.idExercise.toString()}")
            Text(text = exercise.weight.toString() + "kg")
//
//            if(exercise.type != "Gym") {
//                Text(text = exercise.distance.toString() + "km")
//            } else {
//                Text(text = exercise.weight.toString() + "kg")
//            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    WorkoutTrackerTheme(dynamicColor = false) {
        HomeScreen(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
                .fillMaxSize()
        )
    }
}