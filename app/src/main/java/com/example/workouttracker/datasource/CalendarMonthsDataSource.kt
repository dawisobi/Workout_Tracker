package com.example.workouttracker.datasource

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Month
import java.time.Year
import java.time.YearMonth

object CalendarMonthsDataSource {
    @RequiresApi(Build.VERSION_CODES.O)
    val calendarMonths = mapOf(
        "January" to YearMonth.of(Year.now().value, Month.JANUARY).lengthOfMonth(),
        "February" to YearMonth.of(Year.now().value, Month.FEBRUARY).lengthOfMonth(),
        "March" to YearMonth.of(Year.now().value, Month.MARCH).lengthOfMonth(),
        "April" to YearMonth.of(Year.now().value, Month.APRIL).lengthOfMonth(),
        "May" to YearMonth.of(Year.now().value, Month.MAY).lengthOfMonth(),
        "June" to YearMonth.of(Year.now().value, Month.JUNE).lengthOfMonth(),
        "July" to YearMonth.of(Year.now().value, Month.JULY).lengthOfMonth(),
        "August" to YearMonth.of(Year.now().value, Month.AUGUST).lengthOfMonth(),
        "September" to YearMonth.of(Year.now().value, Month.SEPTEMBER).lengthOfMonth(),
        "October" to YearMonth.of(Year.now().value, Month.OCTOBER).lengthOfMonth(),
        "November" to YearMonth.of(Year.now().value, Month.NOVEMBER).lengthOfMonth(),
        "December" to YearMonth.of(Year.now().value, Month.DECEMBER).lengthOfMonth()
    )
}