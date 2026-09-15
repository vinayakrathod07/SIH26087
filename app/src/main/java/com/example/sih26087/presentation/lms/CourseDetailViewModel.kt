package com.example.sih26087.presentation.lms

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sih26087.data.local.LessonEntity
import com.example.sih26087.data.local.ModuleEntity
import com.example.sih26087.data.repository.LmsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseDetailViewModel @Inject constructor(
    private val repository: LmsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val courseId: String = checkNotNull(savedStateHandle["courseId"])

    val modules: StateFlow<List<ModuleEntity>> = repository.getModulesForCourse(courseId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _lessons = MutableStateFlow<Map<String, List<LessonEntity>>>(emptyMap())
    val lessons: StateFlow<Map<String, List<LessonEntity>>> = _lessons.asStateFlow()

    init {
        viewModelScope.launch {
            repository.refreshCourseDetails(courseId)
            
            // Collect modules to trigger lesson fetching for each
            modules.collect { moduleList ->
                moduleList.forEach { module ->
                    launch {
                        repository.getLessonsForModule(module.id).collect { lessonList ->
                            val currentMap = _lessons.value.toMutableMap()
                            currentMap[module.id] = lessonList
                            _lessons.value = currentMap
                        }
                    }
                }
            }
        }
    }
}
