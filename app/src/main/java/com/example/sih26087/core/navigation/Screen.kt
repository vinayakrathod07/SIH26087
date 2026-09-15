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
}
