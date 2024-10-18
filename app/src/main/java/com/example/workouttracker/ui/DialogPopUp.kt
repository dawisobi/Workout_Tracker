package com.example.workouttracker.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme

@Composable
fun AddExerciseDialog(
    onDismiss: () -> Unit
){
    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(375.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
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
            text = "Add Exercise",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold
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
    }
}



@Preview(showBackground = true)
@Composable
fun AddExerciseDialogPreview(){
    WorkoutTrackerTheme(dynamicColor = false) {
        AddExerciseDialog( onDismiss = { Log.d("AddExerciseDialog", "onDismiss") } )
    }
}