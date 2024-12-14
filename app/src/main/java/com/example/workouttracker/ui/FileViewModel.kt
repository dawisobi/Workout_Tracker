package com.example.workouttracker.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workouttracker.data.repository.FileDownloadRepository
import kotlinx.coroutines.launch
import java.io.File

class FileViewModel(private val fileDownloadRepository: FileDownloadRepository) : ViewModel() {

    private val _fileDownloadStatus = MutableLiveData<Boolean>()
    val fileDownloadStatus: LiveData<Boolean> get() = _fileDownloadStatus

    // Function to download the file
    fun downloadFile(url: String, destination: File) {
        viewModelScope.launch {
            val success = fileDownloadRepository.downloadFile(url, destination)
            _fileDownloadStatus.postValue(success)
        }
    }
}