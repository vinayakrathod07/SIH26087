package com.example.sih26087.presentation.notifications

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class NotificationData(val title: String, val message: String, val time: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(onBack: () -> Unit) {
    val notices = listOf(
        NotificationData("Class Rescheduled", "Your morning session 'PACS Framework' is moved to 11:00 AM.", "10 mins ago"),
        NotificationData("Certificate Issued", "Congratulations! Your 'Digital Literacy' certificate is now available.", "2 hours ago"),
        NotificationData("New Job Match", "A new opening for 'Cooperative Executive' matches your skill profile.", "5 hours ago")
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notifications") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null) }
                }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding)) {
            items(notices) { notice ->
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(notice.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(notice.time, fontSize = 12.sp, color = Color.Gray)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(notice.message, fontSize = 14.sp)
                    HorizontalDivider(modifier = Modifier.padding(top = 16.dp))
                }
            }
        }
    }
}
