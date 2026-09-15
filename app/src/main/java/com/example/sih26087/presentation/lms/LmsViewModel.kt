package com.example.sih26087.presentation.lms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sih26087.data.local.CourseMetadataEntity
import com.example.sih26087.data.repository.DashboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LmsViewModel @Inject constructor(
    private val repository: DashboardRepository
) : ViewModel() {

    val courses: StateFlow<List<CourseMetadataEntity>> = repository.getEnrolledCourses()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            repository.refreshCourses()
        }
    }
    
    fun startLesson(courseId: String) {
        // Logic to navigate to lesson or update progress
    }
}
