package com.example.workouttracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WorkoutTrackerTheme(dynamicColor = false) {
//                HomeScreen(
//                    modifier = Modifier
//                        .padding(dimensionResource(R.dimen.padding_medium))
//                        .fillMaxSize()
//                )
                WorkoutTrackerApp()
            }
        }
    }
}