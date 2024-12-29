package com.example.workouttracker.ui.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.workouttracker.ui.exerciseDetailsDialog.ExerciseDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.YearMonth


@RequiresApi(Build.VERSION_CODES.O)
class CalendarViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CalendarUiState())
    val uiState: StateFlow<CalendarUiState> = _uiState.asStateFlow()


    fun updateSelectedDay(day: Int) {
        _uiState.value = _uiState.value.copy(selectedDay = day)
    }

    fun updateSelectedMonth(month: Int) {
        _uiState.value = _uiState.value.copy(selectedMonth = month)
    }

    fun updateSelectedYear(year: Int) {
        _uiState.value = _uiState.value.copy(selectedYear = year)
    }

    fun updateSelectedMonthForward() {
        if (_uiState.value.selectedMonth == 12) { _uiState.value = _uiState.value.copy(
                selectedMonth = 1,
                selectedYear = _uiState.value.selectedYear + 1 )
        } else {
            _uiState.value = _uiState.value.copy(selectedMonth = _uiState.value.selectedMonth + 1)
        }
        updateMonthDetails()
    }

    fun updateSelectedMonthBackward() {
        if (_uiState.value.selectedMonth == 1) { _uiState.value = _uiState.value.copy(
                selectedMonth = 12,
                selectedYear = _uiState.value.selectedYear - 1 )
        } else {
            _uiState.value = _uiState.value.copy(selectedMonth = _uiState.value.selectedMonth - 1)
        }
        updateMonthDetails()
    }

    private fun updateMonthDetails() {
        _uiState.value = _uiState.value.copy(
            selectedMonthNumberOfDays = YearMonth.of(_uiState.value.selectedYear, _uiState.value.selectedMonth).lengthOfMonth(),
            selectedMonthFirstDay = LocalDate.of(_uiState.value.selectedYear, _uiState.value.selectedMonth, 1).dayOfWeek.value,
            selectedMonthFirstDayIndex = LocalDate.of(_uiState.value.selectedYear, _uiState.value.selectedMonth, 1).dayOfWeek.value - 1
        )
    }
}