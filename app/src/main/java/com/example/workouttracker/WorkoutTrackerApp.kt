package com.example.workouttracker

import android.os.Build
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.workouttracker.data.database.ExerciseDatabase
import com.example.workouttracker.data.database.TrainingSessionsDatabase
import com.example.workouttracker.data.repository.ExerciseRepository
import com.example.workouttracker.data.repository.TrainingSessionsRepository
import com.example.workouttracker.ui.ExerciseRepositoryScreen
import com.example.workouttracker.ui.ExerciseViewModelFactory
import com.example.workouttracker.ui.calendarScreen.CalendarScreen
import com.example.workouttracker.ui.homeScreen.HomeScreen
import com.example.workouttracker.ui.profileScreen.ProfileScreen
import com.example.workouttracker.ui.TrainingSessionViewModel
import com.example.workouttracker.ui.TrainingSessionViewModelFactory
import com.example.workouttracker.ui.WorkoutTrackerViewModel
import com.example.workouttracker.ui.exerciseListDialog.ExerciseViewModel
import com.example.workouttracker.ui.exerciseListDialog.SelectExerciseScreen

enum class WorkoutTrackerScreen(
    @StringRes val title: Int,
    @DrawableRes val icon: Int
) {
    Home(R.string.bottom_navBar_home, R.drawable.icon_home_fill),
    Calendar(R.string.bottom_navBar_calendar, R.drawable.icon_calendar_fill),
    ExerciseList(R.string.bottom_navBar_exercisesList, R.drawable.icon_dumbbell),
    Profile(R.string.bottom_navBar_profile, R.drawable.icon_profile_fill),
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun WorkoutTrackerApp() {
    val navController: NavHostController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryFlow.collectAsState(initial = navController.currentBackStackEntry)

    // Exercise List view model initialization
    val context = LocalContext.current

    val workoutTrackerViewModel: WorkoutTrackerViewModel = WorkoutTrackerViewModel()

    val exerciseDatabase = remember { ExerciseDatabase.getDatabase(context) }
    val exerciseRepository = remember { ExerciseRepository(exerciseDatabase.exerciseDao()) }
    val exerciseViewModelFactory = remember { ExerciseViewModelFactory(exerciseRepository) }
    val exerciseViewModel: ExerciseViewModel = viewModel(factory = exerciseViewModelFactory)

    val trainingSessionsDatabase = remember { TrainingSessionsDatabase.getDatabase(context) }
    val trainingSessionsRepository = remember { TrainingSessionsRepository(trainingSessionsDatabase.trainingSessionDao()) }
    val trainingSessionViewModelFactory = remember { TrainingSessionViewModelFactory(trainingSessionsRepository) }
    val trainingSessionViewModel: TrainingSessionViewModel = viewModel(factory = trainingSessionViewModelFactory)

//    val homeTrainingSessionViewModel: TrainingSessionViewModel = viewModel(factory = trainingSessionViewModelFactory, key = "HomeScreen")
//    val calendarTrainingSessionViewModel: TrainingSessionViewModel = viewModel(factory = trainingSessionViewModelFactory, key = "CalendarScreen")

    val screensContentModifier = Modifier.fillMaxSize().padding(dimensionResource(R.dimen.padding_medium))


    Scaffold(
        bottomBar = {
            if(currentRoute.value?.destination?.route != "SelectExerciseScreen")
                BottomNavigationBar(navController, currentRoute)
                    },
        floatingActionButton = {
            if(currentRoute.value?.destination?.route == WorkoutTrackerScreen.Home.name
                || currentRoute.value?.destination?.route == WorkoutTrackerScreen.Calendar.name) {
                ActionButton(
                    onClick = {
                        Log.d("FAB", "FAB clicked")
                        workoutTrackerViewModel.resetSearchedExercise()
                        navController.navigate("SelectExerciseScreen") {
                            launchSingleTop = true
                        }
                    },
                )
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = WorkoutTrackerScreen.Home.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = WorkoutTrackerScreen.Home.name) {
                Log.d("HomeScreen", "Launching the HomeScreen from NavHost")
                HomeScreen(
                    //workoutTrackerViewModel = workoutTrackerViewModel,
//                    trainingSessionViewModel = homeTrainingSessionViewModel,
                    trainingSessionViewModel = trainingSessionViewModel,
                    exerciseListViewModel = exerciseViewModel,
                    modifier = screensContentModifier
                )
            }
            composable(route = WorkoutTrackerScreen.Calendar.name) {
                Log.d("CalendarScreen", "Launching the CalendarScreen from NavHost")
                CalendarScreen(
                    //workoutTrackerViewModel = workoutTrackerViewModel,
//                    trainingSessionViewModel = calendarTrainingSessionViewModel,
                    trainingSessionViewModel = trainingSessionViewModel,
                    exerciseListViewModel = exerciseViewModel,
                    modifier = screensContentModifier,
                )
            }
            composable(route = WorkoutTrackerScreen.Profile.name) {
                Log.d("ProfileScreen", "Launching the ProfileScreen from NavHost")
                ProfileScreen(modifier = screensContentModifier)
            }
            composable(route = "SelectExerciseScreen") {
                Log.d("SelectExerciseScreen", "Launching the SelectExerciseScreen from NavHost")
                SelectExerciseScreen(
                    onDismiss = { navController.navigateUp() },
                    exerciseListViewModel = exerciseViewModel,
                    workoutTrackerViewModel = workoutTrackerViewModel,
                    trainingSessionViewModel = trainingSessionViewModel,
                    modifier = screensContentModifier
                )
            }
            composable(route = WorkoutTrackerScreen.ExerciseList.name) {
                Log.d("ExerciseListScreen", "Launching the ExerciseListScreen from NavHost")
                ExerciseRepositoryScreen(modifier = screensContentModifier)
            }
        }
    }
}

@Composable
fun ActionButton(
    onClick: () -> Unit,
) {
    FloatingActionButton(
        onClick = { onClick() },
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.secondary
    ) {
        Icon(Icons.Filled.Add, "Small floating action button.")
    }
}


@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    currentRoute: State<NavBackStackEntry?>
) {
    var selectedItem by remember { mutableIntStateOf(0) }

    val navigationBarColors = NavigationBarItemDefaults.colors(
        indicatorColor = Color.Transparent,
        selectedIconColor = MaterialTheme.colorScheme.primary,
        selectedTextColor = MaterialTheme.colorScheme.primary,
        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
    )

    NavigationBar {
        WorkoutTrackerScreen.entries.forEachIndexed { index,item ->
            NavigationBarItem(
                icon = { Icon(painterResource(item.icon), contentDescription = stringResource(item.title)) },
                label = { Text(stringResource(item.title)) },
                onClick = {
                    selectedItem = index
                    if(currentRoute.value?.destination?.route != item.name) { navController.navigate(item.name) }
                },
                selected = selectedItem == index, // Check if this item is selected
                colors = navigationBarColors
            )
        }
    }
}
