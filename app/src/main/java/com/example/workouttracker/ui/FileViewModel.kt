package com.example.workouttracker.ui

import android.app.Application
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
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

    var isFileChecked = false
        private set

    // Function to download the file
    fun downloadFile(url: String, destination: File) {
        viewModelScope.launch {
            val success = fileDownloadRepository.downloadFile(url, destination)
            _fileDownloadStatus.postValue(success)
//            isFileChecked = true
        }
    }


    fun updateIsFileCheckedFlag(value: Boolean) {
        isFileChecked = value
    }
}