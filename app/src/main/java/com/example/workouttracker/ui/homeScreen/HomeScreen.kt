package com.example.workouttracker.ui.homeScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.workouttracker.R
import com.example.workouttracker.ui.PerformedExercisesDisplay
import com.example.workouttracker.ui.TrainingSessionViewModel
import com.example.workouttracker.ui.exerciseListDialog.ExerciseViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    modifier: Modifier,
    trainingSessionViewModel: TrainingSessionViewModel,
    exerciseListViewModel: ExerciseViewModel,
    onAddExerciseClick: () -> Unit
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
        TodayDateLabel()
        AddExerciseButtonHomeScreen(onAddExerciseClick = onAddExerciseClick)
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

        PerformedExercisesDisplay(
            trainingSessionViewModel = trainingSessionViewModel,
            exerciseListViewModel = exerciseListViewModel,
            dateToDisplay = LocalDate.now().toString()
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
fun AddExerciseButtonHomeScreen(onAddExerciseClick: () -> Unit) {
    Card(
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onAddExerciseClick() }
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
        ){
            Icon(
                painter = painterResource(R.drawable.rounded_add_circle_24),
                contentDescription = null,
                tint =  MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.add_exercise),
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}