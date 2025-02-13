package com.example.workouttracker.ui.profileScreen

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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.workouttracker.R
import com.example.workouttracker.data.datastore.UserDetailsDataStore
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun ProfileScreen(
    profileScreenViewModel: ProfileScreenViewModel,
    dataStore: UserDetailsDataStore,
    userHeight: Int,
    modifier: Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    val profileScreenUiState by profileScreenViewModel.uiState.collectAsStateWithLifecycle()
    val showUserDetailsDialog = profileScreenUiState.showUserDetailsDialog

    LaunchedEffect(Unit) {
        profileScreenViewModel.updateUserDetails(weight = profileScreenViewModel.currentUserWeight.value.userWeight.toString(), height = userHeight.toString())
    }

    Column(modifier = modifier){
        ProfileHeader()
        UserDetailsPanel(
            weightValue = profileScreenUiState.userWeight,
            heightValue = userHeight.toString(),
            onEditUserDetailsClick = {
                profileScreenViewModel.updateUserDetailsInput(weight = profileScreenUiState.userWeight, height = userHeight.toString())
                profileScreenViewModel.showUserDetailsDialog()
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        UserBmiPanel(
            bmiValue = profileScreenUiState.userBmi
        )
    }

    if(showUserDetailsDialog){
        EditUserDetailsDialog(
            onDismiss = { profileScreenViewModel.hideUserDetailsDialog() },
            onSaveChangesClick = {
                profileScreenViewModel.saveUserDetails()
                coroutineScope.launch { dataStore.saveUserDetails(profileScreenViewModel.heightInput.toInt())}
                profileScreenViewModel.insertUserWeightData()
                                 },
            userHeight = profileScreenViewModel.heightInput,
            userWeight = profileScreenViewModel.weightInput,
            onUserHeightChange = { profileScreenViewModel.updateHeightInput(it) },
            onUserWeightChange = { profileScreenViewModel.updateWeightInput(it) }
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
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun UserDetailsPanel(
    onEditUserDetailsClick: () -> Unit,
    weightValue: String,
    heightValue: String
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
                Column {
                    Text(
                        text = buildAnnotatedString {
                            append("Weight: ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append(weightValue) }
                            append(" kg") },
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    )
                    Text(
                            text = buildAnnotatedString {
                                append("Height: ")
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append(heightValue) }
                                append(" cm") },
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    )
                }

                IconButton(onClick = { onEditUserDetailsClick() }) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit user details"
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
    val slices = BmiSlices.slices

    Card(
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = buildAnnotatedString {
                    append("BMI: ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append(bmiValue) } },
                )
                Text(text = getBmiLevel(bmiValue.toFloat(), slices), fontWeight = FontWeight.Bold)
            }
            BmiChart(bmiValue, slices)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
private fun BmiChart(bmiValue: String, bmiSlices: List<Slice>) {

    val bmi = bmiValue.toFloatOrNull() ?: 0f
    val sliceMatchingBmi = bmiSlices.find { it.minValue <= bmi && it.maxValue >= bmi } ?: bmiSlices.last()

    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth().offset(y = (-6).dp)
    ) {
        bmiSlices.forEach { slice ->
            val slicePercentage = getSliceSize(slice)
            val sliceClipShape = {
                if(slice.minValue == 0f) RoundedCornerShape(50, 0,0,50)
                else if(slice.maxValue == 58.4f) RoundedCornerShape(0, 50,50,0)
                else RoundedCornerShape(0)
            }
            val indicatorColor = if(slice == sliceMatchingBmi) MaterialTheme.colorScheme.onSurface else Color.Transparent

            Column(
                modifier = Modifier.weight(slicePercentage),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Edit weight",
                    tint = indicatorColor,
                    modifier = Modifier.width(32.dp).height(32.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth().offset(y = (-8).dp)
                        .clip(shape = sliceClipShape())
                        .background(slice.color)
                        .height(10.dp)
                ) { }
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
            profileScreenViewModel = ProfileScreenViewModel(context = LocalContext.current),
            userHeight = 180,
            dataStore = UserDetailsDataStore(context = LocalContext.current),
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}

// Helper function to calculate slice size based on min and max values
private fun getSliceSize(slice: Slice): Float {
    val sliceRange: Float

    if(slice.minValue == 0f || slice.maxValue == 58.4f) {
        return 5f
    } else {
        sliceRange = slice.maxValue - slice.minValue
        return sliceRange / 58.4f * 100
    }
}

internal fun getBmiLevel(bmi: Float, slices: List<Slice>): String {
    return when(bmi) {
        in 0f..18.49f -> slices[0].label
        in 18.5f..24.99f -> slices[1].label
        in 25.0f..29.99f -> slices[2].label
        in 30.0f..34.99f -> slices[3].label
        in 35.0f..39.99f -> slices[4].label
        else -> slices[5].label
    }
}

// Data class for BMI chart slices
data class Slice(val minValue: Float, val maxValue: Float, val color: Color, val label: String)

object BmiSlices {
    val slices = listOf(
        Slice(minValue = 0f, maxValue = 18.49f, color = Color(0xFF007fe0), label = "Underweight"),
        Slice(minValue = 18.5f, maxValue = 24.99f, color = Color(0xFF00b807), label = "Normal weight"),
        Slice(minValue = 25.0f, maxValue = 29.99f, color = Color(0xFFe0d400), label = "Overweight"),
        Slice(minValue = 30.0f, maxValue = 34.99f, color = Color(0xFFdb8114), label = "Obesity I"),
        Slice(minValue = 35.0f, maxValue = 39.99f, color = Color(0xFFc90202), label = "Obesity II"),
        Slice(minValue = 40.0f, maxValue = 58.4f, color = Color(0xFF5a0ad1), label = "Obesity III")
    )
}