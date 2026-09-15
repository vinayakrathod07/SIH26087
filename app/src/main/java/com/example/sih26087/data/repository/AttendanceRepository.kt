package com.example.sih26087.data.repository

import com.example.sih26087.data.local.MainDao
import com.example.sih26087.data.local.AttendanceRecordEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AttendanceRepository @Inject constructor(
    private val mainDao: MainDao
) {
    fun getAttendanceRecords(): Flow<List<AttendanceRecordEntity>> = mainDao.getAttendanceRecords()

    suspend fun markAttendance(batchId: String, sessionName: String, status: String) {
        val record = AttendanceRecordEntity(
            id = "att_" + System.currentTimeMillis(),
            batchId = batchId,
            sessionName = sessionName,
            timestamp = System.currentTimeMillis(),
            status = status,
            syncStatus = "PENDING"
        )
        mainDao.insertAttendanceRecord(record)
        // In a real app, we would trigger a sync here or use WorkManager
    }
}
