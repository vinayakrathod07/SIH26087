package com.example.sih26087.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.sih26087.data.model.UserRole
import com.example.sih26087.presentation.ai.AiMentorScreen
import com.example.sih26087.presentation.attendance.AttendanceViewModel
import com.example.sih26087.presentation.attendance.QrScannerScreen
import com.example.sih26087.presentation.auth.*
import com.example.sih26087.presentation.dashboard.DashboardHubScreen
import com.example.sih26087.presentation.employment.JobDetailScreen
import com.example.sih26087.presentation.lms.CourseDetailScreen
import com.example.sih26087.presentation.lms.LessonScreen
import com.example.sih26087.presentation.modules.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val userProfile by authViewModel.userProfile.collectAsState()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(onTimeout = {
                navController.navigate(Screen.Onboarding.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }

        composable(Screen.Onboarding.route) {
            OnboardingScreen(onGetStarted = {
                navController.navigate(Screen.Login.route)
            })
        }

        composable(Screen.Login.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            LoginScreen(
                onLoginSuccess = { role ->
                    viewModel.login("demo@coop.gov.in", role) {
                        navController.navigate(Screen.TraineeDashboard.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(onRegisterSuccess = {
                navController.navigate(Screen.Login.route)
            })
        }

        composable(Screen.TraineeDashboard.route) {
            val role = userProfile?.role ?: UserRole.TRAINEE
            DashboardHubScreen(
                role = role,
                onNavigateToModule = { route ->
                    navController.navigate(route)
                },
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.ProgrammeManagement.route) {
            ProgrammeManagementScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.AttendanceHub.route) {
            AttendanceHubScreen(
                onBack = { navController.popBackStack() },
                onNavigateToScanner = { navController.navigate("qr_scanner") }
            )
        }

        composable("qr_scanner") {
            val attendanceViewModel: AttendanceViewModel = hiltViewModel()
            QrScannerScreen(
                onQrCodeScanned = { data ->
                    attendanceViewModel.processQrCode(data)
                    navController.popBackStack()
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.LmsHub.route) {
            LmsHubScreen(
                onBack = { navController.popBackStack() },
                onNavigateToCourse = { courseId ->
                    navController.navigate(Screen.CourseDetail.createRoute(courseId))
                }
            )
        }

        composable(
            route = Screen.CourseDetail.route,
            arguments = listOf(navArgument("courseId") { type = NavType.StringType })
        ) { backStackEntry ->
            val courseId = backStackEntry.arguments?.getString("courseId") ?: ""
            CourseDetailScreen(
                onBack = { navController.popBackStack() },
                onLessonNavigate = { lessonId ->
                    navController.navigate(Screen.LessonView.createRoute(courseId, lessonId))
                }
            )
        }

        composable(
            route = Screen.LessonView.route,
            arguments = listOf(
                navArgument("courseId") { type = NavType.StringType },
                navArgument("lessonId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val courseId = backStackEntry.arguments?.getString("courseId") ?: ""
            val lessonId = backStackEntry.arguments?.getString("lessonId") ?: ""
            LessonScreen(
                courseId = courseId,
                lessonId = lessonId,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.EmploymentExchange.route) {
            EmploymentExchangeScreen(
                onBack = { navController.popBackStack() },
                onNavigateToJob = { jobId ->
                    navController.navigate(Screen.JobDetail.createRoute(jobId))
                }
            )
        }

        composable(
            route = Screen.JobDetail.route,
            arguments = listOf(navArgument("jobId") { type = NavType.StringType })
        ) { backStackEntry ->
            val jobId = backStackEntry.arguments?.getString("jobId") ?: ""
            JobDetailScreen(
                jobId = jobId,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.ProfileHub.route) {
            ProfileHubScreen(
                onBack = { navController.popBackStack() },
                onNavigateToCertificates = { navController.navigate(Screen.CertificationHub.route) }
            )
        }

        composable(Screen.Timetable.route) {
            TimetableScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.SyncHub.route) {
            SyncHubScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.CertificationHub.route) {
            CertificationHubScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.Notifications.route) {
            NotificationsScreen(onBack = { navController.popBackStack() })
        }

        composable(
            route = Screen.AssessmentView.route,
            arguments = listOf(
                navArgument("courseId") { type = NavType.StringType },
                navArgument("assessmentId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val courseId = backStackEntry.arguments?.getString("courseId") ?: ""
            val assessmentId = backStackEntry.arguments?.getString("assessmentId") ?: ""
            AssessmentScreen(
                courseId = courseId,
                assessmentId = assessmentId,
                onBack = { navController.popBackStack() }
            )
        }

        composable("ai_career_mentor") {
            AiMentorScreen(onBack = { navController.popBackStack() })
        }
    }
}
