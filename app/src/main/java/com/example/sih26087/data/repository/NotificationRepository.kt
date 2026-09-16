package com.example.sih26087.data.repository

import com.example.sih26087.data.local.MainDao
import com.example.sih26087.data.local.NotificationEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepository @Inject constructor(
    private val mainDao: MainDao
) {
    fun getNotifications(): Flow<List<NotificationEntity>> = mainDao.getNotifications()

    suspend fun refreshNotifications() {
        val mockNotices = listOf(
            NotificationEntity("n1", "Class Rescheduled", "Your morning session 'PACS Framework' is moved to 11:00 AM.", System.currentTimeMillis() - 600000, false),
            NotificationEntity("n2", "Certificate Issued", "Congratulations! Your 'Digital Literacy' certificate is now available.", System.currentTimeMillis() - 7200000, false),
            NotificationEntity("n3", "New Job Match", "A new opening for 'Cooperative Executive' matches your skill profile.", System.currentTimeMillis() - 18000000, true)
        )
        mockNotices.forEach { mainDao.insertNotification(it) }
    }
}
