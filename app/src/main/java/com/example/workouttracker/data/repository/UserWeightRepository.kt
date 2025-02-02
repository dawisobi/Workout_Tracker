package com.example.workouttracker.data.repository

import com.example.workouttracker.data.dao.UserWeightDao
import com.example.workouttracker.data.model.UserWeightData
import kotlinx.coroutines.flow.Flow

class UserWeightRepository(private val userWeightDao: UserWeightDao) {

    fun getAllUserWeightData(): Flow<List<UserWeightData>> {
        return userWeightDao.getAllUserWeightData()
    }

    fun getRecentUserWeightData(): Flow<UserWeightData> {
        return userWeightDao.getRecentUserWeightData()
    }

    suspend fun insertUserWeightData(userWeightData: UserWeightData) {
        userWeightDao.insertUserWeightData(userWeightData)
    }

}