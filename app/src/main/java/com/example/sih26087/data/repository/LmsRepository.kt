package com.example.sih26087.data.repository

import com.example.sih26087.data.local.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LmsRepository @Inject constructor(
    private val mainDao: MainDao
) {
    fun getCourses(): Flow<List<CourseMetadataEntity>> = mainDao.getCourses()

    fun getModulesForCourse(courseId: String): Flow<List<ModuleEntity>> {
        // Need to add this to MainDao
        return mainDao.getModulesForCourse(courseId)
    }

    fun getLessonsForModule(moduleId: String): Flow<List<LessonEntity>> {
        // Need to add this to MainDao
        return mainDao.getLessonsForModule(moduleId)
    }

    suspend fun refreshCourseDetails(courseId: String) {
        val mockModules = listOf(
            ModuleEntity("m1", courseId, "Cooperative Foundation", 1, true),
            ModuleEntity("m2", courseId, "Advanced Governance", 2, false)
        )
        val mockLessons = listOf(
            LessonEntity("l1", "m1", "Introduction to PACS", "VIDEO", "https://example.com/pacs.mp4", "15:00", true),
            LessonEntity("l2", "m1", "Historical Context", "PDF", "https://example.com/history.pdf", "10 Pages", true),
            LessonEntity("l3", "m2", "Board Composition", "VIDEO", "https://example.com/board.mp4", "20:00", false)
        )
        mainDao.insertModules(mockModules)
        mainDao.insertLessons(mockLessons)
    }
}
