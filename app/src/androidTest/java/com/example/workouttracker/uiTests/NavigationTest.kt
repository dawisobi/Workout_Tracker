package com.example.workouttracker.uiTests

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.workouttracker.WorkoutTrackerApp
import com.example.workouttracker.WorkoutTrackerScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupWorkoutTrackerNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            WorkoutTrackerApp(navController = navController)
        }
    }


    @Test
    fun verifyStartDestination() {
        navController.assertCurrentRouteName(WorkoutTrackerScreen.Home.name)
    }

    @Test
    fun workoutTrackerNavHost_navigateToCalendarScreen() {
        navigateToCalendarScreen()
        navController.assertCurrentRouteName(WorkoutTrackerScreen.Calendar.name)
    }

    @Test
    fun workoutTrackerNavHost_navigateToExerciseListScreen() {
        navigateToExerciseListScreen()
        navController.assertCurrentRouteName(WorkoutTrackerScreen.ExerciseList.name)
    }

    @Test
    fun workoutTrackerNavHost_navigateToProfileScreen() {
        navigateToProfileScreen()
        navController.assertCurrentRouteName(WorkoutTrackerScreen.Profile.name)
    }

    @Test
    fun workoutTrackerNavHost_navigateToHomeScreen() {
        navigateToProfileScreen()
        navigateToHomeScreen()
        navController.assertCurrentRouteName(WorkoutTrackerScreen.Home.name)
    }

    @Test
    fun openExercisesListWithFab() {
        navigateToExerciseListScreenWithFab()
        navController.assertCurrentRouteName("SelectExerciseScreen")
    }

    private fun navigateToHomeScreen() {
        composeTestRule.onNodeWithContentDescription("Home", useUnmergedTree = true).performClick()
    }

    private fun navigateToCalendarScreen() {
        composeTestRule.onNodeWithContentDescription("Calendar", useUnmergedTree = true).performClick()
    }

    private fun navigateToExerciseListScreen() {
        composeTestRule.onNodeWithContentDescription("Exercises", useUnmergedTree = true).performClick()
    }

    private fun navigateToProfileScreen() {
        composeTestRule.onNodeWithContentDescription("Profile", useUnmergedTree = true).performClick()
    }

    private fun navigateToExerciseListScreenWithFab() {
        composeTestRule.onNodeWithContentDescription("Small floating action button.", useUnmergedTree = true).performClick()
    }

}