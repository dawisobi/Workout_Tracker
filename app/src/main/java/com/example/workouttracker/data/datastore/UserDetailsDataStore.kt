package com.example.workouttracker.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_details")

class UserDetailsDataStore(
    private val context: Context
) {

    suspend fun saveUserDetails(height: Int) {
        context.dataStore.edit { preferences ->
            preferences[heightKey] = height
        }
    }

    val height = context.dataStore.data.map { preferences ->
        preferences[heightKey] ?: 0
    }


    companion object{
        val heightKey = intPreferencesKey("heightKey")
    }
}