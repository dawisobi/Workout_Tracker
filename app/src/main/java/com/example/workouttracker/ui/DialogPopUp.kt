package com.example.workouttracker.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.workouttracker.model.Exercise
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme
import com.example.workouttracker.datasource.ExercisesDatabase.exerciseDb


@Composable
fun AddExerciseDialog(
    onDismiss: () -> Unit
){
    val configuration = LocalConfiguration.current
    val deviceScreenWidth = configuration.screenWidthDp
    val deviceScreenHeight = configuration.screenHeightDp

//    val deviceScreenWidth = LocalContext.current.resources.displayMetrics.widthPixels
//    val deviceScreenHeight = LocalContext.current.resources.displayMetrics.heightPixels
    Log.d("AddExerciseDialog", "Device screen width: $deviceScreenWidth")
    Log.d("AddExerciseDialog", "Device screen width * 0.9: ${deviceScreenWidth*0.9}")
    Log.d("AddExerciseDialog", "Device screen height: $deviceScreenHeight")
    Log.d("AddExerciseDialog", "Device screen height * 0.9: ${deviceScreenHeight*0.9}")

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        ),
    ) {
        Card(
            modifier = Modifier
//                .fillMaxWidth()
//                .fillMaxSize()
                .size((deviceScreenWidth * 0.9).dp, (deviceScreenHeight * 0.9).dp)
//                .width((deviceScreenWidth * 0.9).dp)
//                .height(375.dp)
//                .padding(30.dp),
            ,shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AddExerciseContent()
            }
        }
    }
}

@Composable
fun AddExerciseContent() {
    Column {
        Text(
            text = "Select Exercise",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        OutlinedTextField(
            value = "",
            onValueChange = { },
            label = { Text("Search") },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
        DisplayExercisesList(exerciseList = exerciseDb)
    }
}

@Composable
fun DisplayExercisesList(exerciseList: List<Exercise>) {
    val exerciseListSorted = exerciseList.sortedBy { it.name }
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(exerciseListSorted) { exercise ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        Log.d("AddExerciseDialog", "Exercise selected: ${exercise.name}")
                    }
            ) {
                Text(
                    text = exercise.name,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(vertical = 12.dp, horizontal = 12.dp)
                )
            }
            HorizontalDivider()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddExerciseDialogPreview(){
    WorkoutTrackerTheme(dynamicColor = false) {
        AddExerciseDialog( onDismiss = {  } )
    }
}