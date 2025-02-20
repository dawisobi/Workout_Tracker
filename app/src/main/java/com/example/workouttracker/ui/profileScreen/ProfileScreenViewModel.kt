package com.example.workouttracker.ui.profileScreen

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workouttracker.data.database.UserWeightDatabase
import com.example.workouttracker.data.model.UserWeightData
import com.example.workouttracker.data.repository.UserWeightRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class ProfileScreenViewModel(context: Context) : ViewModel() {

    private val userWeightRepository = UserWeightRepository(UserWeightDatabase.getDatabase(context).userWeightDataDao())

    // current user weight from database
    val currentUserWeight: StateFlow<UserWeightData> = userWeightRepository.getRecentUserWeightData().stateIn(viewModelScope, SharingStarted.Eagerly, UserWeightData(0, "", 0.0))

    // user weight from database
    private val _userWeightData = MutableStateFlow<List<UserWeightData>>(emptyList())
    val userWeightData = _userWeightData.asStateFlow()

    // UI State for Profile screen and EditUserDetails dialog
    private val _uiState = MutableStateFlow(ProfileScreenUiState())
    val uiState: StateFlow<ProfileScreenUiState> = _uiState.asStateFlow()

    var weightInput by mutableStateOf(_uiState.value.userWeight)
        private set
    var heightInput by mutableStateOf(_uiState.value.userHeight)
        private set


    fun updateUserDetails(weight: String, height: String) {
        Log.d("ProfileScreenViewModel", "updateUserDetails called with weight: $weight and height: $height")
        updateWeight(weight)
        updateHeight(height)
        updateBmi()
    }

    fun updateUserDetailsInput(weight: String, height: String) {
        updateWeightInput(weight)
        updateHeightInput(height)
    }

    fun updateWeightInput(input: String) {
        weightInput = input
    }

    fun updateHeightInput(input: String) {
        heightInput = input
    }

    fun saveUserDetails() {
        _uiState.value = _uiState.value.copy(
            userWeight = weightInput,
            userHeight = heightInput,
            showUserDetailsDialog = false
        )
        updateBmi()
    }

    private fun updateWeight(weight: String) {
        _uiState.value = _uiState.value.copy(userWeight = weight)
    }

    private fun updateHeight(height: String) {
        _uiState.value = _uiState.value.copy(userHeight = height)
    }

    private fun updateBmi() {
        val weight = _uiState.value.userWeight.toDoubleOrNull() ?: 0.0
        val height = _uiState.value.userHeight.toDoubleOrNull() ?: 0.0
        val bmi = weight / ((height / 100) * (height / 100))
        val truncatedBmi = "%.2f".format(bmi)
        _uiState.value = _uiState.value.copy(userBmi = truncatedBmi)
    }

    fun hideUserDetailsDialog() {
        _uiState.value = _uiState.value.copy(showUserDetailsDialog = false)
    }

    fun showUserDetailsDialog() {
        _uiState.value = _uiState.value.copy(showUserDetailsDialog = true)
    }


    fun getAllUserWeightData() {
        viewModelScope.launch {
            userWeightRepository.getAllUserWeightData().collect { list ->
                _userWeightData.value = list
            }
        }
    }

    fun insertUserWeightData() {
        val userWeightToInsert = UserWeightData(date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now()).toString(), userWeight = _uiState.value.userWeight.toDouble())
        viewModelScope.launch {
            userWeightRepository.insertUserWeightData(userWeightToInsert)
        }
    }
}