package com.example.sih26087.presentation.modules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgrammeManagementScreen(onBack: () -> Unit) {
    val items = listOf(
        "Advanced Cooperative Management (Duration: 3 Months, Seats: 40)",
        "Digital Agriculture Marketing & ERP Integration (Duration: 1 Month, Seats: 50)",
        "Micro-Finance & Self-Help Group (SHG) Operations (Duration: 6 Weeks, Seats: 30)",
        "Data Analytics for Agricultural Cooperatives (Duration: 2 Months, Seats: 25)"
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cooperative Programmes") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = null) }
                }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(items) { item ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(item, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = {}) { Text("Nominate / Apply Now") }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceHubScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Attendance Matrix") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = null) }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Icon(Icons.Default.QrCodeScanner, contentDescription = null, modifier = Modifier.size(100.dp), tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(24.dp))
            Text("Secure Face/QR Attendance Engine", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Verifying time-bounded geo-location token...", color = Color.Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = {}) { Text("Scan Session QR Code") }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LmsHubScreen(onBack: () -> Unit) {
    val modules = listOf(
        "Module 1: Cooperative History & Legal Framework",
        "Module 2: Double Entry Bookkeeping for Societies",
        "Module 3: Advanced Procurement & Supply Chain ERP",
        "Module 4: Emerging Tech & AI in Rural Economy"
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Learning Management System") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = null) }
                }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(modules) { mod ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(mod, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Progress: 40% Completed", fontSize = 13.sp, color = Color.Gray)
                        LinearProgressIndicator(progress = 0.4f, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp))
                        Button(onClick = {}) { Text("Start Lesson") }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmploymentExchangeScreen(onBack: () -> Unit) {
    val jobs = listOf(
        "Cooperative Accounts Executive - PACS Society (Salary: ₹25,000/mo)",
        "MIS Specialist - District Cooperative Bank (Salary: ₹35,000/mo)",
        "Rural Warehouse Logistics In-charge (Salary: ₹22,000/mo)"
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Employment Exchange Portal") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = null) }
                }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(jobs) { job ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(job, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Verified Match Score: 95% via AI Career Radar", color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = {}) { Text("Apply / Track Status") }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileHubScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ecosystem Verified Profile") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = null) }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp)) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Name: Ramesh Kumar", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text("Role: Cooperative Trainee", fontSize = 14.sp, color = Color.Gray)
                    Text("Affiliation: National Cooperative Training Institute", fontSize = 14.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Verified Skill Attributes:", fontWeight = FontWeight.Bold)
                    Text("• Cooperative Accounting (Level 3)\n• Digital Financial Tools\n• Local Warehousing Rules", fontSize = 14.sp)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiCareerMentorScreen(onBack: () -> Unit) {
    var chatHistory by remember { mutableStateOf(listOf("AI Mentor: Hello Ramesh, I have analyzed your skills, completed modules, and regional bank vacancies. How can I guide your cooperative career roadmap today?")) }
    var inputMessage by remember { mutableStateOf("") }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AI Copilot & Career Mentor") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = null) }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(chatHistory) { msg ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Text(msg, modifier = Modifier.padding(12.dp), fontSize = 14.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = inputMessage,
                    onValueChange = { inputMessage = it },
                    label = { Text("Ask anything (e.g. What jobs am I eligible for?)") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = {
                    if (inputMessage.isNotBlank()) {
                        val userMsg = "You: $inputMessage"
                        val aiMsg = when {
                            inputMessage.contains("job", ignoreCase = true) -> "AI Mentor: Based on your SQL and Cooperative Management records, you are qualified for the PACS Society Executive opening in Dharwad district."
                            inputMessage.contains("tomorrow", ignoreCase = true) -> "AI Mentor: Tomorrow you have 'Cooperative Management Principles' at 10:00 AM in Room 3B."
                            else -> "AI Mentor: Personalized career matrix generated. Recommended path includes completing Module 3 to bridge the Python skill gap."
                        }
                        chatHistory = chatHistory + userMsg + aiMsg
                        inputMessage = ""
                    }
                }) {
                    Icon(Icons.Default.Send, contentDescription = null)
                }
            }
        }
    }
}
