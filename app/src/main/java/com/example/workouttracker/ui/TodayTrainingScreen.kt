package com.example.workouttracker.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.workouttracker.R
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme

@Composable
fun TodayTrainingScreen(
    modifier: Modifier = Modifier
) {

}




@Preview(showBackground = true)
@Composable
fun TodayTrainingScreenPreview() {
    WorkoutTrackerTheme(dynamicColor = false) {
        TodayTrainingScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}