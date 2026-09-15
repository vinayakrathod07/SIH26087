package com.example.sih26087.data.model

import kotlinx.serialization.Serializable

@Serializable
enum class UserRole {
    SUPER_ADMIN,
    INSTITUTION_ADMIN,
    TRAINER,
    TRAINEE,
    EMPLOYER
}

@Serializable
data class UserProfile(
    val id: String,
    val name: String,
    val photoUrl: String? = null,
    val gender: String? = null,
    val dob: String? = null,
    val mobile: String,
    val email: String,
    val location: String = "Ward 4, Rural Development Block",
    val district: String = "Dharwad",
    val state: String = "Karnataka",
    val education: String? = "Diploma in Agriculture",
    val role: UserRole,
    val isVerified: Boolean = false,
    val profileCompletion: Int = 75,
    val institution: String = "National Cooperative Training Institute",
    val cooperativeAffiliation: String? = "Dharwad District Cooperative Bank",
    val skills: List<String> = listOf("Cooperative Principles", "Digital Literacy", "Basic Accounting"),
    val interests: List<String> = listOf("Organic Farming", "Agri-Tech"),
    val languages: List<String> = listOf("English", "Hindi", "Kannada"),
    val certifications: List<String> = listOf("Digital Literacy National Certificate"),
    val courseHistory: List<String> = listOf("Cooperative Management 101"),
    val employmentStatus: String? = "Student",
    val preferredJobRoles: List<String> = listOf("Field Executive", "MIS Assistant"),
    val entrepreneurshipInterests: Boolean = true
)
