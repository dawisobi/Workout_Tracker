package com.example.workouttracker.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.workouttracker.data.repository.ExerciseRepository
import com.example.workouttracker.data.repository.FileDownloadRepository
import com.example.workouttracker.data.repository.TrainingSessionsRepository
import com.example.workouttracker.ui.exerciseListDialog.ExerciseViewModel

//class ViewModelFactory(
//    private val fileRepository: FileRepository,
////    private val exerciseRepository: ExerciseRepository
//) : ViewModelProvider.Factory {
//
////    @Suppress("UNCHECKED_CAST")
////    override fun <T : ViewModel> create(modelClass: Class<T>): T {
////
////        return when {
////            modelClass.isAssignableFrom(FileViewModel::class.java) -> {
////                FileViewModel(fileRepository) as T
////            }
////            modelClass.isAssignableFrom(ExerciseViewModel::class.java) -> {
////                ExerciseViewModel(exerciseRepository) as T
////            }
////            else -> throw IllegalArgumentException("Unknown ViewModel class")
////        }
////    }
////}
//
//        @Suppress("UNCHECKED_CAST")
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            if (modelClass.isAssignableFrom(FileViewModel::class.java)) {
//                return FileViewModel(fileRepository) as T
//            }
//            throw IllegalArgumentException("Unknown ViewModel class")
//        }
//    }


class ViewModelFactory(
    private val fileDownloadRepository: FileDownloadRepository,
    private val exerciseRepository: ExerciseRepository,
    private val trainingSessionRepository: TrainingSessionsRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FileViewModel::class.java) -> {
                Log.d("ViewModelFactory", "Creating FileViewModel instance...")
                FileViewModel(fileDownloadRepository) as T
            }
            modelClass.isAssignableFrom(ExerciseViewModel::class.java) -> {
                Log.d("ViewModelFactory", "Creating ExerciseViewModel instance...")
                ExerciseViewModel(exerciseRepository) as T
            }
            modelClass.isAssignableFrom(TrainingSessionViewModel::class.java) -> {
                Log.d("ViewModelFactory", "Creating TrainingSessionViewModel instance...")
                TrainingSessionViewModel(trainingSessionRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}