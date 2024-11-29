package com.example.workouttracker

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.example.workouttracker.model.FileRepository
import com.example.workouttracker.ui.FileViewModel
import com.example.workouttracker.ui.ViewModelFactory
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme
import java.io.File

class MainActivity : ComponentActivity() {

    private val viewModel: FileViewModel by viewModels {
        ViewModelFactory(FileRepository())
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WorkoutTrackerTheme(dynamicColor = false) {
                WorkoutTrackerApp()
            }
        }

        val destinationFile = File(filesDir, "ExercisesDatabase.db")
        val fileUrl =
            "https://www.dropbox.com/scl/fi/4n9mjq8bonqdyr5rhfm38/ExercisesDatabase.db?rlkey=vcsaj9xsricp8ynxmof2595sh&st=8ca6nihi&dl=1"

        viewModel.downloadFile(fileUrl, destinationFile)

        viewModel.fileDownloadStatus.observe(this) { success ->
            if (success) {
                // File downloaded successfully
                Toast.makeText(this, "File downloaded successfully", Toast.LENGTH_SHORT).show()

                // Testing the database creation and data
                viewModel.getDataFromDatabase(this)
            } else {
                // File download failed
                Toast.makeText(this, "File download failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}