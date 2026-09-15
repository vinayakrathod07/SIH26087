package com.example.sih26087.presentation.modules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sih26087.presentation.lms.LmsViewModel
import com.example.sih26087.presentation.profile.ProfileViewModel
import com.example.sih26087.presentation.programme.ProgrammeViewModel
import com.example.sih26087.presentation.employment.EmploymentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgrammeManagementScreen(
    onBack: () -> Unit,
    viewModel: ProgrammeViewModel = hiltViewModel()
) {
    val programmes by viewModel.programmes.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Available Programmes", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null) }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(programmes) { prog ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.School, contentDescription = null, modifier = Modifier.size(24.dp), tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(prog.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(prog.institution, fontSize = 14.sp, color = MaterialTheme.colorScheme.secondary)
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            BadgeItem(label = prog.duration, icon = Icons.Default.Timer)
                            BadgeItem(label = prog.category, icon = Icons.Default.Category)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Eligibility: ${prog.eligibility}", fontSize = 13.sp, color = Color.Gray)
                        Text("Seats Remaining: ${prog.seats}", fontSize = 13.sp, color = Color.Gray)
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {},
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Nominate / Register")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BadgeItem(label: String, icon: ImageVector) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(label, fontSize = 11.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceHubScreen(onBack: () -> Unit, onNavigateToScanner: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Attendance Matrix") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null) }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .background(Color.White, RoundedCornerShape(16.dp))
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.QrCode2, contentDescription = null, modifier = Modifier.fillMaxSize(), tint = Color.Black)
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("Secure QR Scanner", fontWeight = FontWeight.ExtraBold, fontSize = 22.sp)
                    Text("Scan the trainer's session code to mark your presence.", textAlign = TextAlign.Center, color = Color.Gray)
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Button(
                onClick = onNavigateToScanner,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.CameraAlt, contentDescription = null)
                Spacer(modifier = Modifier.width(12.dp))
                Text("Open Camera Scanner", fontSize = 16.sp)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Face, contentDescription = null)
                Spacer(modifier = Modifier.width(12.dp))
                Text("Use Face Verification (Beta)", fontSize = 16.sp)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ProfileHubScreen(
    onBack: () -> Unit,
    onNavigateToCertificates: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val profile by viewModel.userProfile.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Ecosystem Profile") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null) }
                },
                actions = {
                    IconButton(onClick = {}) { Icon(Icons.Default.Edit, contentDescription = "Edit") }
                }
            )
        }
    ) { padding ->
        profile?.let { data ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(60.dp), tint = MaterialTheme.colorScheme.primary)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(data.name, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                        Text(data.role.name, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Medium)
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = { data.profileCompletion / 100f },
                            modifier = Modifier.width(200.dp).height(8.dp).clip(RoundedCornerShape(4.dp))
                        )
                        Text("${data.profileCompletion}% Complete", fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
                    }
                }

                item {
                    ProfileSectionCard(title = "Personal Details") {
                        ProfileInfoRow(label = "Email", value = data.email)
                        ProfileInfoRow(label = "Mobile", value = data.mobile)
                        ProfileInfoRow(label = "Location", value = "${data.district}, ${data.state}")
                        ProfileInfoRow(label = "Education", value = data.education ?: "Not set")
                    }
                }

                item {
                    ProfileSectionCard(title = "Institution & Affiliation") {
                        ProfileInfoRow(label = "Institution", value = data.institution)
                        ProfileInfoRow(label = "Coop Affiliation", value = data.cooperativeAffiliation ?: "None")
                    }
                }

                item {
                    ProfileSectionCard(title = "Verified Skills") {
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            data.skills.forEach { skill ->
                                AssistChip(
                                    onClick = {},
                                    label = { Text(skill) },
                                    leadingIcon = { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp)) }
                                )
                            }
                        }
                    }
                }
                
                item {
                    Button(
                        onClick = onNavigateToCertificates, 
                        modifier = Modifier.fillMaxWidth(), 
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                    ) {
                        Icon(Icons.Default.CardMembership, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("View Digital Certificates")
                    }
                }
            }
        } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun ProfileSectionCard(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
private fun ProfileInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 14.sp, color = Color.Gray)
        Text(value, fontSize = 14.sp, fontWeight = FontWeight.Medium, textAlign = TextAlign.End, modifier = Modifier.weight(1f).padding(start = 16.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LmsHubScreen(
    onBack: () -> Unit,
    onNavigateToCourse: (String) -> Unit,
    viewModel: LmsViewModel = hiltViewModel()
) {
    val courses by viewModel.courses.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("E-Learning Hub") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null) }
                }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            item {
                Text("My Learning Progress", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
            items(courses) { mod ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(40.dp).background(MaterialTheme.colorScheme.primaryContainer, CircleShape), contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.PlayArrow, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(mod.title, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                Text(mod.trainer, fontSize = 12.sp, color = Color.Gray)
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        LinearProgressIndicator(
                            progress = { mod.progress / 100f },
                            modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp))
                        )
                        Row(modifier = Modifier.fillMaxWidth().padding(top = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("${mod.progress}% Complete", fontSize = 11.sp)
                            Text(if(mod.progress == 100) "Certified" else "In Progress", fontSize = 11.sp, color = if(mod.progress == 100) Color.Green else MaterialTheme.colorScheme.primary)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = { onNavigateToCourse(mod.id) },
                            modifier = Modifier.align(Alignment.End),
                            contentPadding = PaddingValues(horizontal = 24.dp)
                        ) {
                            Text(if(mod.progress == 100) "Review" else "Continue")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmploymentExchangeScreen(
    onBack: () -> Unit,
    onNavigateToJob: (String) -> Unit,
    viewModel: EmploymentViewModel = hiltViewModel()
) {
    val jobs by viewModel.jobs.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Employment Exchange") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null) }
                }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            item {
                Text("AI-Matched Opportunities", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Top recommendations based on your verified skills.", fontSize = 13.sp, color = Color.Gray)
            }
            items(jobs) { job ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(job.title, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.weight(1f))
                            Badge(containerColor = MaterialTheme.colorScheme.secondaryContainer) {
                                Text("${job.matchScore}% Match", modifier = Modifier.padding(4.dp))
                            }
                        }
                        Text(job.company, fontSize = 14.sp, color = MaterialTheme.colorScheme.secondary)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Payments, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.Gray)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(job.salary, fontSize = 13.sp, color = Color.Gray)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { onNavigateToJob(job.id) }, modifier = Modifier.fillMaxWidth()) { Text("View & Apply") }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiCareerMentorScreen(onBack: () -> Unit) {
    var chatHistory by remember { mutableStateOf(listOf(ChatMessage("AI Mentor", "Hello Ramesh, I have analyzed your skills, completed modules, and regional bank vacancies. How can I guide your cooperative career roadmap today?", false))) }
    var inputMessage by remember { mutableStateOf("") }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AI Career Copilot") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null) }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(chatHistory) { msg ->
                    val isUser = msg.isUser
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = if(isUser) Alignment.End else Alignment.Start) {
                        Surface(
                            color = if(isUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = if(isUser) 16.dp else 0.dp, bottomEnd = if(isUser) 0.dp else 16.dp),
                            modifier = Modifier.widthIn(max = 280.dp)
                        ) {
                            Text(msg.content, modifier = Modifier.padding(12.dp), fontSize = 14.sp, color = if(isUser) Color.White else Color.Unspecified)
                        }
                        Text(msg.sender, fontSize = 10.sp, color = Color.Gray, modifier = Modifier.padding(top = 2.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = inputMessage,
                    onValueChange = { inputMessage = it },
                    placeholder = { Text("Ask your mentor...") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                FloatingActionButton(
                    onClick = {
                        if (inputMessage.isNotBlank()) {
                            val userMsg = ChatMessage("You", inputMessage, true)
                            val aiMsg = when {
                                inputMessage.contains("job", ignoreCase = true) -> ChatMessage("AI Mentor", "Based on your SQL and Cooperative Management records, you are qualified for the PACS Society Executive opening in Dharwad district. Shall I help you draft an application?", false)
                                inputMessage.contains("tomorrow", ignoreCase = true) -> ChatMessage("AI Mentor", "Tomorrow you have 'Cooperative Management Principles' at 10:00 AM in Room 3B. You also have an assignment due by 5:00 PM.", false)
                                else -> ChatMessage("AI Mentor", "I am analyzing your profile... You currently have a 15% skill gap in 'Advanced Auditing'. I suggest starting Module 4 next week.", false)
                            }
                            chatHistory = chatHistory + userMsg + aiMsg
                            inputMessage = ""
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White,
                    shape = CircleShape,
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(Icons.Default.Send, contentDescription = null)
                }
            }
        }
    }
}

data class ChatMessage(val sender: String, val content: String, val isUser: Boolean)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimetableScreen(onBack: () -> Unit) {
    val schedule = listOf(
        TimetableEntry("09:00 AM", "Registration & Morning Drill", "Main Ground", "All Batches"),
        TimetableEntry("10:00 AM", "Cooperative Law & PACS Framework", "Hall A", "Batch 2026-A"),
        TimetableEntry("12:00 PM", "Lunch & Networking", "Dining Hall", "General"),
        TimetableEntry("02:00 PM", "Digital Bookkeeping Workshop", "Computer Lab 1", "Batch 2026-A"),
        TimetableEntry("04:30 PM", "AI Career Guidance Session", "Auditorium", "All Batches")
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Complete Schedule") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null) }
                }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(schedule) { entry ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(entry.time, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary, fontSize = 16.sp)
                            Text(entry.subject, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Text("Location: ${entry.location}", fontSize = 13.sp, color = Color.Gray)
                        }
                        Badge(containerColor = MaterialTheme.colorScheme.tertiaryContainer) {
                            Text(entry.batch, modifier = Modifier.padding(4.dp), fontSize = 10.sp)
                        }
                    }
                }
            }
        }
    }
}

data class TimetableEntry(val time: String, val subject: String, val location: String, val batch: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SyncHubScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ecosystem Sync Engine") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null) }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Icon(Icons.Default.CloudSync, contentDescription = null, modifier = Modifier.size(80.dp), tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(24.dp))
            Text("Offline Sync Hub", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("Ensure your local learning records and attendance are synchronized with the central server.", textAlign = TextAlign.Center, color = Color.Gray)
            Spacer(modifier = Modifier.height(32.dp))
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            Text("Last synced: 12 minutes ago", fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp))
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text("Force Manual Synchronization")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CertificationHubScreen(onBack: () -> Unit) {
    val certificates = listOf(
        CertificateItem("Cooperative Management 101", "National Institute of Coop", "MAR 2026", "VERIFIED"),
        CertificateItem("Digital Literacy Foundations", "Central Govt Skills Dept", "JAN 2026", "VERIFIED"),
        CertificateItem("Rural Entrepreneurship", "Rural Dev Block", "FEB 2026", "PENDING")
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Digital Document Repository") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null) }
                }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            item {
                Text("My Verified Credentials", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Official digital certificates for your training and skills.", fontSize = 13.sp, color = Color.Gray)
            }
            items(certificates) { cert ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.WorkspacePremium, contentDescription = null, modifier = Modifier.size(40.dp), tint = if(cert.status == "VERIFIED") Color(0xFFFFD700) else Color.Gray)
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(cert.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text(cert.issuer, fontSize = 12.sp, color = Color.Gray)
                            Text("Issued: ${cert.date}", fontSize = 12.sp)
                        }
                        Badge(containerColor = if(cert.status == "VERIFIED") Color(0xFFE8F5E9) else Color(0xFFFFF3E0)) {
                            Text(cert.status, modifier = Modifier.padding(4.dp), color = if(cert.status == "VERIFIED") Color(0xFF2E7D32) else Color(0xFFE65100), fontSize = 10.sp)
                        }
                    }
                }
            }
            item {
                Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.QrCode, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Verify a Certificate via QR")
                }
            }
        }
    }
}

data class CertificateItem(val title: String, val issuer: String, val date: String, val status: String)

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

data class NotificationData(val title: String, val message: String, val time: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssessmentScreen(onBack: () -> Unit, courseId: String, assessmentId: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Self-Assessment") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null) }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Assessment: $assessmentId", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("Course: $courseId", color = Color.Gray)
            Spacer(modifier = Modifier.height(32.dp))
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("Question 1 of 10", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Which of the following is a fundamental principle of a cooperative society?", fontSize = 18.sp, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(24.dp))
                    val options = listOf("Profit Maximization", "Voluntary and Open Membership", "Government Control", "Restricted Participation")
                    options.forEach { opt ->
                        OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                            Text(opt)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = onBack, modifier = Modifier.fillMaxWidth().height(56.dp)) {
                Text("Submit Answer")
            }
        }
    }
}
