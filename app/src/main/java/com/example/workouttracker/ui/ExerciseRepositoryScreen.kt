package com.example.workouttracker.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.workouttracker.data.model.Exercise

@Composable
fun ExerciseRepositoryScreen(
    exerciseList: List<Exercise>,
    onScreenLoad: () -> Unit,
    onExerciseSelected: (Exercise) -> Unit,
    modifier: Modifier = Modifier,
) {

    LaunchedEffect(Unit) {
        onScreenLoad()
    }

    if(exerciseList.isEmpty()){
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

        Column(
            modifier = modifier
        ) {
            Text(
                text = "Exercises Database",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp)
            )

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(exerciseList) { exercise ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onExerciseSelected(exercise) }
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
}