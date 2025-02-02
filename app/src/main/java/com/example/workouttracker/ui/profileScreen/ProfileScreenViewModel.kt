package com.example.workouttracker.ui.profileScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileScreenUiState())
    val uiState: StateFlow<ProfileScreenUiState> = _uiState.asStateFlow()

    var weightInput by mutableStateOf(_uiState.value.userWeight)
        private set
    var heightInput by mutableStateOf(_uiState.value.userHeight)
        private set

    init {
        updateBmi()
    }

    fun updateUserDetails(weight: String, height: String) {
        updateWeight(weight)
        updateHeight(height)
        updateBmi()
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

    fun updateWeight(weight: String) {
        _uiState.value = _uiState.value.copy(userWeight = weight)
    }

    fun updateHeight(height: String) {
        _uiState.value = _uiState.value.copy(userWeight = height)
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

}