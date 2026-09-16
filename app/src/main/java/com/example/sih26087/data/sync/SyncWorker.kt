package com.example.sih26087.data.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.sih26087.data.repository.DashboardRepository
import com.example.sih26087.data.repository.LmsRepository
import com.example.sih26087.data.repository.ProgrammeRepository
import com.example.sih26087.data.repository.JobRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val dashboardRepository: DashboardRepository,
    private val lmsRepository: LmsRepository,
    private val programmeRepository: ProgrammeRepository,
    private val jobRepository: JobRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
        try {
            // Run all refresh operations in parallel
            val dashboardSync = async { dashboardRepository.refreshTimetable() }
            val courseSync = async { dashboardRepository.refreshCourses() }
            val programmeSync = async { programmeRepository.refreshProgrammes() }
            val jobSync = async { jobRepository.refreshJobs() }

            dashboardSync.await()
            courseSync.await()
            programmeSync.await()
            jobSync.await()

            Result.success()
        } catch (e: Exception) {
            if (runAttemptCount < 3) {
                Result.retry()
            } else {
                Result.failure()
            }
        }
    }
}
