package com.example.sih26087.data.repository

import com.example.sih26087.data.local.AuthDataStore
import com.example.sih26087.data.model.UserProfile
import com.example.sih26087.data.model.UserRole
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authDataStore: AuthDataStore
) {
    val authToken: Flow<String?> = authDataStore.authToken
    val userProfile: Flow<UserProfile?> = authDataStore.userProfile

    suspend fun login(email: String, role: UserRole): Result<UserProfile> {
        delay(1000) // Mock API network delay
        if (!email.contains("@")) {
            return Result.failure(Exception("Invalid email format"))
        }
        val name = when(role) {
            UserRole.SUPER_ADMIN -> "Dr. Amit Sharma (Super Admin)"
            UserRole.INSTITUTION_ADMIN -> "Smt. Meena Tech-Admin"
            UserRole.TRAINER -> "Prof. Rajesh Kumar (Senior Advisor)"
            UserRole.TRAINEE -> "Ramesh Kumar (Demo Trainee)"
            UserRole.EMPLOYER -> "Tata Agri-Growth HR Manager"
        }
        val profile = UserProfile(
            id = "user_" + System.currentTimeMillis(),
            name = name,
            email = email,
            mobile = "9876543210",
            role = role,
            isVerified = true
        )
        authDataStore.saveSession("mock_jwt_token_sih26087", profile)
        return Result.success(profile)
    }

    suspend fun register(name: String, email: String, mobile: String, role: UserRole): Result<UserProfile> {
        delay(1000)
        val profile = UserProfile(
            id = "user_" + System.currentTimeMillis(),
            name = name,
            email = email,
            mobile = mobile,
            role = role,
            isVerified = false
        )
        return Result.success(profile)
    }

    suspend fun verifyOtp(email: String, otp: String): Result<UserProfile> {
        delay(800)
        if (otp == "1234" || otp == "123456") {
            val profile = UserProfile(
                id = "user_verified",
                name = "Verified User",
                email = email,
                mobile = "9988776655",
                role = UserRole.TRAINEE,
                isVerified = true
            )
            authDataStore.saveSession("mock_jwt_token_sih26087", profile)
            return Result.success(profile)
        }
        return Result.failure(Exception("Incorrect OTP. Enter 1234 or 123456 for demo."))
    }

    suspend fun logout() {
        authDataStore.clearSession()
    }
}
