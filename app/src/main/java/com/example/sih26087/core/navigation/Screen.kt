package com.example.sih26087.core.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Register : Screen("register")
    object ForgotPassword : Screen("forgot_password")
    
    // Core functional modules / hubs
    object TraineeDashboard : Screen("trainee_dashboard")
    object TrainerDashboard : Screen("trainer_dashboard")
    object AdminDashboard : Screen("admin_dashboard")
    object EmployerDashboard : Screen("employer_dashboard")
    
    object ProgrammeManagement : Screen("programme_management")
    object Timetable : Screen("timetable")
    object AttendanceHub : Screen("attendance_hub")
    object LmsHub : Screen("lms_hub")
    object EmploymentExchange : Screen("employment_exchange")
    object ProfileHub : Screen("profile_hub")
    object SettingsHub : Screen("settings_hub")
    object SyncHub : Screen("sync_hub")
    object CertificationHub : Screen("certification_hub")
    object Notifications : Screen("notifications")

    // Detail screens
    object CourseDetail : Screen("course_detail/{courseId}") {
        fun createRoute(courseId: String) = "course_detail/$courseId"
    }
    object LessonView : Screen("lesson_view/{courseId}/{lessonId}") {
        fun createRoute(courseId: String, lessonId: String) = "lesson_view/$courseId/$lessonId"
    }
    object AssessmentView : Screen("assessment_view/{courseId}/{assessmentId}") {
        fun createRoute(courseId: String, assessmentId: String) = "assessment_view/$courseId/$assessmentId"
    }
    object JobDetail : Screen("job_detail/{jobId}") {
        fun createRoute(jobId: String) = "job_detail/$jobId"
    }
}
