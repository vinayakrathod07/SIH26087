package com.example.sih26087.presentation.ai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sih26087.data.model.UserProfile
import com.example.sih26087.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChatMessage(
    val id: String,
    val sender: String,
    val content: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

@HiltViewModel
class AiMentorViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _chatHistory = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatHistory: StateFlow<List<ChatMessage>> = _chatHistory.asStateFlow()

    private val _isTyping = MutableStateFlow(false)
    val isTyping: StateFlow<Boolean> = _isTyping.asStateFlow()

    private var userProfile: UserProfile? = null

    init {
        viewModelScope.launch {
            authRepository.userProfile.collect {
                userProfile = it
            }
        }
        
        // Initial greeting
        _chatHistory.value = listOf(
            ChatMessage(
                id = "1",
                sender = "AI Mentor",
                content = "Namaste! I am your AI Cooperative Career Mentor. I've analyzed your current training records and local employment trends in the cooperative sector. How can I assist you today?",
                isUser = false
            )
        )
    }

    fun sendMessage(text: String) {
        if (text.isBlank()) return

        val userMsg = ChatMessage(
            id = System.currentTimeMillis().toString(),
            sender = "You",
            content = text,
            isUser = true
        )
        
        _chatHistory.value = _chatHistory.value + userMsg
        
        generateAiResponse(text)
    }

    private fun generateAiResponse(userText: String) {
        viewModelScope.launch {
            _isTyping.value = true
            delay(1500) // Simulate complex AI reasoning/backend latency
            
            val responseText = when {
                userText.contains("job", ignoreCase = true) || userText.contains("vacancy", ignoreCase = true) -> {
                    "Based on your verified skills in 'Digital Literacy' and 'Cooperative Principles', you are a 92% match for the 'PACS Executive' role in Dharwad. There is another opening at the District Cooperative Bank which requires 'Advanced Auditing'—would you like me to recommend a course for that?"
                }
                userText.contains("tomorrow", ignoreCase = true) || userText.contains("schedule", ignoreCase = true) -> {
                    "Checking your timetable... Tomorrow, March 15th, you have 'Cooperative Management Principles' at 10:00 AM in Room 3B. Don't forget your assignment is due by evening!"
                }
                userText.contains("certificate", ignoreCase = true) -> {
                    "You currently have 2 verified certificates. Your 'Digital Literacy' certificate was issued by the Central Government. Your 'Rural Entrepreneurship' certificate is pending final institution approval."
                }
                userText.contains("skill", ignoreCase = true) -> {
                    "I recommend learning 'Tally for Cooperatives' and 'GST Compliance'. These are the most in-demand skills currently requested by recruiters in your district."
                }
                else -> {
                    "I understand. As a ${userProfile?.role?.name ?: "Trainee"}, your progress in the LMS is excellent (85% completion). Focusing on the 'Financial Audit' module next will significantly boost your employment score."
                }
            }

            val aiMsg = ChatMessage(
                id = (System.currentTimeMillis() + 1).toString(),
                sender = "AI Mentor",
                content = responseText,
                isUser = false
            )
            
            _chatHistory.value = _chatHistory.value + aiMsg
            _isTyping.value = false
        }
    }
}
