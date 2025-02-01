package com.example.workouttracker.ui.profileScreen

data class ProfileScreenUiState(
    val userWeight: String = "",
    val userHeight: String = "",
    val userBmi: String = "",

    val showUserDetailsDialog: Boolean = false,
)
