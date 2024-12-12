package com.example.workouttracker.ui.calendar

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.workouttracker.R
import com.example.workouttracker.data.datasource.CalendarMonthsDataSource
import com.example.workouttracker.ui.WorkoutTrackerViewModel
import com.example.workouttracker.ui.exerciseDetailsDialog.ExerciseDetailsDialog
import com.example.workouttracker.ui.exerciseListDialog.AddExerciseDialog
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme
import java.time.DateTimeException
import java.time.LocalDate
import java.time.Month
import java.time.Year
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    workoutTrackerViewModel: WorkoutTrackerViewModel = viewModel(),
) {
    val workoutTrackerUiState by workoutTrackerViewModel.uiState.collectAsState()
    val showExerciseListDialog = workoutTrackerUiState.showExerciseListDialog
    val showExerciseDetailsDialog = workoutTrackerUiState.showExerciseDetailsDialog

    var selectedDay by remember { mutableIntStateOf(LocalDate.now().dayOfMonth) }
    var selectedMonth by remember { mutableIntStateOf(LocalDate.now().monthValue) }

    Column(
        modifier = modifier
    ) {
        CalendarLayout(
            selectedDay = selectedDay,
            selectedMonth = selectedMonth,
            onDaySelected = { day: Int, month: Int ->
                selectedDay = day
                selectedMonth = month
            },
            onMonthChanged = { day: Int, month: Int ->
                selectedDay = day
                selectedMonth = month
            }
        )
        SelectedDayText(selectedDay, selectedMonth)
//        DayLayout()
    }

    if(showExerciseListDialog) {
        Log.d("ExerciseDetailsDialog", "showExerciseListDialog: $showExerciseListDialog")
        AddExerciseDialog(onDismiss = { workoutTrackerViewModel.updateExerciseListDialogState(false)}, workoutTrackerViewModel = workoutTrackerViewModel, exerciseList = workoutTrackerUiState.foundExercises) //{ workoutTrackerViewModel.updateShowDialog(false) }
    }
    if(showExerciseDetailsDialog) {
        Log.d("ExerciseDetailsDialog", "showExerciseDetailsDialog: $showExerciseDetailsDialog")
        ExerciseDetailsDialog(
            onDismiss = { workoutTrackerViewModel.updateExerciseDetailsDialogState(false) },
            exercise = workoutTrackerUiState.selectedExercise!!,
            onConfirmClick = { Log.d("ExerciseDetailsDialog", "Confirm button clicked") }
            )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SelectedDayText(
    selectedDay: Int,
    selectedMonth: Int,
    selectedYear: Int = LocalDate.now().year
){
    val date = getDate(selectedYear, selectedMonth, selectedDay)
    val formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM", Locale.getDefault())

    Text(
        text = date.format(formatter),
        style = MaterialTheme.typography.labelLarge,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = dimensionResource(R.dimen.padding_medium)
            )
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarLayout(
    selectedDay: Int,
    selectedMonth: Int,
    onDaySelected: (Int, Int) -> Unit,
    onMonthChanged: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val weekDays = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    val monthName = CalendarMonthsDataSource.calendarMonths.keys.elementAt(selectedMonth - 1)
    val numberOfDays = CalendarMonthsDataSource.calendarMonths.values.elementAt(selectedMonth - 1)
    val firstDayOfMonth = LocalDate.now().withMonth(selectedMonth).withDayOfMonth(1).dayOfWeek.value
    val firstDayOfMonthIndex = firstDayOfMonth - 1
    val today = LocalDate.now().dayOfMonth


    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(
                onClick = {
                    onMonthChanged(selectedDay, (selectedMonth - 1).takeIf { it > 0 } ?: 12)
                },
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Previous Month"
                )
            }
            Text(
                text = monthName,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
            )
            IconButton(
                onClick = { onMonthChanged(selectedDay, (selectedMonth + 1).takeIf { it < 13 } ?: 1) }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Next Month"
                )
            }
        }

        // Day headers row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            weekDays.forEach { day ->
                Box(
                    modifier = Modifier.weight(1f), // Equal weight for each day
                    contentAlignment = Alignment.Center // Center content in the Box
                ) {
                    Text(
                        text = day,
                        fontWeight = FontWeight.Bold,)
                }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(7)
        ) {
            items(numberOfDays + firstDayOfMonthIndex) { index ->
                if (index >= firstDayOfMonthIndex) {
                    Box(
                        modifier = Modifier
                            .clickable {
                                onDaySelected(index - firstDayOfMonthIndex + 1, selectedMonth)
                            }
                    ) {
                        Column {
                            Text(
                                text = (index - firstDayOfMonthIndex + 1).toString(),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp)
                                    .then(
//                                      Adding highlight to selected day
                                        if (index - firstDayOfMonthIndex + 1 == selectedDay) {
                                            Log.d("CalendarScreen", "Selected day: $selectedDay")
                                            Modifier
                                                .background(
                                                    color = MaterialTheme.colorScheme.primaryContainer,
                                                    shape = MaterialTheme.shapes.large
                                                )
                                        }
//                                      Adding highlight to current day
                                        else if (index - firstDayOfMonthIndex + 1 == today && selectedMonth == LocalDate.now().monthValue)
                                            Modifier
                                                .background(
                                                    color = MaterialTheme.colorScheme.secondaryContainer,
                                                    shape = MaterialTheme.shapes.large
                                            )
                                        else Modifier
                                    )
                            )
                            if (index < numberOfDays + firstDayOfMonthIndex) {
                                HorizontalDivider(
                                    color = Color.LightGray,
                                    thickness = 1.dp,
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
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

@Preview(showBackground = true)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreenPreview() {
    WorkoutTrackerTheme(dynamicColor = false) {
        CalendarScreen(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}