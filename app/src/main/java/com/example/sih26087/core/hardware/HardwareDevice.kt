package com.example.sih26087.core.hardware

sealed class DeviceResult<out T> {
    data class Success<out T>(val data: T) : DeviceResult<T>()
    data class Error(val message: String, val exception: Exception? = null) : DeviceResult<Nothing>()
}

enum class DeviceType {
    ATTENDANCE_TERMINAL,
    QR_SCANNER,
    RFID_READER,
    BIOMETRIC_SCANNER,
    KIOSK_DISPLAY
}

enum class ConnectionState {
    DISCONNECTED,
    CONNECTING,
    CONNECTED,
    ERROR
}

interface HardwareDevice {
    val id: String
    val name: String
    val type: DeviceType
    val connectionState: ConnectionState

    suspend fun connect(): DeviceResult<Unit>
    suspend fun disconnect(): DeviceResult<Unit>
    suspend fun fetchData(params: Map<String, String>): DeviceResult<String>
    suspend fun sendData(data: String): DeviceResult<Unit>
}

interface AttendanceTerminal : HardwareDevice {
    suspend fun registerAttendance(userId: String): DeviceResult<Boolean>
    suspend fun getSyncLogs(): DeviceResult<List<String>>
}
