package com.example.workouttracker.ui.profileScreen

data class ProfileScreenUiState(
    val userWeight: String = "0",
    val userHeight: String = "0",
    val userBmi: String = "0",

    val showUserDetailsDialog: Boolean = false,
)
