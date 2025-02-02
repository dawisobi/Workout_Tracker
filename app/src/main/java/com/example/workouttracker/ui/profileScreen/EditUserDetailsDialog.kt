package com.example.workouttracker.ui.profileScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme

@Composable
fun EditUserDetailsDialog (
    onDismiss: () -> Unit,
    onSaveChangesClick: () -> Unit,
    userHeight: String,
    userWeight: String,
    onUserHeightChange: (String) -> Unit,
    onUserWeightChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Dialog( onDismissRequest = { onDismiss() } ) {
        Card( shape = RoundedCornerShape(16.dp)) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Edit", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
                    IconButton(
                        onClick = { onDismiss() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close"
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = userWeight,
                        singleLine = true,
                        onValueChange = onUserWeightChange,
                        label = { Text("Weight") },
                        suffix = { Text("kg") },
//                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
//                    trailingIcon = {
//                        if(searchedExerciseName.isNotEmpty()){
//                            IconButton(onClick = { onSearchClear() }) {
//                                Icon(Icons.Filled.Clear, contentDescription = "Clear") } } },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            //.fillMaxWidth()
                            .padding(vertical = 4.dp).weight(1f),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                            }
                        )
                    )
//                    IconButton( onClick = {  }, modifier = Modifier.weight(0.3f)) {
//                        Icon(
//                            painter = painterResource(R.drawable.icon_minus_circle),
//                            contentDescription = "Increase by one",
//                            tint = MaterialTheme.colorScheme.primary,
//                            modifier = Modifier.height(32.dp).width(32.dp)
//                        )
//                    }
//                    IconButton( onClick = {  }, modifier = Modifier.weight(0.3f)) {
//                        Icon(
//                            imageVector = Icons.Rounded.AddCircle,
//                            contentDescription = "Increase by one",
//                            tint = MaterialTheme.colorScheme.primary,
//                            modifier = Modifier.height(32.dp).width(32.dp)
//                        )
//                    }
//                    IconButton( onClick = {  }, modifier = Modifier.weight(0.4f)) {
//                        Icon(
//                            imageVector = Icons.Rounded.CheckCircle,
//                            contentDescription = "Increase by one",
//                            tint = Color(0xFF087525),
//                            modifier = Modifier.height(32.dp).width(32.dp)
//                        )
//                    }

                }


                OutlinedTextField(
                    value = userHeight,
                    singleLine = true,
                    onValueChange = onUserHeightChange,
                    label = { Text("Height") },
                    suffix = { Text("cm") },
//                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
//                    trailingIcon = {
//                        if(searchedExerciseName.isNotEmpty()){
//                            IconButton(onClick = { onSearchClear() }) {
//                                Icon(Icons.Filled.Clear, contentDescription = "Clear") } } },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    )
                )

                Button(
                    onClick = {
                        onSaveChangesClick()

                              },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, start = 72.dp, end = 72.dp)
                ) {
                    Text(text = "Save Changes")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EditUserDetailsDialogPreview() {
    WorkoutTrackerTheme(darkTheme = false, dynamicColor = false) {
        EditUserDetailsDialog(
            onDismiss = {},
            onSaveChangesClick = {},
            userHeight = "170",
            userWeight = "70",
            onUserHeightChange = {},
            onUserWeightChange = {}
        )
    }
}