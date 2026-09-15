package com.example.sih26087.presentation.lms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sih26087.data.local.CourseMetadataEntity
import com.example.sih26087.data.repository.LmsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LmsViewModel @Inject constructor(
    private val repository: LmsRepository
) : ViewModel() {

    val courses: StateFlow<List<CourseMetadataEntity>> = repository.getCourses()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        // No auto-refresh for now to avoid overwriting user progress if implemented
    }
    
    fun refreshCourses() {
        viewModelScope.launch {
            // repository.refreshCourses() // If implemented in LmsRepository
        }
    }
}
