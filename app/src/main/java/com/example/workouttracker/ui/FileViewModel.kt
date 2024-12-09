package com.example.workouttracker.ui

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workouttracker.data.datasource.ExercisesDatabase.exerciseDb
import com.example.workouttracker.data.model.Exercise
import com.example.workouttracker.data.repository.FileRepository
import kotlinx.coroutines.launch
import java.io.File

class FileViewModel(private val fileRepository: FileRepository) : ViewModel() {

    private val _fileDownloadStatus = MutableLiveData<Boolean>()
    val fileDownloadStatus: LiveData<Boolean> get() = _fileDownloadStatus

    // Function to download the file
    fun downloadFile(url: String, destination: File) {
        viewModelScope.launch {
            val success = fileRepository.downloadFile(url, destination)
            _fileDownloadStatus.postValue(success)
        }
    }

    fun getDataFromDatabase(context: Context) {

        try{
            val exercisesList = exerciseDb

//            val exercisesList = mutableListOf<Exercise>()
            val databasePath = File(context.filesDir, "ExercisesDatabase.db").absolutePath
//            Log.d("WorkoutTrackerViewModel", "getDataFromDatabase() filesDir = ${context.filesDir}")
            Log.d("WorkoutTrackerViewModel", "getDataFromDatabase() databasePath: $databasePath")
//            Log.d("WorkoutTrackerViewModel", "getDataFromDatabase() databasePath exists: ${File(databasePath).exists()} with size: ${File(databasePath).length()}")

            val db = SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READONLY)

            val cursor = db.rawQuery("SELECT * FROM Exercises", null)

            while (cursor.moveToNext()) {
                val id = cursor.getInt(0)
                val type = cursor.getString(1)
                val muscle = cursor.getString(2)
                val name = cursor.getString(3)
                val description = cursor.getString(4)

//                exerciseDb.add(Exercise(id, type, muscle, name, description))

                exercisesList.add(Exercise(id, type, muscle, name, description))
//                Log.d("WorkoutTrackerViewModel", "getDataFromDatabase() exerciseList size: ${exercisesList.size} content: $exercisesList")
//                Log.d("WorkoutTrackerViewModel", "getDataFromDatabase() exerciseDb size: ${exerciseDb.size}")
//                Log.d("WorkoutTrackerViewModel", "getDataFromDatabase() exerciseDb: $exerciseDb")
//                Log.d("WorkoutTrackerViewModel", "getDataFromDatabase() added exercise: $id - $type - $muscle - $name")
            }

            cursor.close()
            db.close()
        } catch (e: Exception) {
            Log.e("WorkoutTrackerViewModel", "getDataFromDatabase() error: ${e.message}")

        }
    }
}