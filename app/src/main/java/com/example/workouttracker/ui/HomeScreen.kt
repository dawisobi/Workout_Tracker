package com.example.workouttracker.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.workouttracker.R
import com.example.workouttracker.ui.theme.WorkoutTrackerTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.welcome_message),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.rounded_add_circle_24),
                        contentDescription = null,
                        tint =  MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = stringResource(R.string.add_exercise),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    WorkoutTrackerTheme(dynamicColor = false) {
        HomeScreen(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
                .fillMaxSize()
        )
    }
}