package com.example.sih26087.data.remote

import com.example.sih26087.data.model.*
import com.example.sih26087.data.local.*
import retrofit2.http.*

interface ApiService {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): ApiResponse<LoginResponse>

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): ApiResponse<UserProfile>

    @GET("profiles/me")
    suspend fun getMyProfile(): ApiResponse<UserProfile>

    @GET("programmes")
    suspend fun getProgrammes(): ApiResponse<List<ProgrammeEntity>>

    @GET("timetable")
    suspend fun getTimetable(): ApiResponse<List<TimetableEntity>>

    @POST("attendance/mark")
    suspend fun markAttendance(@Body request: AttendanceRequest): ApiResponse<Unit>

    @GET("courses")
    suspend fun getCourses(): ApiResponse<List<CourseMetadataEntity>>

    @GET("courses/{id}/modules")
    suspend fun getCourseModules(@Path("id") courseId: String): ApiResponse<List<ModuleEntity>>

    @GET("modules/{id}/lessons")
    suspend fun getModuleLessons(@Path("id") moduleId: String): ApiResponse<List<LessonEntity>>

    @GET("jobs")
    suspend fun getJobs(): ApiResponse<List<JobEntity>>

    @POST("ai/chat")
    suspend fun chatWithAi(@Body request: ChatRequest): ApiResponse<ChatResponse>
}

data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T?,
    val timestamp: String
)

data class LoginRequest(val email: String, val role: String)
data class LoginResponse(val token: String, val user: UserProfile)
data class RegisterRequest(val name: String, val email: String, val mobile: String, val role: String)
data class AttendanceRequest(val batchId: String, val sessionName: String, val status: String, val timestamp: Long)
data class ChatRequest(val message: String, val context: Map<String, String>)
data class ChatResponse(val reply: String)
