package com.example.workouttracker.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.workouttracker.data.model.UserWeightData
import kotlinx.coroutines.flow.Flow

@Dao
interface UserWeightDao {

    @Query("SELECT * FROM userWeightDatabase ORDER BY date DESC")
    fun getAllUserWeightData(): Flow<List<UserWeightData>>

    @Query("SELECT * FROM userWeightDatabase ORDER BY entryId DESC LIMIT 1")
    fun getRecentUserWeightData(): Flow<UserWeightData>

    @Insert
    suspend fun insertUserWeightData(userWeightData: UserWeightData)

    @Delete
    suspend fun deleteUserWeightData(userWeightData: UserWeightData)

}

