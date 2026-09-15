package com.example.sih26087.data.repository

import com.example.sih26087.data.local.MainDao
import com.example.sih26087.data.local.TimetableEntity
import com.example.sih26087.data.local.CourseMetadataEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DashboardRepository @Inject constructor(
    private val mainDao: MainDao
) {
    fun getTimetable(): Flow<List<TimetableEntity>> = mainDao.getTimetable()

    suspend fun refreshTimetable() {
        // Mocking an API call update
        val mockEntries = listOf(
            TimetableEntity("1", "2026-03-15", "10:00 AM", "Cooperative Management Principles", "Prof. Rajesh Kumar", "Room 3B", "Batch A", false),
            TimetableEntity("2", "2026-03-15", "02:00 PM", "Digital Financial Awareness & ERP", "Ms. Sneha Patil", "Lab 1", "Batch A", false),
            TimetableEntity("3", "2026-03-16", "11:00 AM", "Agri-Supply Chain Logistics", "Mr. Amit Shah", "Room 2", "Batch A", true)
        )
        mainDao.insertTimetable(mockEntries)
    }

    fun getEnrolledCourses(): Flow<List<CourseMetadataEntity>> = mainDao.getCourses()

    suspend fun refreshCourses() {
        val mockCourses = listOf(
            CourseMetadataEntity("c1", "Advanced Cooperative Management", "Master the skills of managing large scale cooperatives.", "Prof. Rajesh", 45, "Management"),
            CourseMetadataEntity("c2", "Digital Literacy for Rural Youth", "Basics of computers, internet and mobile banking.", "Ms. Sneha", 90, "Technology")
        )
        mainDao.insertCourses(mockCourses)
    }
}
