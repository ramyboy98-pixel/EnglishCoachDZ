package com.englishcoachdz.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EnglishCoachApp()
        }
    }
}

private enum class Screen {
    Home, Placement, Lesson, Practice, Progress
}

private data class CorrectionResult(
    val correctedText: String,
    val explanation: String,
    val errorTag: String? = null
)

private val CoachBlue = Color(0xFF2456A6)
private val CoachDark = Color(0xFF111827)
private val CoachSoft = Color(0xFFF6F7FB)
private val CoachCard = Color(0xFFFFFFFF)
private val CoachMuted = Color(0xFF6B7280)

@Composable
private fun EnglishCoachApp() {
    var screen by remember { mutableStateOf(Screen.Home) }
    val errorLog = remember { mutableStateListOf<String>() }

    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = CoachBlue,
            background = CoachSoft,
            surface = CoachCard,
            onPrimary = Color.White,
            onBackground = CoachDark,
            onSurface = CoachDark
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = CoachSoft
        ) {
            when (screen) {
                Screen.Home -> HomeScreen(
                    onStartPlacement = { screen = Screen.Placement },
                    onOpenLesson = { screen = Screen.Lesson },
                    onOpenProgress = { screen = Screen.Progress }
                )

                Screen.Placement -> PlacementScreen(
                    onBack = { screen = Screen.Home },
                    onFinish = { screen = Screen.Lesson }
                )

                Screen.Lesson -> LessonScreen(
                    onBack = { screen = Screen.Home },
                    onStartPractice = { screen = Screen.Practice }
                )

                Screen.Practice -> PracticeScreen(
                    errors = errorLog,
                    onBack = { screen = Screen.Lesson },
                    onProgress = { screen = Screen.Progress }
                )

                Screen.Progress -> ProgressScreen(
                    errors = errorLog,
                    onBack = { screen = Screen.Home }
                )
            }
        }
    }
}

@Composable
private fun PageContainer(content: @Composable () -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(CoachSoft)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { content() }
    }
}

@Composable
private fun Header(title: String, subtitle: String) {
    Column {
        Text(
            text = title,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = CoachDark
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = subtitle,
            fontSize = 15.sp,
            color = CoachMuted,
            lineHeight = 21.sp
        )
    }
}

@Composable
private fun CoachCard(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CoachCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            content()
        }
    }
}

@Composable
private fun PrimaryButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        colors = ButtonDefaults.buttonColors(containerColor = CoachBlue),
        shape = RoundedCornerShape(18.dp)
    ) {
        Text(text = text, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun SecondaryButton(text: String, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(18.dp)
    ) {
        Text(text = text, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = CoachBlue)
    }
}

@Composable
private fun HomeScreen(
    onStartPlacement: () -> Unit,
    onOpenLesson: () -> Unit,
    onOpenProgress: () -> Unit
) {
    PageContainer {
        Header(
            title = "English Coach DZ",
            subtitle = "A smart English learning path from A0 to C2, adapted to your real mistakes."
        )

        CoachCard {
            Text("Current Level", fontSize = 14.sp, color = CoachMuted)
            Text("A2+ / Early B1", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = CoachDark)
            LinearProgressIndicator(
                progress = { 0.38f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = CoachBlue,
                trackColor = Color(0xFFE5E7EB)
            )
            Text("Goal: Confident communication", fontSize = 14.sp, color = CoachMuted)
        }

        CoachCard {
            Text("Today\'s Lesson", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text("Daily Routine with Present Simple", color = CoachMuted)
            Text("Learn to describe your day naturally using usually, after that, sometimes, then, and because.")
            PrimaryButton("Start Daily Lesson", onOpenLesson)
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            MiniCard("Test", "Check your level", Modifier.weight(1f), onStartPlacement)
            MiniCard("Progress", "See weak points", Modifier.weight(1f), onOpenProgress)
        }

        CoachCard {
            Text("Design Note", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(
                "The interface is English-only, with a clean Microsoft-style layout inspired by IDARA DZ: calm blue, white cards, soft gray background, rounded corners, and light shadows.",
                color = CoachMuted,
                lineHeight = 21.sp
            )
        }
    }
}

@Composable
private fun MiniCard(title: String, subtitle: String, modifier: Modifier, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = modifier.height(120.dp),
        colors = CardDefaults.cardColors(containerColor = CoachCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        shape = RoundedCornerShape(22.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(6.dp))
            Text(subtitle, color = CoachMuted, fontSize = 13.sp)
        }
    }
}

@Composable
private fun PlacementScreen(onBack: () -> Unit, onFinish: () -> Unit) {
    PageContainer {
        Header(
            title = "Placement Test",
            subtitle = "This first version uses a simple diagnostic test. Later, it will score grammar, vocabulary, writing, and speaking separately."
        )

        CoachCard {
            Text("Question 1", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("Choose the correct sentence:")
            Text("A) She are a doctor.\nB) She is a doctor.\nC) She am a doctor.", color = CoachMuted)
        }

        CoachCard {
            Text("Question 2", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("Write 3 sentences about your daily routine.", color = CoachMuted)
        }

        CoachCard {
            Text("Estimated Result", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("Based on your current work, the app will start you at A2+ / Early B1.")
            PrimaryButton("Use A2+ Path", onFinish)
            SecondaryButton("Back Home", onBack)
        }
    }
}

@Composable
private fun LessonScreen(onBack: () -> Unit, onStartPractice: () -> Unit) {
    PageContainer {
        Header(
            title = "Lesson 1",
            subtitle = "Daily Routine with Present Simple"
        )

        CoachCard {
            Text("1. Grammar", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("Use the present simple to talk about habits and routines.")
            Text("Examples:", fontWeight = FontWeight.SemiBold)
            Text("I usually wake up at 4 a.m.\nI go to work in the morning.\nAfter work, I go back home.", color = CoachMuted)
        }

        CoachCard {
            Text("2. Vocabulary", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("wake up • pray • breakfast • work • break • snack • relax • exercise • walk • sleep", color = CoachMuted)
        }

        CoachCard {
            Text("3. Natural English", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("Say: I go back home.")
            Text("Do not say: I go back to home.", color = CoachMuted)
            Text("Say: I have dinner.")
            Text("Do not say: I have a dinner.", color = CoachMuted)
        }

        CoachCard {
            Text("4. Application", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("Write 5 sentences about your morning routine. The coach will correct your sentence and save repeated mistakes.", color = CoachMuted)
            PrimaryButton("Start Practice", onStartPractice)
            SecondaryButton("Back Home", onBack)
        }
    }
}

@Composable
private fun PracticeScreen(
    errors: MutableList<String>,
    onBack: () -> Unit,
    onProgress: () -> Unit
) {
    var answer by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<CorrectionResult?>(null) }

    PageContainer {
        Header(
            title = "Practice",
            subtitle = "Write one sentence about your routine. The first version checks common mistakes."
        )

        CoachCard {
            Text("Prompt", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("Write a sentence about what you do after work.", color = CoachMuted)
            OutlinedTextField(
                value = answer,
                onValueChange = { answer = it },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4,
                label = { Text("Your sentence") }
            )
            PrimaryButton("Check My Sentence") {
                val correction = checkSentence(answer)
                result = correction
                correction.errorTag?.let { errors.add(it) }
            }
        }

        result?.let { correction ->
            CoachCard {
                Text("Correction", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(correction.correctedText, fontSize = 17.sp, color = CoachDark)
                Text(correction.explanation, color = CoachMuted, lineHeight = 21.sp)
            }
        }

        CoachCard {
            Text("Examples to Try", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("After work, I go back home.\nSometimes, I have a snack.\nIn the evening, I go for a walk.", color = CoachMuted)
            SecondaryButton("Back to Lesson", onBack)
            SecondaryButton("View Progress", onProgress)
        }
    }
}

private fun checkSentence(input: String): CorrectionResult {
    val text = input.trim()
    val lower = text.lowercase()

    if (text.isBlank()) {
        return CorrectionResult(
            correctedText = "Write a sentence first.",
            explanation = "The coach needs your sentence before it can correct anything."
        )
    }

    if ("go back to home" in lower) {
        return CorrectionResult(
            correctedText = text.replace(Regex("go back to home", RegexOption.IGNORE_CASE), "go back home"),
            explanation = "Use 'go back home', not 'go back to home'. In English, 'home' often works like an adverb after verbs of movement.",
            errorTag = "Preposition: go back home"
        )
    }

    if ("have a dinner" in lower) {
        return CorrectionResult(
            correctedText = text.replace(Regex("have a dinner", RegexOption.IGNORE_CASE), "have dinner"),
            explanation = "For daily meals, say 'have dinner', not 'have a dinner'.",
            errorTag = "Natural English: have dinner"
        )
    }

    if ("talk english" in lower) {
        return CorrectionResult(
            correctedText = text.replace(Regex("talk English", RegexOption.IGNORE_CASE), "speak English"),
            explanation = "Use 'speak English' when you mean using the language. 'Talk' usually needs 'to' or 'with' a person.",
            errorTag = "Word choice: speak English"
        )
    }

    if ("sometime" in lower && "sometimes" !in lower) {
        return CorrectionResult(
            correctedText = text.replace(Regex("sometime", RegexOption.IGNORE_CASE), "sometimes"),
            explanation = "Use 'sometimes' for frequency. 'Sometime' means an unspecified time.",
            errorTag = "Frequency: sometimes"
        )
    }

    if (!text.endsWith(".") && !text.endsWith("!") && !text.endsWith("?")) {
        return CorrectionResult(
            correctedText = "$text.",
            explanation = "Your sentence is understandable. Add punctuation at the end of written sentences.",
            errorTag = "Punctuation: final mark"
        )
    }

    return CorrectionResult(
        correctedText = text,
        explanation = "Good sentence. No common mistake detected in this first version. Later, the coach will analyze grammar, fluency, and naturalness more deeply."
    )
}

@Composable
private fun ProgressScreen(errors: List<String>, onBack: () -> Unit) {
    val groupedErrors = errors.groupingBy { it }.eachCount().toList().sortedByDescending { it.second }

    PageContainer {
        Header(
            title = "Progress",
            subtitle = "Your level, lesson progress, and repeated mistakes will appear here."
        )

        CoachCard {
            Text("Current Level", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text("A2+ / Early B1", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = CoachBlue)
            Text("Target: Confident communication, then B2, C1, and C2.", color = CoachMuted)
        }

        CoachCard {
            Text("Skill Map", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            SkillRow("Grammar", 0.70f)
            SkillRow("Vocabulary", 0.64f)
            SkillRow("Natural English", 0.48f)
            SkillRow("Writing Fluency", 0.42f)
        }

        CoachCard {
            Text("Repeated Mistakes", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            if (groupedErrors.isEmpty()) {
                Text("No mistakes saved yet. Complete practice to build your learning profile.", color = CoachMuted)
            } else {
                groupedErrors.forEach { (tag, count) ->
                    Text("$tag — $count time(s)", color = CoachMuted)
                }
            }
        }

        SecondaryButton("Back Home", onBack)
    }
}

@Composable
private fun SkillRow(label: String, value: Float) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(label, fontWeight = FontWeight.SemiBold)
            Text("${(value * 100).toInt()}%", color = CoachMuted)
        }
        LinearProgressIndicator(
            progress = { value },
            modifier = Modifier
                .fillMaxWidth()
                .height(7.dp),
            color = CoachBlue,
            trackColor = Color(0xFFE5E7EB)
        )
    }
}
