package com.example.sih26087.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timetable_entries")
data class TimetableEntity(
    @PrimaryKey val id: String,
    val date: String,
    val time: String,
    val subject: String,
    val trainer: String,
    val room: String,
    val batch: String,
    val isOnline: Boolean
)

@Entity(tableName = "attendance_records")
data class AttendanceRecordEntity(
    @PrimaryKey val id: String,
    val batchId: String,
    val sessionName: String,
    val timestamp: Long,
    val status: String, // PRESENT, ABSENT, LATE
    val syncStatus: String // SYNCED, PENDING
)

@Entity(tableName = "course_metadata")
data class CourseMetadataEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val trainer: String,
    val progress: Int,
    val category: String = "General"
)

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey val id: String,
    val title: String,
    val message: String,
    val timestamp: Long,
    val isRead: Boolean
)

@Entity(tableName = "programmes")
data class ProgrammeEntity(
    @PrimaryKey val id: String,
    val title: String,
    val institution: String,
    val duration: String,
    val eligibility: String,
    val seats: Int,
    val category: String
)

@Entity(tableName = "jobs")
data class JobEntity(
    @PrimaryKey val id: String,
    val title: String,
    val company: String,
    val location: String,
    val salary: String,
    val matchScore: Int,
    val postedDate: Long
)

@Entity(tableName = "skills")
data class SkillEntity(
    @PrimaryKey val name: String,
    val proficiency: String, // BEGINNER, INTERMEDIATE, EXPERT
    val isVerified: Boolean
)
