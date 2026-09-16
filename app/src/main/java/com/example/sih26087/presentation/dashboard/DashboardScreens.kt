package com.example.sih26087.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.example.sih26087.core.navigation.Screen
import com.example.sih26087.data.local.CourseMetadataEntity
import com.example.sih26087.data.local.TimetableEntity
import com.example.sih26087.data.model.UserRole
import com.example.sih26087.presentation.components.InfoBadge
import com.example.sih26087.presentation.components.SectionHeader

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
                        Text("Session: Active", fontSize = 12.sp, color = MaterialTheme.colorScheme.primaryContainer)
                    }
                },
                actions = {
                    IconButton(onClick = { onNavigateToModule(Screen.Notifications.route) }) {
                        Icon(Icons.Default.NotificationsNone, contentDescription = "Notifications")
                    }
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
                val navItems = when (role) {
                    UserRole.TRAINEE -> listOf(
                        "Home" to Icons.Default.Home to "Home",
                        "Learn" to Icons.AutoMirrored.Filled.MenuBook to Screen.LmsHub.route,
                        "Jobs" to Icons.Default.Work to Screen.EmploymentExchange.route,
                        "Profile" to Icons.Default.Person to Screen.ProfileHub.route
                    )
                    UserRole.TRAINER -> listOf(
                        "Home" to Icons.Default.Home to "Home",
                        "Batches" to Icons.Default.Group to Screen.ProgrammeManagement.route,
                        "Attendance" to Icons.Default.QrCode to Screen.AttendanceHub.route,
                        "Profile" to Icons.Default.Person to Screen.ProfileHub.route
                    )
                    UserRole.EMPLOYER -> listOf(
                        "Home" to Icons.Default.Home to "Home",
                        "Postings" to Icons.Default.PostAdd to Screen.EmploymentExchange.route,
                        "Candidates" to Icons.Default.People to Screen.EmploymentExchange.route, // Using same for now
                        "Profile" to Icons.Default.Person to Screen.ProfileHub.route
                    )
                    else -> listOf(
                        "Home" to Icons.Default.Home to "Home",
                        "Analytics" to Icons.Default.Analytics to Screen.Analytics.route,
                        "System" to Icons.Default.Settings to Screen.SettingsHub.route,
                        "Profile" to Icons.Default.Person to Screen.ProfileHub.route
                    )
                }
                
                navItems.forEach { (labelIcon, route) ->
                    val (label, icon) = labelIcon
                    NavigationBarItem(
                        selected = if(route == "Home") activeTab == "Home" else false,
                        onClick = { 
                            if (route == "Home") {
                                activeTab = "Home"
                            } else {
                                onNavigateToModule(route)
                            }
                        },
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label, fontSize = 11.sp) }
                    )
                }
            }
        },
        floatingActionButton = {
            Column(horizontalAlignment = Alignment.End) {
                FloatingActionButton(
                    onClick = { onNavigateToModule(Screen.VoiceAssistant.route) },
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = Color.White,
                    shape = CircleShape,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(Icons.Default.Mic, contentDescription = "Voice Assistant")
                }
                Spacer(modifier = Modifier.height(12.dp))
                FloatingActionButton(
                    onClick = { onNavigateToModule(Screen.AiCareerMentor.route) },
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = Color.White,
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = "AI Mentor")
                }
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
                    Text("Welcome Back, Ramesh! 👋", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Digital Literacy Module: 85% Done", fontSize = 14.sp, fontWeight = FontWeight.Medium)
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
            SectionHeader(title = "Quick Hub", subtitle = "Access critical services instantly")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                QuickActionButton(
                    label = "Attendance",
                    icon = Icons.Default.QrCodeScanner,
                    onClick = { onNavigateToModule(Screen.AttendanceHub.route) },
                    modifier = Modifier.weight(1f)
                )
                QuickActionButton(
                    label = "Analytics",
                    icon = Icons.Default.Analytics,
                    onClick = { onNavigateToModule(Screen.Analytics.route) },
                    modifier = Modifier.weight(1f)
                )
                QuickActionButton(
                    label = "Sync",
                    icon = Icons.Default.Sync,
                    onClick = { onNavigateToModule(Screen.SyncHub.route) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            SectionHeader(title = "Today's Schedule", subtitle = "March 15, 2026")
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    if (timetable.isEmpty()) {
                        Text("No classes scheduled for today.", fontSize = 14.sp, color = Color.Gray)
                    } else {
                        timetable.take(3).forEachIndexed { index, entry ->
                            TimetableRowItem(time = entry.time, subject = entry.subject, room = entry.room)
                            if (index < timetable.size.coerceAtMost(3) - 1) {
                                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), thickness = 0.5.dp, color = Color.LightGray)
                            }
                        }
                    }
                }
            }
        }

        item {
            SectionHeader(title = "Next in Learning", subtitle = "Resume your most recent module")
        }

        items(courses.take(2)) { course ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp),
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
        
        item {
            Spacer(modifier = Modifier.height(32.dp))
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
            SectionHeader(title = "Trainer Console", subtitle = "Manage batches and sessions")
        }
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Today's Active Batches", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))
                    BatchSummaryRow("Batch 2026-A", "45 Trainees", "95% Attendance")
                    BatchSummaryRow("Batch 2026-C", "32 Trainees", "88% Attendance")
                }
            }
        }
        item {
            Button(
                onClick = { onNavigateToModule(Screen.AttendanceHub.route) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.QrCode, contentDescription = null)
                Spacer(modifier = Modifier.width(12.dp))
                Text("Generate Session QR Code")
            }
        }
        item {
            SectionHeader(title = "Pending Evaluations")
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("• Cooperative Law Quiz (12 pending)", fontSize = 14.sp)
                    Text("• Final Project Proposal (5 pending)", fontSize = 14.sp)
                }
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
            SectionHeader(title = "Executive Analytics", subtitle = "Institution: NCTI Delhi")
        }
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                DashboardMetricCard("Total Enrolled", "1,240", Icons.Default.People, Color(0xFF0D47A1), Modifier.weight(1f))
                DashboardMetricCard("Avg. Score", "78%", Icons.Default.AutoGraph, Color(0xFFE65100), Modifier.weight(1f))
            }
        }
        item {
            DashboardMetricCard("Placement Target", "84% Achieved", Icons.AutoMirrored.Filled.TrendingUp, Color(0xFF1B5E20))
        }
        item {
            SectionHeader(title = "Management Control")
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                QuickActionButton("Institutions", Icons.Default.AccountBalance, { onNavigateToModule(Screen.ProgrammeManagement.route) }, Modifier.weight(1f))
                QuickActionButton("Certificates", Icons.Default.VerifiedUser, { onNavigateToModule(Screen.CertificationHub.route) }, Modifier.weight(1f))
                QuickActionButton("Reports", Icons.Default.BarChart, { onNavigateToModule(Screen.Analytics.route) }, Modifier.weight(1f))
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
            SectionHeader(title = "Recruitment Portal", subtitle = "Verified Cooperative Talent")
        }
        item {
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Active Job Postings", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text("You have 3 active openings with 42 new applicants.", fontSize = 13.sp)
                }
            }
        }
        item {
            Button(onClick = { onNavigateToModule(Screen.EmploymentExchange.route) }, modifier = Modifier.fillMaxWidth().height(50.dp)) {
                Text("Manage Openings / Post New")
            }
        }
        item {
            SectionHeader(title = "Talent Recommendations", subtitle = "AI-matched candidates")
            CandidateSmallCard("Ramesh Kumar", "95% Match", "Dharwad")
            CandidateSmallCard("Suresh Singh", "88% Match", "Hubli")
        }
    }
}

@Composable
fun QuickActionButton(label: String, icon: ImageVector, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(80.dp).clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(label, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun BatchSummaryRow(name: String, count: String, attendance: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(name, fontWeight = FontWeight.Medium, fontSize = 14.sp)
        Text(count, fontSize = 14.sp, color = Color.Gray)
        Text(attendance, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun CandidateSmallCard(name: String, match: String, loc: String) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(40.dp).background(Color.LightGray, CircleShape), contentAlignment = Alignment.Center) {
                Text(name.take(1))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text(loc, fontSize = 12.sp, color = Color.Gray)
            }
            Text(match, fontWeight = FontWeight.ExtraBold, color = Color(0xFF1B5E20), fontSize = 13.sp)
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
