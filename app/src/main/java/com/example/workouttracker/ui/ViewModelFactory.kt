package com.example.workouttracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.workouttracker.model.FileRepository

class ViewModelFactory(private val repository: FileRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FileViewModel::class.java)) {
            return FileViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}