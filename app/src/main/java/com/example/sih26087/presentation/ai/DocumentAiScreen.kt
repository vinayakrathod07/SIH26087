package com.example.sih26087.presentation.ai

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentAiScreen(onBack: () -> Unit) {
    var selectedFile by remember { mutableStateOf<String?>(null) }
    var isAnalyzing by remember { mutableStateOf(false) }
    var analysisResult by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AI Document Analysis") },
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
            Text(
                "Upload certificates, study material, or resumes for AI-powered summarization and insights.",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
                onClick = { selectedFile = "certificate_pacs_101.pdf" }
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.UploadFile,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        selectedFile ?: "Tap to Select Document",
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            if (selectedFile != null && analysisResult.isEmpty()) {
                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = {
                        isAnalyzing = true
                        // Simulating backend AI processing
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isAnalyzing
                ) {
                    if (isAnalyzing) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                    } else {
                        Text("Analyze with Gemini AI")
                    }
                }
                
                if (isAnalyzing) {
                    LaunchedEffect(Unit) {
                        kotlinx.coroutines.delay(2000)
                        analysisResult = "Summary of: $selectedFile\n\n1. This document verifies your successful completion of the 'Cooperative Banking Operations' module.\n2. Key skills acquired: Double-entry bookkeeping, PACS loan disbursement, and KYC compliance.\n3. Recommendation: You are now ready to apply for junior auditor roles in rural banks."
                        isAnalyzing = false
                    }
                }
            }

            if (analysisResult.isNotEmpty()) {
                Spacer(Modifier.height(24.dp))
                Text("Analysis Results", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(Modifier.height(12.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f))
                ) {
                    Text(
                        analysisResult,
                        modifier = Modifier.padding(16.dp),
                        fontSize = 15.sp,
                        lineHeight = 22.sp
                    )
                }
                
                Spacer(Modifier.height(16.dp))
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = { analysisResult = "" }, modifier = Modifier.weight(1f)) {
                        Text("Clear")
                    }
                    Button(onClick = { /* Add to skill repository */ }, modifier = Modifier.weight(1f)) {
                        Text("Save to Profile")
                    }
                }
            }
        }
    }
}
