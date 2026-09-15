package com.example.sih26087.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sih26087.data.model.UserRole
import com.example.sih26087.presentation.auth.*
import com.example.sih26087.presentation.dashboard.DashboardHubScreen
import com.example.sih26087.presentation.modules.*

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
            LoginScreen(
                onLoginSuccess = { role ->
                    navController.navigate(Screen.TraineeDashboard.route) { // Simplified for demo
                        popUpTo(Screen.Login.route) { inclusive = true }
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
            AttendanceHubScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.LmsHub.route) {
            LmsHubScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.EmploymentExchange.route) {
            EmploymentExchangeScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.ProfileHub.route) {
            ProfileHubScreen(onBack = { navController.popBackStack() })
        }

        composable("ai_career_mentor") {
            AiCareerMentorScreen(onBack = { navController.popBackStack() })
        }
    }
}
