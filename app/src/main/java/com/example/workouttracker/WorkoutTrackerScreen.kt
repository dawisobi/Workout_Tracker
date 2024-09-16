package com.example.workouttracker

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.workouttracker.ui.HomeScreen

enum class WorkoutTrackerScreen {
    Home,
    Today,
    Calendar,
    Profile,
    Settings
}

@Composable
fun WorkoutTrackerApp() {

    Scaffold(
        bottomBar = { BottomNavigationBar() }
    ) { innerPadding ->
        HomeScreen(
            modifier = Modifier
                .padding(innerPadding)
                .padding(dimensionResource(R.dimen.padding_medium))
                .fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBar() {
    NavigationBar {
        val navigationBarColors = NavigationBarItemDefaults.colors(
            indicatorColor = Color.Transparent,
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
        )


        NavigationBarItem(
            icon = { Icon(painter = painterResource(R.drawable.icon_home_fill), contentDescription = null)},
            label = { Text(stringResource(R.string.bottom_navBar_home)) },
            onClick = { /*TODO*/ },
            selected = true,
            colors = navigationBarColors
        )
        NavigationBarItem(
            icon = { Icon(painter = painterResource(R.drawable.icon_checklist), contentDescription = null)},
            label = { Text(stringResource(R.string.bottom_navBar_today)) },
            onClick = { /*TODO*/ },
            selected = false,
            colors =  navigationBarColors
        )
        NavigationBarItem(
            icon = { Icon(painter = painterResource(R.drawable.icon_calendar_fill), contentDescription = null)},
            label = { Text(stringResource(R.string.bottom_navBar_calendar)) },
            onClick = { /*TODO*/ },
            selected = false,
            colors = navigationBarColors
        )
        NavigationBarItem(
            icon = { Icon(painter = painterResource(R.drawable.icon_profile_fill), contentDescription = null)},
            label = { Text(stringResource(R.string.bottom_navBar_profile)) },
            onClick = { /*TODO*/ },
            selected = false,
            colors = navigationBarColors
        )
        NavigationBarItem(
            icon = { Icon(painter = painterResource(R.drawable.icon_settings_fill), contentDescription = null)},
            label = { Text(stringResource(R.string.bottom_navBar_settings)) },
            onClick = { /*TODO*/ },
            selected = false,
            colors = navigationBarColors
        )
    }
}