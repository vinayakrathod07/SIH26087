package com.example.sih26087.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sih26087.data.model.UserProfile
import com.example.sih26087.data.model.UserRole
import com.example.sih26087.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        viewModelScope.launch {
            repository.userProfile.collect {
                _userProfile.value = it
            }
        }
    }

    fun login(email: String, role: UserRole, onSuccess: (UserRole) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            val result = repository.login(email, role)
            _isLoading.value = false
            result.onSuccess { profile ->
                _userProfile.value = profile
                onSuccess(profile.role)
            }.onFailure {
                _error.value = it.message ?: "Login failed"
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
            _userProfile.value = null
        }
    }
}
