package com.example.workouttracker

import com.example.workouttracker.ui.calendarScreen.getDate
import com.example.workouttracker.ui.calendarScreen.isTrainingDay
import com.example.workouttracker.ui.calendarScreen.processDateList
import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDate

class CalendarUnitTests {

    // Verify that the date is correctly set to the last day of the month if the input date is invalid
    // Example: switching months when 31st day is selected
    @Test
    fun calendarInvalidDate_isLastDaySet_NotLeapYear() {
        val actualDate = getDate(2025, 2, 30)
        assertEquals(getDate(2025, 2, 28), actualDate)
    }

    @Test
    fun calendarInvalidDate_isLastDaySet_LeapYear() {
        val actualDate = getDate(2024, 2, 30)
        assertEquals(getDate(2024, 2, 29), actualDate)
    }

    @Test
    fun calendarValidDate_isSelectedDateRemained() {
        val actualDate = getDate(2025, 3, 20)
        assertEquals(getDate(2025, 3, 20), actualDate)
    }

    // Verify if the function finds given date in set of training days
    @Test
    fun isTrainingDay_checkIfGivenDayIsTrainingDay_returnTrue() {
        val dateSet = setOf(
            LocalDate.of(2024, 2, 10),
            LocalDate.of(2024, 3, 15),
            LocalDate.of(2024, 5, 20)
        )

        val actualResult = isTrainingDay(2024, 2,10, dateSet)
        assertTrue(actualResult)
    }

    @Test
    fun isTrainingDay_checkIfGivenDayIsTrainingDay_returnFalse() {
        val dateSet = setOf(
            LocalDate.of(2024, 2, 10),
            LocalDate.of(2024, 3, 15),
            LocalDate.of(2024, 5, 20)
        )

        val actualDateWrongDay = isTrainingDay(2024, 3,12, dateSet)
        assertFalse(actualDateWrongDay)
        val actualDateWrongMonth = isTrainingDay(2024, 10,10, dateSet)
        assertFalse(actualDateWrongMonth)
        val actualDateWrongYear = isTrainingDay(2025, 5,20, dateSet)
        assertFalse(actualDateWrongYear)
    }

    // Verify processing the list of dates into a set of LocalDates
    @Test
    fun processDates_returnSetOfDates() {
        val stringDatesList = listOf("2024-09-28","2025-03-15","2026-05-20")
        val expectedSet = setOf(
            LocalDate.of(2024, 9, 28),
            LocalDate.of(2025, 3, 15),
            LocalDate.of(2026, 5, 20)
        )

        val actualSet = processDateList(stringDatesList)
        assertEquals(expectedSet.size, actualSet.size) // Verify size of the set
        assertEquals(expectedSet, actualSet) // Verify content of the set
    }
}