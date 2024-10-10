package com.example.workouttracker.ui

import android.util.Log
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
import com.example.workouttracker.datasource.TodayTrainingDataSource
import com.example.workouttracker.model.Exercise
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(
    workoutTrackerViewModel: WorkoutTrackerViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val workoutTrackerUiState by workoutTrackerViewModel.uiState.collectAsState()
    val showDialog = workoutTrackerUiState.showDialog

    Log.d("AddExerciseDialog", "showDialog(uiState): ${workoutTrackerUiState.showDialog}")
    Log.d("AddExerciseDialog", "showDialog: $showDialog")

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
        DayLayout()
    }

    if(showDialog) {
        Log.d("AddExerciseDialog", "HomeScreen.kt -> showDialog: $showDialog")
        AddExerciseDialog { workoutTrackerViewModel.updateShowDialog(false) }
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
    exerciseList: List<Pair<Exercise, String>> = TodayTrainingDataSource.todayTrainingSessions,
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ){
        exerciseList.forEach { (exercise, time) ->
            Column {
                Row {
                    Text(
                        text = time,
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
                ExerciseCard(exercise = exercise)
            }
        }
        //add space at the bottom of the list so FAB does not block content at the bottom
        Spacer(Modifier.height(56.dp))
    }
}

@Composable
fun ExerciseCard(exercise: Exercise){
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
            Text(text = exercise.name)

            if(exercise.type != "Gym") {
                Text(text = exercise.distance.toString() + "km")
            } else {
                Text(text = exercise.weight.toString() + "kg")
            }
        }
    }
}

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