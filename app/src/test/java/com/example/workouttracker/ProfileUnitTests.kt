package com.example.workouttracker

import com.example.workouttracker.ui.profileScreen.BmiSlices
import com.example.workouttracker.ui.profileScreen.getBmiLevel
import org.junit.Assert.*
import org.junit.Test

class ProfileUnitTests {

    private val bmiSlices = BmiSlices.slices

    @Test
    fun bmiLevelsLabels_Underweight_MinValue() {
        val expectedLabel = "Underweight"
        assertEquals(expectedLabel, getBmiLevel(9.9f, bmiSlices)) // Test with min value
        assertEquals(expectedLabel, getBmiLevel(0f, bmiSlices)) // Test with inside value
        assertEquals(expectedLabel, getBmiLevel(18.49f, bmiSlices)) // Test with max value
    }

    @Test
    fun bmiLevelsLabels_NormalWeight() {
        val expectedLabel = "Normal weight"
        assertEquals(expectedLabel, getBmiLevel(18.5f, bmiSlices)) // Test with min value
        assertEquals(expectedLabel, getBmiLevel(21.37f, bmiSlices)) // Test with inside value
        assertEquals(expectedLabel, getBmiLevel(24.99f, bmiSlices)) // Test with max value
    }

    @Test
    fun bmiLevelsLabels_Overweight() {
        val expectedLabel = "Overweight"
        assertEquals(expectedLabel, getBmiLevel(25.0f, bmiSlices)) // Test with min value
        assertEquals(expectedLabel, getBmiLevel(26.34f, bmiSlices)) // Test with inside value
        assertEquals(expectedLabel, getBmiLevel(29.99f, bmiSlices)) // Test with max value
    }

    @Test
    fun bmiLevelsLabels_ObesityI() {
        val expectedLabel = "Obesity I"
        assertEquals(expectedLabel, getBmiLevel(30f, bmiSlices)) // Test with min value
        assertEquals(expectedLabel, getBmiLevel(31.37f, bmiSlices)) // Test with inside value
        assertEquals(expectedLabel, getBmiLevel(34.99f, bmiSlices)) // Test with max value
    }

    @Test
    fun bmiLevelsLabels_ObesityII() {
        val expectedLabel = "Obesity II"
        assertEquals(expectedLabel, getBmiLevel(35f, bmiSlices)) // Test with min value
        assertEquals(expectedLabel, getBmiLevel(38.37f, bmiSlices)) // Test with inside value
        assertEquals(expectedLabel, getBmiLevel(39.99f, bmiSlices)) // Test with max value
    }

    @Test
    fun bmiLevelsLabels_ObesityIII() {
        val expectedLabel = "Obesity III"
        assertEquals(expectedLabel, getBmiLevel(40f, bmiSlices)) // Test with min value
        assertEquals(expectedLabel, getBmiLevel(51.37f, bmiSlices)) // Test with inside value
        assertEquals(expectedLabel, getBmiLevel(58.4f, bmiSlices)) // Test with max value
    }
}