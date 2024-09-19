package com.example.workouttracker.ui

import android.os.Build
import android.widget.CalendarView
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.workouttracker.R
import com.example.workouttracker.datasource.CalendarMonthsDataSource
import java.time.LocalDate
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(modifier: Modifier = Modifier) {
    val calendarMonths = CalendarMonthsDataSource.calendarMonths

    CalendarLayout(modifier = modifier)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarLayout(modifier: Modifier = Modifier) {
    val monthName = CalendarMonthsDataSource.calendarMonths.keys.elementAt(8)
    val numberOfDays = CalendarMonthsDataSource.calendarMonths.values.elementAt(8)
    val offset = 3 // Number of days to offset
    val weekDays = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    val firstDayOfMonth = LocalDate.now().withDayOfMonth(1).dayOfWeek.value
    val firstDayOfMonthIndex = firstDayOfMonth - 1
    //val today = LocalDate.now().dayOfMonth

    //Text(firstDayOfMonthIndex.toString())

    Column( modifier = modifier ) {
        Text(
            text = monthName,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth())
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
                    Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = (index - firstDayOfMonthIndex + 1).toString(),
                                modifier = Modifier
//                                .fillMaxWidth()
                                    .fillMaxSize()
                                    .padding(8.dp),
                                textAlign = TextAlign.Center
                            )
                            if (index == 15) {
                                Image(
                                    painter = painterResource(id = R.drawable.icon_image_dumbbell_48),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .padding(
                                            bottom = dimensionResource(R.dimen.padding_tiny),
                                        )
                                )
                            }
//                        if (index < numberOfDays + offset) {
//                            HorizontalDivider(
//                                color = Color.LightGray,
//                                thickness = 1.dp,
//                                modifier = Modifier
//                                    .align(Alignment.CenterHorizontally))
//                        }
                    }
                }
            }
        }

//        AndroidView(factory = { CalendarView(it) })
    }
}



@Preview(showBackground = true)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreenPreview() {
    CalendarScreen(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}