package com.englishcoachdz.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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

private val AppBg = Color(0xFFF3F4F7)
private val CardBg = Color(0xFFFFFFFF)
private val PrimaryBlue = Color(0xFF2F5DAA)
private val TextDark = Color(0xFF171B25)
private val TextSoft = Color(0xFF717784)
private val BorderSoft = Color(0xFFE2E5EA)

private enum class Screen(val title: String) {
    Home("Home"),
    Lessons("Lessons"),
    Practice("Practice"),
    Progress("Progress"),
    Profile("Profile")
}

@Composable
fun EnglishCoachApp() {
    var selectedScreen by remember { mutableStateOf(Screen.Home) }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = AppBg
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    when (selectedScreen) {
                        Screen.Home -> HomeScreen(onOpenLessons = { selectedScreen = Screen.Lessons }, onOpenPractice = { selectedScreen = Screen.Practice })
                        Screen.Lessons -> LessonsScreen(onStartPractice = { selectedScreen = Screen.Practice })
                        Screen.Practice -> PracticeScreen()
                        Screen.Progress -> ProgressScreen()
                        Screen.Profile -> ProfileScreen()
                    }
                }

                BottomNavigationBar(
                    selected = selectedScreen,
                    onSelected = { selectedScreen = it }
                )
            }
        }
    }
}

@Composable
private fun PageContainer(content: @Composable Column.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        content = content
    )
}

@Composable
private fun HomeScreen(onOpenLessons: () -> Unit, onOpenPractice: () -> Unit) {
    PageContainer {
        Text(
            text = "English Coach DZ",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = TextDark
        )
        Text(
            text = "A smart English learning path from A0 to C2, adapted to your real mistakes.",
            fontSize = 16.sp,
            color = TextSoft,
            lineHeight = 23.sp
        )

        AppCard {
            Text("Current Level", color = TextSoft, fontSize = 15.sp)
            Spacer(Modifier.height(12.dp))
            Text("A2+ / Early B1", color = TextDark, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(Modifier.height(18.dp))
            ProgressLine(progress = 0.43f)
            Spacer(Modifier.height(16.dp))
            Text("Goal: Confident communication", color = TextSoft, fontSize = 15.sp)
        }

        AppCard {
            Text("Today's Lesson", color = TextDark, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(14.dp))
            Text("Daily Routine with Present Simple", color = TextSoft, fontSize = 17.sp)
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Learn to describe your day naturally using usually, after that, sometimes, then, and because.",
                color = TextDark,
                fontSize = 17.sp,
                lineHeight = 27.sp
            )
            Spacer(Modifier.height(22.dp))
            PrimaryButton(text = "Start Daily Lesson", onClick = onOpenLessons)
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            SmallHomeCard(
                title = "Practice",
                subtitle = "Fix real mistakes",
                modifier = Modifier.weight(1f),
                onClick = onOpenPractice
            )
            SmallHomeCard(
                title = "Progress",
                subtitle = "See weak points",
                modifier = Modifier.weight(1f),
                onClick = { }
            )
        }
    }
}

@Composable
private fun LessonsScreen(onStartPractice: () -> Unit) {
    PageContainer {
        Header(title = "Lessons", subtitle = "Your A2+ to B1 communication path")

        LessonCard(
            number = "01",
            title = "Daily Routine",
            subtitle = "Present Simple, frequency words, and daily actions.",
            status = "Unlocked",
            buttonText = "Continue Lesson",
            onClick = onStartPractice
        )
        LessonCard(
            number = "02",
            title = "Work and Job",
            subtitle = "Talk about your job, schedule, tasks, and coworkers.",
            status = "Next",
            buttonText = "Locked",
            onClick = { }
        )
        LessonCard(
            number = "03",
            title = "Past Events",
            subtitle = "Speak about yesterday, last week, and completed actions.",
            status = "Locked",
            buttonText = "Locked",
            onClick = { }
        )
    }
}

@Composable
private fun PracticeScreen() {
    var answer by remember { mutableStateOf("") }
    val correction = remember(answer) { checkSimpleMistakes(answer) }

    PageContainer {
        Header(title = "Practice", subtitle = "Write. Get corrected. Improve.")

        AppCard {
            Text("Today's Task", fontSize = 21.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Spacer(Modifier.height(10.dp))
            Text(
                text = "Write 4 sentences about your evening routine. Use: usually, after that, sometimes, because.",
                color = TextSoft,
                fontSize = 16.sp,
                lineHeight = 24.sp
            )
        }

        AppCard {
            OutlinedTextField(
                value = answer,
                onValueChange = { answer = it },
                modifier = Modifier.fillMaxWidth(),
                minLines = 6,
                label = { Text("Your answer") },
                placeholder = { Text("In the evening, I usually...") }
            )
            Spacer(Modifier.height(16.dp))
            Text("Instant Feedback", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Spacer(Modifier.height(8.dp))
            Text(correction, color = TextSoft, fontSize = 16.sp, lineHeight = 24.sp)
        }
    }
}

@Composable
private fun ProgressScreen() {
    PageContainer {
        Header(title = "Progress", subtitle = "Your level and learning data")

        AppCard {
            Text("Current Level", color = TextSoft, fontSize = 15.sp)
            Spacer(Modifier.height(8.dp))
            Text("A2+ / Early B1", color = TextDark, fontSize = 29.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(Modifier.height(18.dp))
            ProgressLine(progress = 0.43f)
        }

        AppCard {
            Text("Weak Points", color = TextDark, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(14.dp))
            WeakPoint("Punctuation", "You need better commas and periods.")
            WeakPoint("Natural English", "Use expressions like go back home, have dinner, go for a walk.")
            WeakPoint("Paragraph Control", "Avoid one very long sentence.")
        }

        AppCard {
            Text("Completed", color = TextDark, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(10.dp))
            Text("10 basic lessons completed before placement upgrade.", color = TextSoft, fontSize = 16.sp)
            Spacer(Modifier.height(8.dp))
            Text("Current path: A2+ to B1 Communication Course", color = TextSoft, fontSize = 16.sp)
        }
    }
}

@Composable
private fun ProfileScreen() {
    PageContainer {
        Header(title = "Profile", subtitle = "Your learning setup")

        AppCard {
            ProfileRow("Goal", "Confident communication")
            ProfileRow("Daily Time", "45 minutes")
            ProfileRow("Interface", "English only")
            ProfileRow("Support Language", "Arabic when needed")
            ProfileRow("Path", "A2+ to B1, then B2, C1, C2")
        }

        AppCard {
            Text("Learning Rule", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Spacer(Modifier.height(10.dp))
            Text(
                text = "The app should not move you to a new lesson until you prove that you understand the previous one.",
                color = TextSoft,
                fontSize = 16.sp,
                lineHeight = 24.sp
            )
        }
    }
}

@Composable
private fun Header(title: String, subtitle: String) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(title, fontSize = 31.sp, fontWeight = FontWeight.ExtraBold, color = TextDark)
        Text(subtitle, fontSize = 16.sp, color = TextSoft, lineHeight = 23.sp)
    }
}

@Composable
private fun AppCard(content: @Composable Column.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 7.dp)
    ) {
        Column(
            modifier = Modifier.padding(22.dp),
            content = content
        )
    }
}

@Composable
private fun SmallHomeCard(title: String, subtitle: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier
            .height(128.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(23.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(title, fontSize = 21.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Text(subtitle, fontSize = 14.sp, color = TextSoft)
        }
    }
}

@Composable
private fun LessonCard(number: String, title: String, subtitle: String, status: String, buttonText: String, onClick: () -> Unit) {
    AppCard {
        Row(verticalAlignment = Alignment.Top) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(PrimaryBlue.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Text(number, color = PrimaryBlue, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
            }
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(title, fontSize = 21.sp, fontWeight = FontWeight.Bold, color = TextDark, modifier = Modifier.weight(1f))
                    Text(status, color = PrimaryBlue, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(8.dp))
                Text(subtitle, color = TextSoft, fontSize = 15.sp, lineHeight = 22.sp)
                Spacer(Modifier.height(16.dp))
                PrimaryButton(text = buttonText, onClick = onClick, enabled = buttonText != "Locked")
            }
        }
    }
}

@Composable
private fun PrimaryButton(text: String, onClick: () -> Unit, enabled: Boolean = true) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryBlue,
            disabledContainerColor = BorderSoft,
            disabledContentColor = TextSoft
        )
    ) {
        Text(text, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun ProgressLine(progress: Float) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
            .clip(RoundedCornerShape(50.dp))
            .background(BorderSoft)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress)
                .height(10.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(PrimaryBlue)
        )
    }
}

@Composable
private fun WeakPoint(title: String, subtitle: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(title, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = TextDark)
        Spacer(Modifier.height(4.dp))
        Text(subtitle, fontSize = 15.sp, color = TextSoft, lineHeight = 22.sp)
    }
}

@Composable
private fun ProfileRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = TextSoft, fontSize = 15.sp)
        Text(value, color = TextDark, fontSize = 15.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun BottomNavigationBar(selected: Screen, onSelected: (Screen) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .background(CardBg)
            .padding(horizontal = 10.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Screen.values().forEach { screen ->
            val isSelected = selected == screen
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(18.dp))
                    .clickable { onSelected(screen) }
                    .background(if (isSelected) PrimaryBlue.copy(alpha = 0.10f) else Color.Transparent)
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = screen.title.take(1),
                    color = if (isSelected) PrimaryBlue else TextSoft,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 17.sp
                )
                Text(
                    text = screen.title,
                    color = if (isSelected) PrimaryBlue else TextSoft,
                    fontSize = 11.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}

private fun checkSimpleMistakes(text: String): String {
    if (text.isBlank()) {
        return "Write your answer first. I will check punctuation, natural English, and common mistakes."
    }

    val feedback = mutableListOf<String>()
    val lower = text.lowercase()

    if ("talk english" in lower) feedback.add("Use 'speak English' instead of 'talk English'.")
    if ("go back to home" in lower) feedback.add("Use 'go back home', not 'go back to home'.")
    if ("go to home" in lower) feedback.add("Use 'go home', not 'go to home'.")
    if ("have a dinner" in lower) feedback.add("Use 'have dinner', not 'have a dinner'.")
    if ("sometime" in lower && "sometimes" !in lower) feedback.add("Use 'sometimes' for frequency. 'Sometime' means an unspecified time.")
    if (text.trim().lastOrNull() !in listOf('.', '!', '?')) feedback.add("Add punctuation at the end of your sentence.")

    return if (feedback.isEmpty()) {
        "Good. No basic mistake detected. Next step: make your paragraph more natural and connected."
    } else {
        feedback.joinToString(separator = "\n")
    }
}
