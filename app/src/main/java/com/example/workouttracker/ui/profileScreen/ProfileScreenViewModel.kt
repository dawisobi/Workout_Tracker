package com.example.workouttracker.ui.profileScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileScreenUiState())
    val uiState: StateFlow<ProfileScreenUiState> = _uiState.asStateFlow()


    fun updateWeight(weight: String) {
        _uiState.value = _uiState.value.copy(weight = weight)

        updateBmi()
    }

    fun updateHeight(height: String) {
        _uiState.value = _uiState.value.copy(height = height)
    }

    private fun updateBmi() {
        val weight = _uiState.value.weight.toDoubleOrNull() ?: 0.0
        val height = _uiState.value.height.toDoubleOrNull() ?: 0.0
        val bmi = weight / (height * height)

        _uiState.value = _uiState.value.copy(bmi = bmi.toString())
    }


}