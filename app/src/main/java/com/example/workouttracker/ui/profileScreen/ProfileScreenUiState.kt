package com.example.workouttracker.ui.profileScreen

data class ProfileScreenUiState(
    val userWeight: String = "10",
    val userHeight: String = "10",
    val userBmi: String = "0",

    val showUserDetailsDialog: Boolean = false,
)
