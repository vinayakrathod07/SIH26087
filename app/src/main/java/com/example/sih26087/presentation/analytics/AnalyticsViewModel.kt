package com.example.sih26087.presentation.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sih26087.data.model.UserRole
import com.example.sih26087.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class AnalyticsState(
    val attendanceRate: Float = 0.92f,
    val courseCompletion: Float = 0.75f,
    val skillGrowth: List<Pair<String, Int>> = listOf(
        "Jan" to 20, "Feb" to 45, "Mar" to 75
    ),
    val jobMatchCount: Int = 12,
    val activeTrainees: Int = 1240, // For Admin
    val placementRate: Float = 0.84f // For Admin
)

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AnalyticsState())
    val state: StateFlow<AnalyticsState> = _state.asStateFlow()

    val userRole: StateFlow<UserRole> = authRepository.userProfile
        .map { it?.role ?: UserRole.TRAINEE }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserRole.TRAINEE)
}
