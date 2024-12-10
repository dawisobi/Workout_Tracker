package com.example.workouttracker

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.example.workouttracker.data.database.ExerciseDatabase
import com.example.workouttracker.data.database.PerformedTrainingSessionsDatabase
import com.example.workouttracker.data.repository.ExerciseRepository
import com.example.workouttracker.data.repository.FileDownloadRepository
import com.example.workouttracker.data.repository.TrainingSessionsRepository
import com.example.workouttracker.ui.FileViewModel
import com.example.workouttracker.ui.TrainingSessionViewModel
import com.example.workouttracker.ui.ViewModelFactory
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

//    private val viewModel: FileViewModel by viewModels {
//        ViewModelFactory(FileRepository())
//    }
//    val exerciseListViewModel: ExerciseViewModel by viewModels {
//        ExerciseViewModelFactory(ExerciseRepository(ExerciseDatabase.getDatabase(LocalContext.current).exerciseDao()))
//    }

//    private val factory = ViewModelFactory(FileRepository(), ExerciseRepository(ExerciseDatabase.getDatabase(this).exerciseDao()), TrainingSessionsRepository(PerformedTrainingSessionsDatabase.getDatabase(this).trainingSessionDao()))
//    private val fileViewModel = ViewModelProvider(this, factory)[FileViewModel::class.java]

    private val fileViewModel : FileViewModel by viewModels {
        ViewModelFactory(FileDownloadRepository(), ExerciseRepository(ExerciseDatabase.getDatabase(this).exerciseDao()), TrainingSessionsRepository(PerformedTrainingSessionsDatabase.getDatabase(this).trainingSessionDao()))
    }

    private val trainingSessionViewModel : TrainingSessionViewModel by viewModels {
        ViewModelFactory(FileDownloadRepository(), ExerciseRepository(ExerciseDatabase.getDatabase(this).exerciseDao()), TrainingSessionsRepository(PerformedTrainingSessionsDatabase.getDatabase(this).trainingSessionDao()))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WorkoutTrackerTheme(dynamicColor = false) {
                WorkoutTrackerApp(trainingSessionViewModel = trainingSessionViewModel)
            }
        }

//        val destinationFile = File(filesDir, "ExercisesDatabase.db")
        val destinationFile = File(getDatabasePath("ExercisesDatabase.db").parentFile, "ExercisesDatabase.db")
        val fileUrl =
            "https://www.dropbox.com/scl/fi/4n9mjq8bonqdyr5rhfm38/ExercisesDatabase.db?rlkey=vcsaj9xsricp8ynxmof2595sh&st=8ca6nihi&dl=1"

        fileViewModel.downloadFile(fileUrl, destinationFile)

        fileViewModel.fileDownloadStatus.observe(this) { success ->
            if (success) {
                // File downloaded successfully
                Toast.makeText(this, "File downloaded successfully", Toast.LENGTH_SHORT).show()

                // Populating
                fileViewModel.getDataFromDatabase( fileDestination = destinationFile )//this)
            } else {
                // File download failed
                Toast.makeText(this, "File download failed", Toast.LENGTH_SHORT).show()
            }
        }




    }
}