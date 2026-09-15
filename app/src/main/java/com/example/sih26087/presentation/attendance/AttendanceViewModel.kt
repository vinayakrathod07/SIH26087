package com.example.sih26087.presentation.attendance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sih26087.data.local.AttendanceRecordEntity
import com.example.sih26087.data.repository.AttendanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AttendanceViewModel @Inject constructor(
    private val repository: AttendanceRepository
) : ViewModel() {

    val attendanceHistory: StateFlow<List<AttendanceRecordEntity>> = repository.getAttendanceRecords()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun processQrCode(qrData: String) {
        // In a real app, parse encrypted/signed QR data
        // For SIH demo: "BATCH_ID|SESSION_NAME"
        viewModelScope.launch {
            val parts = qrData.split("|")
            if (parts.size >= 2) {
                repository.markAttendance(parts[0], parts[1], "PRESENT")
            } else {
                repository.markAttendance("UNKNOWN", "General Session", "PRESENT")
            }
        }
    }

    fun markManualAttendance(batchId: String, session: String) {
        viewModelScope.launch {
            repository.markAttendance(batchId, session, "PRESENT")
        }
    }
}
