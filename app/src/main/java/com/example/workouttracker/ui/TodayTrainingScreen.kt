package com.example.workouttracker.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.workouttracker.R
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme
import com.example.workouttracker.datasource.TodayTrainingDataSource
import com.example.workouttracker.model.Exercise
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TodayTrainingScreen(
    modifier: Modifier = Modifier
) {
    Column {
        TodayDateLabel()
        TodayStats(
            todayStats = TodayTrainingDataSource.todayTrainingStatsRow,
        )
        HorizontalDivider()
        Text(
            text = "Today's Training:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(
                    start = dimensionResource(R.dimen.padding_medium),
                    top = dimensionResource(R.dimen.padding_medium),
                    end = dimensionResource(R.dimen.padding_medium),
                    bottom = dimensionResource(R.dimen.padding_small)
                )
        )
        DayLayout()
    }
}

//@Composable
//fun TodayDateLabel() {
//    val currentDate = Date()
//    val formatter = SimpleDateFormat("EEEE, dd MMMM", Locale.getDefault())
//
//    Text(
//        text = formatter.format(currentDate),
//        style = MaterialTheme.typography.headlineSmall,
//        fontWeight = FontWeight.Bold,
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(color = MaterialTheme.colorScheme.secondaryContainer)
//            .padding(
//                start = dimensionResource(R.dimen.padding_medium),
//                top = dimensionResource(R.dimen.padding_medium),
//                end = dimensionResource(R.dimen.padding_medium),
//            )
//    )
//}

@Composable
fun TodayStats(
    todayStats: List<Pair<String, String>>,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
            .padding(
                start = dimensionResource(R.dimen.padding_medium),
                end = dimensionResource(R.dimen.padding_medium),
                bottom = dimensionResource(R.dimen.padding_small)
            )
    ) {
        todayStats.forEach { (title, value) ->
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                )
                Text(text = value)
            }
        }
    }
}

//@Composable
//fun DayLayout(
//    exerciseList: List<Pair<Exercise, String>> = TodayTrainingDataSource.todayTrainingSessions,
//) {
//    Column(
//        modifier = Modifier
//            .verticalScroll(rememberScrollState())
//    ){
//        exerciseList.forEach { (exercise, time) ->
//            Column {
//                Row {
//                    Text(
//                        text = time,
//                        style = MaterialTheme.typography.bodySmall,
//                        modifier = Modifier
//                            .padding(horizontal = dimensionResource(R.dimen.padding_small))
//                    )
//                    HorizontalDivider(
//                        modifier = Modifier
//                            .align(Alignment.CenterVertically)
//                            .padding(end = dimensionResource(R.dimen.padding_small))
//                    )
//                }
//                ExerciseCard(exercise = exercise)
//            }
//        }
//    }
//}
//
//
//@Composable
//fun ExerciseCard(exercise: Exercise){
//    Card(
//        border = BorderStroke(4.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)),
//        shape = RoundedCornerShape(30),
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f)
//        ),
//        modifier = Modifier
//            .offset(y = (-10).dp)
//            .padding(start = 52.dp, end = dimensionResource(R.dimen.padding_medium))
//            .fillMaxWidth()
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(dimensionResource(R.dimen.padding_medium))
//        ) {
//            Text(text = exercise.name)
//
//            if(exercise.type != "Gym") {
//                Text(text = exercise.distance.toString() + "km")
//            } else {
//                Text(text = exercise.weight.toString() + "kg")
//            }
//        }
//    }
//}

//TO BE USED SOMEWHERE ELSE
//@Composable
//fun ExerciseCard(
//    exercise: Exercise,
//    modifier: Modifier = Modifier
//) {
//    var expanded by remember { mutableStateOf(false) }
//
//    Card(
//        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
//        shape = RoundedCornerShape(30),
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(top = dimensionResource(R.dimen.padding_small))
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .fillMaxWidth()
////                .padding(dimensionResource(R.dimen.padding_medium))
//        ) {
//            IconToggleButton(
//                checked = expanded,
//                onCheckedChange = { expanded = !expanded },
//            ) {
//                Icon(
//                    imageVector = if(expanded) Icons.Filled.KeyboardArrowDown else Icons.AutoMirrored.Filled.KeyboardArrowRight,
//                    contentDescription = null
//                )
//            }
//            Text(
//                text = exercise.name,
//                style = MaterialTheme.typography.titleMedium,
//            )
//        }
//    }
//}


@Preview(showBackground = true)
@Composable
fun TodayTrainingScreenPreview() {
    WorkoutTrackerTheme(dynamicColor = false) {
        TodayTrainingScreen(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}