package com.example.workouttracker.ui.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
data class CalendarUiState (

    val currentDate: LocalDate = LocalDate.now(),

    // Selected date in the calendar
    val selectedDay: Int = LocalDate.now().dayOfMonth,
    val selectedMonth: Int = LocalDate.now().monthValue,
    val selectedYear: Int = LocalDate.now().year,

    val selectedMonthNumberOfDays: Int = YearMonth.of(selectedYear, selectedMonth).lengthOfMonth(),
    val selectedMonthFirstDay: Int = LocalDate.of(selectedYear, selectedMonth, 1).dayOfWeek.value,
    val selectedMonthFirstDayIndex: Int = LocalDate.of(selectedYear, selectedMonth, 1).dayOfWeek.value - 1
)