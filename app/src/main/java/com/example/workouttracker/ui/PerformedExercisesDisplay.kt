package com.example.workouttracker.ui

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.workouttracker.R
import com.example.workouttracker.data.database.ExerciseDatabase
import com.example.workouttracker.data.model.Exercise
import com.example.workouttracker.data.model.ExerciseTrainingSession
import com.example.workouttracker.data.repository.ExerciseRepository
import com.example.workouttracker.ui.exerciseListDialog.ExerciseViewModel


@Composable
fun PerformedExercisesDisplay(
    exerciseList: MutableList<ExerciseTrainingSession>
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
fun ExerciseCard(
    exercise: ExerciseTrainingSession,
    exerciseViewModel: ExerciseViewModel = ExerciseViewModel(ExerciseRepository(ExerciseDatabase.getDatabase(LocalContext.current).exerciseDao()))
){
    var exerciseData by remember { mutableStateOf<Exercise?>(null) }
    var isExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(exercise.idExercise) {
        exerciseData = exerciseViewModel.getExerciseById(exercise.idExercise)
        Log.d("ExerciseCard", "Exercise data loaded for exercise ID ${exercise.idExercise}")
    }


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
            .clickable { isExpanded = !isExpanded }
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                exerciseData?.let {
                    Text(text = it.name, style = MaterialTheme.typography.titleMedium)
                }

                if(!isExpanded) {
                    Icon(imageVector = Icons.Filled.KeyboardArrowDown,
                        contentDescription = "Arrow Down")
                } else {
                    Icon(imageVector = Icons.Filled.KeyboardArrowUp,
                        contentDescription = "Arrow Down")
                }

            }

            if (isExpanded) {
                exerciseData?.let {
                    if(it.type == "Gym") { ExerciseTypeGym(exercise) }
                    else { ExerciseTypeAthletics(exercise) }
                }
            }
        }
    }
}

@Composable
fun ExerciseTypeGym(
    exercise: ExerciseTrainingSession
){
    exercise.sets?.toInt()?.let {

        val repsList = exercise.reps?.split(",")
        val weightList = exercise.weight?.split(",")

        repeat(it) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium))
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.weight(1f)
                ){
                    Text(text = "${it + 1} set")
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.weight(1f)
                ){
                    Text(text = "${repsList?.get(it)} reps")
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.weight(1f)
                ){
                    Text(text = "${weightList?.get(it)} kg")
                }
            }
        }
    }
}


@Composable
fun ExerciseTypeAthletics( exercise: ExerciseTrainingSession ){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(1f)
        ){
            Text(text = "${exercise.distance} km")
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(1f)
        ){
            Text(text = "${exercise.duration} min")
        }
    }
}