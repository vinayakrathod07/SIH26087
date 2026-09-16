package com.example.sih26087.presentation.ai

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Monitor
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityAnalyzerScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    var isMonitoring by remember { mutableStateOf(false) }
    var isPaused by remember { mutableStateOf(false) }
    
    val logs = remember {
        mutableStateListOf(
            "Analyzer initialized.",
            "Waiting for user authorization..."
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AI Activity Assistant") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isMonitoring && !isPaused) 
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)
                    else MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.Monitor,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = if (isMonitoring && !isPaused) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = if (isMonitoring) {
                            if (isPaused) "Monitoring Paused" else "AI Activity Monitoring ON"
                        } else "Monitoring Inactive",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "This feature analyzes your on-screen learning context to provide proactive assistance and summaries.",
                        textAlign = TextAlign.Center,
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                    
                    Spacer(Modifier.height(24.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (!isMonitoring) {
                            Button(
                                onClick = {
                                    isMonitoring = true
                                    logs.add("Monitoring started.")
                                    val intent = Intent(context, ActivityAnalyzerService::class.java).apply {
                                        action = ActivityAnalyzerService.ACTION_START
                                    }
                                    context.startService(intent)
                                },
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(Icons.Default.PlayArrow, contentDescription = null)
                                Spacer(Modifier.width(8.dp))
                                Text("Start Monitoring")
                            }
                        } else {
                            FilledTonalButton(
                                onClick = { 
                                    isPaused = !isPaused
                                    logs.add(if(isPaused) "Monitoring paused." else "Monitoring resumed.")
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(if(isPaused) Icons.Default.PlayArrow else Icons.Default.Pause, contentDescription = null)
                                Spacer(Modifier.width(8.dp))
                                Text(if(isPaused) "Resume" else "Pause")
                            }
                            Spacer(Modifier.width(12.dp))
                            Button(
                                onClick = {
                                    isMonitoring = false
                                    isPaused = false
                                    logs.add("Monitoring stopped.")
                                    val intent = Intent(context, ActivityAnalyzerService::class.java).apply {
                                        action = ActivityAnalyzerService.ACTION_STOP
                                    }
                                    context.stopService(intent)
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                            ) {
                                Icon(Icons.Default.Stop, contentDescription = null)
                                Spacer(Modifier.width(8.dp))
                                Text("Stop")
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
            
            Text("Activity Context Logs", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(Modifier.height(8.dp))
            
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                    .padding(8.dp)
            ) {
                items(logs.reversed()) { log ->
                    Text(
                        text = "> $log",
                        fontSize = 12.sp,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
            
            Spacer(Modifier.height(16.dp))
            
            Text(
                "Privacy Note: Sensitive apps like banking are automatically excluded. No video is permanently stored.",
                fontSize = 11.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
