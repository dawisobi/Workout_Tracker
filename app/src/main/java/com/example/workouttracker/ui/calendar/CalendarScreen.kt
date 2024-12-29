package com.example.workouttracker.ui.calendar

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.workouttracker.R
import com.example.workouttracker.data.datasource.CalendarMonthsDataSource
import com.example.workouttracker.ui.PerformedExercisesDisplay
import com.example.workouttracker.ui.TrainingSessionViewModel
import com.example.workouttracker.ui.WorkoutTrackerViewModel
import com.example.workouttracker.ui.exerciseListDialog.ExerciseViewModel
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme
import java.time.DateTimeException
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.Year
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    workoutTrackerViewModel: WorkoutTrackerViewModel = viewModel(),
    trainingSessionViewModel: TrainingSessionViewModel = viewModel(),
    exerciseListViewModel: ExerciseViewModel
) {
    val calendarViewModel: CalendarViewModel = viewModel()
    val calendarUiState by calendarViewModel.uiState.collectAsState()

    val selectedDate = getDate(calendarUiState.selectedYear, calendarUiState.selectedMonth, calendarUiState.selectedDay)

    Column(
        modifier = modifier
    ) {
        CalendarLayout(
            currentDate = calendarUiState.currentDate,
            selectedDay = calendarUiState.selectedDay,
            selectedMonth = calendarUiState.selectedMonth,
            selectedYear = calendarUiState.selectedYear,
            monthNumberOfDays = calendarUiState.selectedMonthNumberOfDays,
            monthFirstDay = calendarUiState.selectedMonthFirstDay,
            monthFirstDayIndex = calendarUiState.selectedMonthFirstDayIndex,
            onDayChanged = { calendarViewModel.updateSelectedDay(it) },
            onMonthChangedForward = { calendarViewModel.updateSelectedMonthForward() },
            onMonthChangedBackward = { calendarViewModel.updateSelectedMonthBackward() }
        )
        SelectedDayText(
            selectedDay = calendarUiState.selectedDay,
            selectedMonth = calendarUiState.selectedMonth,
            selectedYear = calendarUiState.selectedYear
        )

        PerformedExercisesDisplay(
//                exerciseList = performedExercises as MutableList<ExerciseTrainingSession>,
            trainingSessionViewModel = trainingSessionViewModel,
            exerciseListViewModel = exerciseListViewModel,
            dateToDisplay = selectedDate.toString()
        )


        // DEBUG
        // REMOVE AFTERWARDS
//        Text(text = "Selected day: ${calendarUiState.selectedDay}")
//        Text(text = "Selected month: ${calendarUiState.selectedMonth}")
//        Text(text = "Selected year: ${calendarUiState.selectedYear}")
//        Text(text = "Selected month number of days: ${calendarUiState.selectedMonthNumberOfDays}")
//        Text(text = "Selected month first day: ${calendarUiState.selectedMonthFirstDay} - ${DayOfWeek.of(calendarUiState.selectedMonthFirstDay).name}")
//        Text(text = "Selected month first day index: ${calendarUiState.selectedMonthFirstDayIndex}")
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SelectedDayText(
    selectedDay: Int,
    selectedMonth: Int,
    selectedYear: Int
){
    val date = getDate(selectedYear, selectedMonth, selectedDay)
    val formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM uuuu", Locale.getDefault())

    Text(
        text = date.format(formatter),
        style = MaterialTheme.typography.labelLarge,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.padding_medium))
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarLayout(
    currentDate: LocalDate,
    selectedDay: Int,
    selectedMonth: Int,
    selectedYear: Int,
    monthNumberOfDays: Int,
    monthFirstDay: Int,
    monthFirstDayIndex: Int,
    onDayChanged: (Int) -> Unit,
    onMonthChangedForward: () -> Unit,
    onMonthChangedBackward: () -> Unit
) {
    val weekDays = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { onMonthChangedBackward() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Previous Month"
                )
            }
            Text(
                //converts the number of the selected month into a lowercase string with capitalized first letter
                text = "${Month.of(selectedMonth).name.lowercase(Locale.getDefault())
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }} $selectedYear",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
            IconButton(
                onClick = { onMonthChangedForward() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Next Month"
                )
            }
        }

        // Day headers row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            weekDays.forEach { day ->
                Box(
                    modifier = Modifier.weight(1f), // Equal weight for each day
                    contentAlignment = Alignment.Center // Center content in the Box
                ) {
                    Text(text = day, fontWeight = FontWeight.Bold,)
                }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(7)
        ) {
            items(monthNumberOfDays + monthFirstDayIndex) { index ->
                if (index >= monthFirstDayIndex) {
                    Box(
                        modifier = Modifier.clickable { onDayChanged(index - monthFirstDayIndex + 1) }
                    ) {
                        Column {
                            Text(
                                text = (index - monthFirstDayIndex + 1).toString(),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp)
                                    .then(
                                        // Adding highlight to selected day
                                        if (index - monthFirstDayIndex + 1 == selectedDay) {
                                            Log.d("CalendarScreen", "Selected day: $selectedDay")
                                            Modifier.background(
                                                    color = MaterialTheme.colorScheme.primaryContainer,
                                                    shape = MaterialTheme.shapes.large
                                            )
                                        }
                                        // Adding outline to current day
                                        else if (index - monthFirstDayIndex + 1 == currentDate.dayOfMonth && selectedMonth == currentDate.monthValue && selectedYear == currentDate.year)
                                            Modifier
                                                .border(
                                                    width = 3.dp,
                                                    color = MaterialTheme.colorScheme.primaryContainer,
                                                    shape = MaterialTheme.shapes.large
                                                )
                                        else Modifier
                                    )
                            )
                            if (index < monthNumberOfDays + monthFirstDayIndex) {
                                HorizontalDivider(
                                    color = Color.LightGray,
                                    thickness = 1.dp,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun getDate(year: Int, month: Int, day: Int): LocalDate {
    return try {
        LocalDate.of(year, month, day)
    } catch (e: DateTimeException) {
        LocalDate.of(year, month, 1).withDayOfMonth(
            Month.of(month).length(Year.of(year).isLeap)
        )
    }
}

//@Preview(showBackground = true)
//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun CalendarScreenPreview() {
//    WorkoutTrackerTheme(dynamicColor = false) {
//        CalendarScreen(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        )
//    }
//}