package com.example.sih26087.data.repository

import com.example.sih26087.data.local.MainDao
import com.example.sih26087.data.local.JobEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JobRepository @Inject constructor(
    private val mainDao: MainDao
) {
    fun getJobs(): Flow<List<JobEntity>> = mainDao.getJobs()

    suspend fun refreshJobs() {
        val mockJobs = listOf(
            JobEntity(
                id = "j1",
                title = "Cooperative Accounts Executive",
                company = "PACS Society",
                location = "Dharwad, Karnataka",
                salary = "₹25,000 - ₹30,000",
                matchScore = 95,
                postedDate = System.currentTimeMillis()
            ),
            JobEntity(
                id = "j2",
                title = "MIS Specialist",
                company = "District Coop Bank",
                location = "Hubli, Karnataka",
                salary = "₹35,000 - ₹45,000",
                matchScore = 88,
                postedDate = System.currentTimeMillis() - 86400000
            ),
            JobEntity(
                id = "j3",
                title = "Rural Logistics Manager",
                company = "Agri-Warehouse",
                location = "Belagavi, Karnataka",
                salary = "₹22,000 - ₹28,000",
                matchScore = 72,
                postedDate = System.currentTimeMillis() - 172800000
            )
        )
        mainDao.insertJobs(mockJobs)
    }
}
