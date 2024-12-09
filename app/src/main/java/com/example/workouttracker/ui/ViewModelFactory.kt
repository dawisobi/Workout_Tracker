package com.example.workouttracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.workouttracker.data.repository.FileRepository

class ViewModelFactory(
    private val fileRepository: FileRepository,
//    private val exerciseRepository: ExerciseRepository
) : ViewModelProvider.Factory {

//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//
//        return when {
//            modelClass.isAssignableFrom(FileViewModel::class.java) -> {
//                FileViewModel(fileRepository) as T
//            }
//            modelClass.isAssignableFrom(ExerciseViewModel::class.java) -> {
//                ExerciseViewModel(exerciseRepository) as T
//            }
//            else -> throw IllegalArgumentException("Unknown ViewModel class")
//        }
//    }
//}

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FileViewModel::class.java)) {
                return FileViewModel(fileRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }