package com.example.workouttracker

import android.media.RouteListingPreference
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.workouttracker.ui.CalendarScreen
import com.example.workouttracker.ui.HomeScreen
import com.example.workouttracker.ui.ProfileScreen
import com.example.workouttracker.ui.TodayTrainingScreen
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme

enum class WorkoutTrackerScreen(
    @StringRes val title: Int,
    @DrawableRes val icon: Int
) {
    Home(R.string.bottom_navBar_home, R.drawable.icon_home_fill),
    Today(R.string.bottom_navBar_today, R.drawable.icon_checklist),
    Calendar(R.string.bottom_navBar_calendar, R.drawable.icon_calendar_fill),
    Profile(R.string.bottom_navBar_profile, R.drawable.icon_profile_fill),
//    Settings(R.string.bottom_navBar_settings, R.drawable.icon_settings_fill)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WorkoutTrackerApp() {
    // Create ViewModel
//    val viewModel: WorkoutTrackerViewModel = viewModel()
    val navController: NavHostController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
//    val currentScreen = LunchTrayScreen.valueOf(
//        backStackEntry?.destination?.route ?: LunchTrayScreen.Start.name
//    )

    Scaffold(
//        topBar = { TopBar() },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = WorkoutTrackerScreen.Home.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = WorkoutTrackerScreen.Home.name) {
                HomeScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }
            composable(route = WorkoutTrackerScreen.Today.name) {
                TodayTrainingScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }
            composable(route = WorkoutTrackerScreen.Calendar.name) {
                CalendarScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }
            composable(route = WorkoutTrackerScreen.Profile.name) {
                ProfileScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = { Text(stringResource(R.string.app_name)) },
    )
}



@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun WorkoutTrackerAppPreview() {
    WorkoutTrackerTheme(){
        WorkoutTrackerApp()
    }
}


@Composable
fun BottomNavigationBar(
    navController: NavHostController
) {
    var selectedItem by remember { mutableStateOf(0) }

    NavigationBar {
        val navigationBarColors = NavigationBarItemDefaults.colors(
            indicatorColor = Color.Transparent,
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
        )

        WorkoutTrackerScreen.entries.forEachIndexed { index,item ->
            NavigationBarItem(
                icon = { Icon(painterResource(item.icon), contentDescription = null) },
                label = { Text(stringResource(item.title)) },
                onClick = {
                    selectedItem = index
                    navController.navigate(item.name)
                }, // Update selected item on click
                selected = selectedItem == index, // Check if this item is selected
                colors = navigationBarColors
            )
        }
    }
}