package com.example.sih26087.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sih26087.data.model.UserRole
import com.example.sih26087.data.local.TimetableEntity
import com.example.sih26087.data.local.CourseMetadataEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardHubScreen(
    role: UserRole,
    onNavigateToModule: (String) -> Unit,
    onLogout: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    var activeTab by remember { mutableStateOf("Home") }
    val timetable by viewModel.timetable.collectAsState()
    val courses by viewModel.courses.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("SIH26087 ERP Ecosystem", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text("Role: ${role.name}", fontSize = 12.sp, color = MaterialTheme.colorScheme.primaryContainer)
                    }
                },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                val items = listOf("Home", "Learn", "Schedule", "Jobs", "Profile")
                val icons = listOf(Icons.Default.Home, Icons.AutoMirrored.Filled.MenuBook, Icons.Default.DateRange, Icons.Default.Work, Icons.Default.Person)
                
                items.forEachIndexed { idx, item ->
                    NavigationBarItem(
                        selected = activeTab == item,
                        onClick = { 
                            activeTab = item 
                            if (item != "Home") {
                                when(item) {
                                    "Learn" -> onNavigateToModule("lms_hub")
                                    "Schedule" -> onNavigateToModule("timetable")
                                    "Jobs" -> onNavigateToModule("employment_exchange")
                                    "Profile" -> onNavigateToModule("profile_hub")
                                }
                            }
                        },
                        icon = { Icon(icons[idx], contentDescription = item) },
                        label = { Text(item, fontSize = 11.sp) }
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToModule("ai_career_mentor") },
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Default.AutoAwesome, contentDescription = "AI Mentor")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (role) {
                UserRole.TRAINEE -> TraineeDashboardView(onNavigateToModule, timetable, courses)
                UserRole.TRAINER -> TrainerDashboardView(onNavigateToModule)
                UserRole.INSTITUTION_ADMIN, UserRole.SUPER_ADMIN -> AdminDashboardView(onNavigateToModule)
                UserRole.EMPLOYER -> EmployerDashboardView(onNavigateToModule)
            }
        }
    }
}

@Composable
fun TraineeDashboardView(
    onNavigateToModule: (String) -> Unit,
    timetable: List<TimetableEntity>,
    courses: List<CourseMetadataEntity>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Welcome Back, Trainee! 👋", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Profile Completion: 85%", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    LinearProgressIndicator(
                        progress = { 0.85f },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DashboardMetricCard(
                    title = "Attendance",
                    value = "92%",
                    icon = Icons.Default.CheckCircle,
                    color = Color(0xFF1B5E20),
                    modifier = Modifier.weight(1f)
                )
                DashboardMetricCard(
                    title = "Active Courses",
                    value = "${courses.size} Enrolled",
                    icon = Icons.AutoMirrored.Filled.MenuBook,
                    color = Color(0xFFE65100),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Text("Quick Actions / त्वरित कार्रवाई", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onNavigateToModule("attendance_hub") },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Icon(Icons.Default.QrCodeScanner, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Mark Attendance", fontSize = 11.sp, overflow = TextOverflow.Ellipsis, maxLines = 1)
                }
                Button(
                    onClick = { onNavigateToModule("settings_hub") },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant, contentColor = MaterialTheme.colorScheme.onSurfaceVariant)
                ) {
                    Icon(Icons.Default.Sync, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Sync Data", fontSize = 11.sp, overflow = TextOverflow.Ellipsis, maxLines = 1)
                }
            }
        }

        item {
            Text("Today's Timetable", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    if (timetable.isEmpty()) {
                        Text("No classes scheduled for today.", fontSize = 14.sp, color = Color.Gray)
                    } else {
                        timetable.forEachIndexed { index, entry ->
                            TimetableRowItem(time = entry.time, subject = entry.subject, room = entry.room)
                            if (index < timetable.size - 1) {
                                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), thickness = 0.5.dp, color = Color.LightGray)
                            }
                        }
                    }
                }
            }
        }

        item {
            Text("Recommended Learning", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        items(courses) { course ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.PlayCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(32.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(course.title, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        Text(course.trainer, fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}

@Composable
fun TrainerDashboardView(onNavigateToModule: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("Trainer Control Console 🧑‍🏫", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Active Batches Today", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("• Batch A: Cooperative Banking (45 Trainees)", fontSize = 14.sp)
                    Text("• Batch B: Rural Micro-Finance (38 Trainees)", fontSize = 14.sp)
                }
            }
        }
        item {
            Button(
                onClick = { onNavigateToModule("attendance_hub") },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Icon(Icons.Default.QrCode, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Generate Secure Session QR Code")
            }
        }
    }
}

@Composable
fun AdminDashboardView(onNavigateToModule: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("Institution Executive Analytics", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                DashboardMetricCard("Total Trainees", "1,240", Icons.Default.People, Color(0xFF0D47A1), Modifier.weight(1f))
                DashboardMetricCard("Placement Rate", "84%", Icons.AutoMirrored.Filled.TrendingUp, Color(0xFF1B5E20), Modifier.weight(1f))
            }
        }
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("System Integrity / Audit Logs", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text("• All security parameters matching central government guidelines.", fontSize = 13.sp, color = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun EmployerDashboardView(onNavigateToModule: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("Recruiter Dashboard", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
        item {
            Button(onClick = { onNavigateToModule("employment_exchange") }, modifier = Modifier.fillMaxWidth()) {
                Text("Post a New Job Opening")
            }
        }
    }
}

@Composable
fun DashboardMetricCard(
    title: String,
    value: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = Color.Gray)
            Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Composable
fun TimetableRowItem(time: String, subject: String, room: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(subject, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text("Room: $room", fontSize = 12.sp, color = Color.Gray)
        }
        Text(time, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
    }
}
