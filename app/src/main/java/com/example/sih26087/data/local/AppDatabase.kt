package com.example.sih26087.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        TimetableEntity::class,
        AttendanceRecordEntity::class,
        CourseMetadataEntity::class,
        NotificationEntity::class,
        ProgrammeEntity::class,
        JobEntity::class,
        SkillEntity::class,
        ModuleEntity::class,
        LessonEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mainDao(): MainDao
}
