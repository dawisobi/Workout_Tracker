package com.example.workouttracker

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workouttracker.ui.calendarScreen.CalendarScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


@RunWith(AndroidJUnit4::class)
class CalendarScreenLayoutTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
//            CalendarScreen()
        }
    }

    @Test
    fun calendarScreen_selectFirstDayOfMonth() {
        val expectedDate = LocalDate.now().withDayOfMonth(1)
        val formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM", Locale.getDefault())

//        composeTestRule.onNodeWithText(expectedDate.dayOfMonth.toString(), useUnmergedTree = true).performClick()
        selectDayOfMonth(expectedDate)
        composeTestRule.onNodeWithText(expectedDate.format(formatter), useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun calendarScreen_selectLastDayOfMonth() {
        val expectedDate = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth())
        val formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM", Locale.getDefault())

//        composeTestRule.onNodeWithText(expectedDate.dayOfMonth.toString(), useUnmergedTree = true).performClick()
        selectDayOfMonth(expectedDate)
        composeTestRule.onNodeWithText(expectedDate.format(formatter), useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun calendarScreen_pressPreviousMonthButton() {
        val currentMonth = LocalDate.now().monthValue
        val expectedMonth = if (currentMonth == 1) 12 else currentMonth - 1

        navigateToPreviousMonth()
        composeTestRule.onNodeWithText(expectedMonth.toString(), useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun calendarScreen_pressNextMonthButton() {
        val currentMonth = LocalDate.now().monthValue
        val expectedMonth = if (currentMonth == 12) 1 else currentMonth + 1

        navigateToNextMonth()
        composeTestRule.onNodeWithText(expectedMonth.toString(), useUnmergedTree = true).assertIsDisplayed()
    }

//    Test to verify the invalid date exception
    @Test
    fun calendarScreen_selectLastDayOfMonth_cycleThroughMonths() {
        val lastDayOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth())

//        composeTestRule.onNodeWithText(lastDayOfMonth.dayOfMonth.toString(), useUnmergedTree = true).performClick()
        selectDayOfMonth(lastDayOfMonth)
        repeat(12 ) { navigateToNextMonth() }

        composeTestRule.onNodeWithText(lastDayOfMonth.monthValue.toString(), useUnmergedTree = true).assertIsDisplayed()
    }

    private fun selectDayOfMonth(expectedDate: LocalDate) {
        composeTestRule.onNodeWithText(expectedDate.dayOfMonth.toString(), useUnmergedTree = true).performClick()
    }


    private fun navigateToNextMonth() {
        composeTestRule.onNodeWithContentDescription("Next Month", useUnmergedTree = true).performClick()
    }

    private fun navigateToPreviousMonth() {
        composeTestRule.onNodeWithContentDescription("Previous Month", useUnmergedTree = true).performClick()
    }
}