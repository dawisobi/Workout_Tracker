package com.example.workouttracker

import com.example.workouttracker.data.model.SetDetails
import com.example.workouttracker.ui.exerciseDetailsDialog.ExerciseDetailsViewModel
import org.junit.Assert.*
import org.junit.Test

class ExerciseDetailsUnitTests {

    private val exerciseDetailsViewModel = ExerciseDetailsViewModel()


    // Verify conversion of details of SetDetails objects list to string format
    @Test
    fun verifyConvertRepsToString() {
        val setsDetailsList = listOf(
            SetDetails(5, 20.0),
            SetDetails(10, 42.5),
            SetDetails(15, 60.25)
        )

        val expectedString = "5,10,15"
        val actualString = exerciseDetailsViewModel.convertRepsToString(setsDetailsList)

        assertEquals(expectedString, actualString)
    }

    @Test
    fun verifyConvertWeightsToString() {
        val setsDetailsList = listOf(
            SetDetails(5, 20.0),
            SetDetails(10, 42.5),
            SetDetails(15, 60.25)
        )

        val expectedString = "20.0,42.5,60.25"
        val actualString = exerciseDetailsViewModel.convertWeightToString(setsDetailsList)

        assertEquals(expectedString, actualString)
    }

}