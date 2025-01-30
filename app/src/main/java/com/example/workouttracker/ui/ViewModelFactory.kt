package com.example.workouttracker.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.workouttracker.data.repository.ExerciseRepository
import com.example.workouttracker.data.repository.FileDownloadRepository
import com.example.workouttracker.data.repository.TrainingSessionsRepository
import com.example.workouttracker.ui.exerciseListDialog.ExerciseViewModel

class ViewModelFactory(
    private val fileDownloadRepository: FileDownloadRepository,
    private val trainingSessionRepository: TrainingSessionsRepository,
) : ViewModelProvider.Factory {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FileViewModel::class.java) -> {
                Log.d("ViewModelFactory", "Creating FileViewModel instance...")
                FileViewModel(fileDownloadRepository) as T
            }
            modelClass.isAssignableFrom(TrainingSessionViewModel::class.java) -> {
                Log.d("ViewModelFactory", "Creating TrainingSessionViewModel instance...")
                TrainingSessionViewModel(trainingSessionRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}

class TrainingSessionViewModelFactory(
    private val trainingSessionRepository: TrainingSessionsRepository
) : ViewModelProvider.Factory {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrainingSessionViewModel::class.java)) {
            return TrainingSessionViewModel(trainingSessionRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

//class CalendarTrainingSessionViewModelFactory(
//    private val trainingSessionRepository: TrainingSessionsRepository
//) : ViewModelProvider.Factory {
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(CalendarTrainingSessionViewModel::class.java)) {
//            return CalendarTrainingSessionViewModel(trainingSessionRepository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}

class ExerciseViewModelFactory(
    private val exerciseRepository: ExerciseRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExerciseViewModel::class.java)) {
            return ExerciseViewModel(exerciseRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}