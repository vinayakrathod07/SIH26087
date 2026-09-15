package com.example.sih26087.presentation.programme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sih26087.data.local.ProgrammeEntity
import com.example.sih26087.data.repository.ProgrammeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProgrammeViewModel @Inject constructor(
    private val repository: ProgrammeRepository
) : ViewModel() {

    val programmes: StateFlow<List<ProgrammeEntity>> = repository.getProgrammes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            repository.refreshProgrammes()
        }
    }
}
