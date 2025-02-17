package com.example.workouttracker.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.workouttracker.data.model.Exercise

@Composable
fun ExerciseDetailsScreen_Repo(
    exerciseToDisplay: Exercise,
    onCloseButtonClicked: () -> Unit,
    contentModifier: Modifier
) {
    Column(
        modifier = contentModifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp)
        ) {
            Text(
                text = exerciseToDisplay.name,//"Exercise Details",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
        }


        if (exerciseToDisplay.type == "Gym")
            Text(
                text = "Muscle: ${exerciseToDisplay.muscle}",
                style = MaterialTheme.typography.titleMedium
            )
        else if (exerciseToDisplay.type == "Athletics")
            Text(
                text = "Type: ${exerciseToDisplay.type}",
                style = MaterialTheme.typography.titleMedium
            )

        if(exerciseToDisplay.description != null)
            Text(
                text = "Description:",
                style = MaterialTheme.typography.titleMedium,
            )

        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                exerciseToDisplay.description?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }


    }
}