package com.example.workouttracker.ui.exerciseDetails

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import com.example.workouttracker.data.model.SetDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
class ExerciseDetailsViewModel {

    private val _uiState = MutableStateFlow(ExerciseDetailsUiState())
    val uiState: StateFlow<ExerciseDetailsUiState> = _uiState.asStateFlow()


    var setsRepsList = mutableStateListOf<String>("0")
        private set
    var setsWeightList = mutableStateListOf<String>("0")
        private set

    fun resetExerciseDetails() {
        // Clear the list
        setsRepsList.clear()
        setsWeightList.clear()

        // Add a default value
        setsRepsList.add("0")
        setsWeightList.add("0")
    }

    fun updateCurrentDateTime() {
        _uiState.update { currentState -> currentState.copy(currentDateTime = LocalDateTime.now()) }
    }

    fun getSetCount(): Int {
        Log.d("ExerciseDetailsViewModel", "getSetCount called, returning: ${_uiState.value.setsCount}")
        return _uiState.value.setsCount
    }

    fun updateSetsCount(newSetsCount: Int) {
        _uiState.update { currentState -> currentState.copy(setsCount = newSetsCount) }
    }

    fun updateSpecificSetDetails(index: Int, newSetDetails: SetDetails) {
        _uiState.update { currentState -> currentState.copy(setsDetails = currentState.setsDetails.also { it[index] = newSetDetails }) }
    }

    fun updateSetsDetailsOnConfirm() {
        _uiState.value.setsDetails.forEachIndexed { index, setDetails ->
            updateSpecificSetDetails(index, SetDetails(setsRepsList[index].toInt(), setsWeightList[index].toDouble()))
        }
    }

    fun addSet() {
        _uiState.update { currentState ->
            currentState.copy(
                setsCount = currentState.setsCount + 1,
                setsDetails = currentState.setsDetails.also { it.add(SetDetails(0, 0.0)) }
            )
        }

        Log.d("ExerciseDetailsViewModel", "Add set button clicked")
        Log.d("ExerciseDetailsViewModel", "setsCount: ${_uiState.value.setsCount}, setsDetails: ${_uiState.value.setsDetails}")
    }

    fun removeLastSet() {
        _uiState.update { currentState ->
            currentState.copy(
                setsCount = currentState.setsCount - 1,
                setsDetails = currentState.setsDetails.also { it.removeLast() }
            )
        }

        Log.d("ExerciseDetailsViewModel", "Remove set button clicked")
        Log.d("ExerciseDetailsViewModel", "setsCount: ${_uiState.value.setsCount}, setsDetails: ${_uiState.value.setsDetails}")
    }




    fun convertRepsToString(setsDetailsList: List<SetDetails>): String {
        return setsDetailsList.joinToString(",") { it.repsCount.toString() }
    }

    fun convertWeightToString(setsDetailsList: List<SetDetails>): String {
        return setsDetailsList.joinToString(",") { it.weight.toString() }
    }
}