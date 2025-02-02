package com.example.workouttracker.ui.profileScreen

data class ProfileScreenUiState(
    val userWeight: String = "80",
    val userHeight: String = "178",
    val userBmi: String = "0",

    val showUserDetailsDialog: Boolean = false,
)
