package com.example.workouttracker

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.workouttracker.data.database.ExerciseDatabase
import com.example.workouttracker.data.model.FileRepository
import com.example.workouttracker.data.repository.ExerciseRepository
import com.example.workouttracker.ui.FileViewModel
import com.example.workouttracker.ui.ViewModelFactory
import com.example.workouttracker.ui.exerciseList.ExerciseViewModel
import com.example.workouttracker.ui.exerciseList.ExerciseViewModelFactory
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme
import java.io.File

class MainActivity : ComponentActivity() {

//    private val fileRepository = FileRepository()
////    private val exerciseRepository = ExerciseRepository(ExerciseDatabase.getDatabase(this).exerciseDao())
//
//    private val viewModel: FileViewModel by viewModels {
//        ViewModelFactory(fileRepository)
//    }

//    private val viewModel: FileViewModel by viewModels {
//        ViewModelFactory(fileRepository, exerciseRepository)
//    }

//    private val exerciseViewModel: ExerciseViewModel by viewModels {
//        ViewModelFactory(fileRepository, exerciseRepository)
//    }

    private val viewModel: FileViewModel by viewModels {
        ViewModelFactory(FileRepository())
    }
//    val exerciseListViewModel: ExerciseViewModel by viewModels {
//        ExerciseViewModelFactory(ExerciseRepository(ExerciseDatabase.getDatabase(LocalContext.current).exerciseDao()))
//    }

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

                // Populating
                viewModel.getDataFromDatabase(this)
            } else {
                // File download failed
                Toast.makeText(this, "File download failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}