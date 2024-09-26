package com.example.workouttracker.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workouttracker.R
import com.example.workouttracker.datasource.TodayTrainingDataSource
import com.example.workouttracker.model.Exercise
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
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
//        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        TodayDateLabel()
        AddExerciseButtonHomeScreen()
//        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        Text(
            text = "Today's Training:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(
//                    start = dimensionResource(R.dimen.padding_medium),
                    top = dimensionResource(R.dimen.padding_medium),
                    end = dimensionResource(R.dimen.padding_medium),
                    bottom = dimensionResource(R.dimen.padding_small)
                )
        )
        DayLayout()
//        WorkoutHistoryCard()
//        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
//        AchievementsCardHomeScreen()
    }
}

@Composable
fun TodayDateLabel() {
    val currentDate = Date()
    val formatter = SimpleDateFormat("EEEE, dd MMMM", Locale.getDefault())

    Text(
        text = formatter.format(currentDate),
        style = MaterialTheme.typography.headlineSmall,
//        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
//            .background(color = MaterialTheme.colorScheme.secondaryContainer)
            .padding(
                vertical = dimensionResource(R.dimen.padding_small)
//                start = dimensionResource(R.dimen.padding_medium),
//                top = dimensionResource(R.dimen.padding_medium),
//                end = dimensionResource(R.dimen.padding_medium),

            )
    )
}

@Composable
fun AddExerciseButtonHomeScreen(modifier: Modifier = Modifier) {
    Card(
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
//            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
//            .align(Alignment.Start)
//            .clickable {  }
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_small))
        ){
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
//                    .padding(dimensionResource(R.dimen.padding_small))
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
//                fontSize =
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


//@Composable
//fun WorkoutHistoryCard(modifier: Modifier = Modifier) {
//    Card(
//        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
//        modifier = modifier
//            .fillMaxWidth()
//    ) {
//        Column(
////            verticalArrangement = Arrangement.Top,
////            horizontalAlignment = Alignment.Start,
//            modifier = Modifier
//                .padding(dimensionResource(R.dimen.padding_medium))
//        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
////                horizontalArrangement = Arrangement.,
////                modifier = Modifier
////                    .padding(dimensionResource(R.dimen.padding_medium))
//            ) {
//                Icon(
//                    painter = painterResource(R.drawable.icon_history),
//                    contentDescription = null,
//                    tint =  MaterialTheme.colorScheme.primary,
//                    modifier = Modifier
//                        .size(30.dp)
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                Text(
//                    text = stringResource(R.string.workout_history),
//                    style = MaterialTheme.typography.titleMedium,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 18.sp
//                )
//            }
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = stringResource(R.string.today_label),
//                style = MaterialTheme.typography.titleMedium,
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween,
//            ) {
//                Text(
//                    text = stringResource(R.string.exercise_name_1),
//                    modifier = Modifier
//                        .weight(1.5f)
//                )
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Center,
//                    modifier = Modifier
//                        .weight(1f)
//                ) {
//                    Image(
//                        painter = painterResource(R.drawable.icon_image_timer_48),
//                        contentDescription = null,
//                        modifier = Modifier
//
//                    )
//                    Spacer(modifier = Modifier.width(6.dp))
//                    Text(
//                        text = stringResource(R.string.time, "30"),
//                        modifier = Modifier
//
//                    )
//                }
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.End,
//                    modifier = Modifier
//                        .weight(1f)
//                ) {
//                    Image(
//                        painter = painterResource(R.drawable.icon_image_dumbbell_48),
//                        contentDescription = null,
//                        modifier = Modifier
//                            .rotate(-45f)
//                    )
//                    Spacer(modifier = Modifier.width(6.dp))
//                    Text(
//                        text = stringResource(R.string.weight, "30"),
//                        modifier = Modifier
//                    )
//                }
//            }
//            Spacer(modifier = Modifier.height(4.dp))
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween,
//            ) {
//                Text(
//                    text = stringResource(R.string.exercise_name_2),
//                    modifier = Modifier
//                        .weight(1.5f)
//                )
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Center,
//                    modifier = Modifier
//                        .weight(1f)
//                ) {
//                    Image(
//                        painter = painterResource(R.drawable.icon_image_timer_48),
//                        contentDescription = null,
//                        modifier = Modifier
//
//                    )
//                    Spacer(modifier = Modifier.width(6.dp))
//                    Text(
//                        text = stringResource(R.string.time, "45"),
//                        modifier = Modifier
//
//                    )
//                }
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.End,
//                    modifier = Modifier
//                        .weight(1f)
//                ) {
//                    Image(
//                        painter = painterResource(R.drawable.icon_image_journey_48),
//                        contentDescription = null,
//                        modifier = Modifier
////                            .rotate(-45f)
//                    )
//                    Spacer(modifier = Modifier.width(6.dp))
//                    Text(
//                        text = stringResource(R.string.distance, "5"),
//                        modifier = Modifier
//                    )
//                }
//            }
//            Spacer(modifier = Modifier.height(4.dp))
//            HorizontalDivider(
//                thickness = 1.dp,
//                color = Color.Black
//            )
//            Spacer(modifier = Modifier.height(4.dp))
//
//            Text(
//                text = stringResource(R.string.last_day_label),
//                style = MaterialTheme.typography.titleMedium,
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween,
//            ) {
//                Text(
//                    text = stringResource(R.string.exercise_name_3),
//                    modifier = Modifier
//                        .weight(1.5f)
//                )
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Center,
//                    modifier = Modifier
//                        .weight(1f)
//                ) {
//                    Image(
//                        painter = painterResource(R.drawable.icon_image_timer_48),
//                        contentDescription = null,
//                        modifier = Modifier
//
//                    )
//                    Spacer(modifier = Modifier.width(6.dp))
//                    Text(
//                        text = stringResource(R.string.time, "30"),
//                        modifier = Modifier
//
//                    )
//                }
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.End,
//                    modifier = Modifier
//                        .weight(1f)
//                ) {
//                    Image(
//                        painter = painterResource(R.drawable.icon_image_step_48),
//                        contentDescription = null,
//                        modifier = Modifier
//                            .rotate(45f)
//                    )
//                    Spacer(modifier = Modifier.width(6.dp))
//                    Text(
//                        text = stringResource(R.string.distance, "2"),
//                        modifier = Modifier
//                    )
//                }
//            }
//        }
//    }
//}

//@Composable
//fun AchievementsCardHomeScreen(modifier: Modifier = Modifier) {
//    Card(
//        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
//        modifier = modifier
//            .fillMaxWidth()
//    ) {
//        Column(
//            verticalArrangement = Arrangement.Top,
//            horizontalAlignment = Alignment.Start,
//            modifier = Modifier
//                .padding(dimensionResource(R.dimen.padding_medium))
//        ) {
//            Text(
//                text = stringResource(R.string.achievements_label),
//                style = MaterialTheme.typography.titleMedium,
//                fontWeight = FontWeight.Bold,
//                fontSize = 18.sp
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Row() {
//                Text(
//                    text = stringResource(R.string.achievement_title_most_time_week),
//                    style = MaterialTheme.typography.bodyMedium,
//                    textAlign = TextAlign.Start
//                    )
//                Text(
//                    text = "5h 45min",
//                    style = MaterialTheme.typography.bodyMedium,
//                    textAlign = TextAlign.End,
//                    modifier = Modifier
//                        .weight(1f)
//                )
//            }
//            Spacer(modifier = Modifier.height(6.dp))
//            Row() {
//                Text(
//                    text = stringResource(R.string.achievement_title_most_time_week),
//                    style = MaterialTheme.typography.bodyMedium,
//                    textAlign = TextAlign.Start
//                )
//                Text(
//                    text = "5h 45min",
//                    style = MaterialTheme.typography.bodyMedium,
//                    textAlign = TextAlign.End,
//                    modifier = Modifier
//                        .weight(1f)
//                )
//            }
//            Spacer(modifier = Modifier.height(6.dp))
//            Row() {
//                Text(
//                    text = stringResource(R.string.achievement_title_most_time_week),
//                    style = MaterialTheme.typography.bodyMedium,
//                    textAlign = TextAlign.Start
//                )
//                Text(
//                    text = "5h 45min",
//                    style = MaterialTheme.typography.bodyMedium,
//                    textAlign = TextAlign.End,
//                    modifier = Modifier
//                        .weight(1f)
//                )
//            }
//
//        }
//    }
//}


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