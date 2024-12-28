package com.example.workouttracker

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.example.workouttracker.data.database.ExerciseDatabase
import com.example.workouttracker.data.database.TrainingSessionsDatabase
import com.example.workouttracker.data.repository.ExerciseRepository
import com.example.workouttracker.data.repository.FileDownloadRepository
import com.example.workouttracker.data.repository.TrainingSessionsRepository
import com.example.workouttracker.ui.FileViewModel
import com.example.workouttracker.ui.TrainingSessionViewModel
import com.example.workouttracker.ui.ViewModelFactory
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme
import java.io.File

class MainActivity : ComponentActivity() {

    private val fileViewModel : FileViewModel by viewModels {
        Log.d("MainActivity", "FileViewModel created")
        ViewModelFactory(FileDownloadRepository(), TrainingSessionsRepository(TrainingSessionsDatabase.getDatabase(this).trainingSessionDao()))
    }

    private val trainingSessionViewModel : TrainingSessionViewModel by viewModels {
        Log.d("MainActivity", "TrainingSessionViewModel created")
        ViewModelFactory(FileDownloadRepository(), TrainingSessionsRepository(TrainingSessionsDatabase.getDatabase(this).trainingSessionDao()))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WorkoutTrackerTheme(dynamicColor = false) {
                WorkoutTrackerApp(trainingSessionViewModel = trainingSessionViewModel)
                Log.d("MainActivity", "onCreate() called")
            }
        }

        val destinationFile = File(getDatabasePath("exercise_database").parentFile, "exercise_database")
        val fileUrl =
            "https://www.dropbox.com/scl/fi/fsbkrvslzei9z0j931hqk/exercise_database1.db?rlkey=6upmlu21idup13pzn6jgo5w7b&st=3rgj82uo&dl=1"

        if(!fileViewModel.isFileChecked) {
            if (destinationFile.exists()) {
                // File already exists, no need to download
                Toast.makeText(this, "File already exists", Toast.LENGTH_SHORT).show()
                fileViewModel.updateIsFileCheckedFlag(true)
            } else {
                fileViewModel.downloadFile(fileUrl, destinationFile)

                fileViewModel.fileDownloadStatus.observe(this) { success ->
                    fileViewModel.updateIsFileCheckedFlag(true)
                    if (success) {
                        // File downloaded successfully
                        Toast.makeText(this, "File downloaded successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        // File download failed
                        Toast.makeText(this, "File download failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}