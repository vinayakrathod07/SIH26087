package com.example.sih26087.presentation.lms

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sih26087.data.local.LessonEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailScreen(
    onBack: () -> Unit,
    onLessonNavigate: (String) -> Unit,
    viewModel: CourseDetailViewModel = hiltViewModel()
) {
    val modules by viewModel.modules.collectAsState()
    val lessonsMap by viewModel.lessons.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Course Syllabus") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
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
            item {
                Text(
                    text = "Module Breakdown",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Master the curriculum step-by-step with AI-assisted learning insights.",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            items(modules) { module ->
                ModuleItem(
                    title = module.title,
                    isCompleted = module.isCompleted,
                    lessons = lessonsMap[module.id] ?: emptyList(),
                    onLessonClick = onLessonNavigate
                )
            }
        }
    }
}

@Composable
fun ModuleItem(
    title: String,
    isCompleted: Boolean,
    lessons: List<LessonEntity>,
    onLessonClick: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCompleted) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f) 
                             else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (isCompleted) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF2E7D32))
                } else {
                    Icon(Icons.Default.PlayCircleOutline, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            
            if (lessons.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                lessons.forEach { lesson ->
                    LessonRow(lesson.title, lesson.duration, lesson.isCompleted) {
                        onLessonClick(lesson.id)
                    }
                }
            }
        }
    }
}

@Composable
fun LessonRow(
    title: String, 
    duration: String, 
    isCompleted: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.PlayArrow, 
                contentDescription = null, 
                modifier = Modifier.size(16.dp),
                tint = if (isCompleted) Color.Gray else MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title, 
                fontSize = 14.sp, 
                color = if (isCompleted) Color.Gray else MaterialTheme.colorScheme.onSurface
            )
        }
        Text(text = duration, fontSize = 12.sp, color = Color.Gray)
    }
}
