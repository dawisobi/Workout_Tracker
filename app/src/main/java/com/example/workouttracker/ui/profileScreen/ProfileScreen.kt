package com.example.workouttracker.ui.profileScreen

import android.app.slice.Slice
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.workouttracker.R
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier){
        ProfileHeader()
        UserWeightPanel(
            weightValue = "86",
            editWeight = { /* TODO */ }
        )
        Spacer(modifier = Modifier.height(16.dp))
        UserBmiPanel(
            bmiValue = "23.48"
        )
    }
}

@Composable
fun ProfileHeader() {
    Column {
        Text(
            text = stringResource(R.string.bottom_navBar_profile),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun UserWeightPanel(
    editWeight: () -> Unit = {},
    weightValue: String,
) {
    Card(
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Current weight: ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append(weightValue) }
                        append(" kg") },
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                )
                IconButton(onClick = { editWeight() }) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit weight"
                    )
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun UserBmiPanel(
    bmiValue: String,
) {
    Card(
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
        ) {
            Text(text = "BMI: $bmiValue")
            BmiChart(bmiValue)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
private fun BmiChart(bmiValue: String) {

    val slices = listOf(
        Slice(minValue = 0f, maxValue = 18.4f, color = Color(0xFF007fe0), label = "Underweight"),
        Slice(minValue = 18.5f, maxValue = 24.9f, color = Color(0xFF00b807), label = "Normal weight"),
        Slice(minValue = 25.0f, maxValue = 29.9f, color = Color(0xFFe0d400), label = "Overweight"),
        Slice(minValue = 30.0f, maxValue = 34.9f, color = Color(0xFFdb8114), label = "Obesity I"),
        Slice(minValue = 35.0f, maxValue = 39.9f, color = Color(0xFFc90202), label = "Obesity II"),
        Slice(minValue = 40.0f, maxValue = 60.0f, color = Color(0xFF5a0ad1), label = "Obesity III")
    )

    val bmi = bmiValue.toFloatOrNull() ?: 0f
    val sliceMatchingBmi = slices.find { it.minValue <= bmi && it.maxValue >= bmi } ?: slices.last()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = (-6).dp)
    ) {
        Row(
            modifier = Modifier
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Edit weight",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.width(32.dp).height(32.dp).offset(x = 30.dp)
            )
        }
        Row(
            modifier = Modifier
                .padding(horizontal = dimensionResource(R.dimen.padding_small))
                .fillMaxWidth()
                .offset(y = (-10).dp)
                .height(10.dp)
                .clip(shape = MaterialTheme.shapes.medium)

        ) {
            slices.forEach { slice ->
                val slicePercentage = getSliceSize(slice)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(slice.color)
                        .weight(slicePercentage)
                ) {
                    Text(text = "")
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.P)
@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    WorkoutTrackerTheme(darkTheme = false) {
        ProfileScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}

// Helper function to calculate slice size based on min and max values
private fun getSliceSize(slice: com.example.workouttracker.ui.profileScreen.Slice): Float {
    val sliceRange: Float

    if(slice.minValue == 0f || slice.maxValue == 60f) {
        return 5f
    } else {
        sliceRange = slice.maxValue - slice.minValue
        return sliceRange / 60 * 100
    }
}

// Data class for BMI chart slices
data class Slice(val minValue: Float, val maxValue: Float, val color: Color, val label: String)