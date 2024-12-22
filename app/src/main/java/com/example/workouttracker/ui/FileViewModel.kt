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


    // Function to download the file
    fun downloadFile(url: String, destination: File) {
        viewModelScope.launch {
            val success = fileDownloadRepository.downloadFile(url, destination)
            _fileDownloadStatus.postValue(success)
        }
    }
}


//class FileViewModel(
//    private val fileDownloadRepository: FileDownloadRepository,
//    application: Application
//) : ViewModel() {
//
//    private val _fileDownloadStatus = MutableLiveData<Boolean>()
//    val fileDownloadStatus: LiveData<Boolean> get() = _fileDownloadStatus
//
//
//    init {
//        val destinationFile = File(getDatabasePath("exercise_database").parentFile, "exercise_database")
//        val fileUrl =
//            "https://www.dropbox.com/scl/fi/fsbkrvslzei9z0j931hqk/exercise_database1.db?rlkey=6upmlu21idup13pzn6jgo5w7b&st=3rgj82uo&dl=1"
//
//        if (destinationFile.exists()) {
//            // File already exists, no need to download
//            Toast.makeText(LocalContext.current, "File already exists", Toast.LENGTH_SHORT).show()
//        } else {
////            fileViewModel.downloadFile(fileUrl, destinationFile)
//            downloadFile(fileUrl, destinationFile)
//            fileViewModel.fileDownloadStatus.observe(this) { success ->
//                if (success) {
//                    // File downloaded successfully
//                    Toast.makeText(this, "File downloaded successfully", Toast.LENGTH_SHORT).show()
//                } else {
//                    // File download failed
//                    Toast.makeText(this, "File download failed", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
//
//
//    // Function to download the file
//    fun downloadFile(url: String, destination: File) {
//        viewModelScope.launch {
//            val success = fileDownloadRepository.downloadFile(url, destination)
//            _fileDownloadStatus.postValue(success)
//        }
//    }
//}

