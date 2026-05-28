package com.englishcoachdz.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
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

private val AppBackground = Color(0xFFF3F5F8)
private val CardWhite = Color(0xFFFFFFFF)
private val PrimaryBlue = Color(0xFF2F5DAE)
private val DarkText = Color(0xFF1C2230)
private val SoftText = Color(0xFF69707D)
private val SoftLine = Color(0xFFE2E6EC)
private val SoftGreen = Color(0xFF2F7D5A)
private val SoftOrange = Color(0xFFE08A22)

private enum class Screen(val title: String, val mark: String) {
    Home("Home", "H"),
    Lessons("Lessons", "L"),
    Practice("Practice", "P"),
    Progress("Progress", "G"),
    Profile("Profile", "U")
}

@Composable
fun EnglishCoachApp() {
    MaterialTheme {
        var currentScreen by remember { mutableStateOf(Screen.Home) }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = AppBackground
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.weight(1f)) {
                    when (currentScreen) {
                        Screen.Home -> HomeScreen(
                            onStartLesson = { currentScreen = Screen.Lessons },
                            onOpenTest = { currentScreen = Screen.Practice },
                            onOpenProgress = { currentScreen = Screen.Progress }
                        )
                        Screen.Lessons -> LessonsScreen()
                        Screen.Practice -> PracticeScreen()
                        Screen.Progress -> ProgressScreen()
                        Screen.Profile -> ProfileScreen()
                    }
                }

                BottomNavigationBar(
                    currentScreen = currentScreen,
                    onScreenSelected = { currentScreen = it }
                )
            }
        }
    }
}

@Composable
private fun HomeScreen(
    onStartLesson: () -> Unit,
    onOpenTest: () -> Unit,
    onOpenProgress: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 22.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(modifier = Modifier.height(10.dp)) }

        item {
            Column {
                Text(
                    text = "English Coach DZ",
                    color = DarkText,
                    fontSize = 31.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.2.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "A smart English learning path from A0 to C2, adapted to your real mistakes.",
                    color = SoftText,
                    fontSize = 16.sp,
                    lineHeight = 23.sp
                )
            }
        }

        item {
            CoachCard {
                Text("Current Level", color = SoftText, fontSize = 15.sp)
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    "A2+ / Early B1",
                    color = DarkText,
                    fontSize = 31.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.8.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
                LinearProgressIndicator(
                    progress = { 0.42f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(99.dp)),
                    color = PrimaryBlue,
                    trackColor = SoftLine
                )
                Spacer(modifier = Modifier.height(18.dp))
                Text("Goal: Confident communication", color = SoftText, fontSize = 15.sp)
            }
        }

        item {
            CoachCard {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CircleMark(text = "▶", color = PrimaryBlue)
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text("Today's Lesson", color = DarkText, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Daily Routine", color = SoftText, fontSize = 15.sp)
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    "Learn to describe your day naturally using usually, after that, sometimes, then, and because.",
                    color = DarkText,
                    fontSize = 17.sp,
                    lineHeight = 27.sp
                )

                Spacer(modifier = Modifier.height(22.dp))

                Button(
                    onClick = onStartLesson,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                ) {
                    Text("Start Daily Lesson", fontSize = 17.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                SmallActionCard(
                    title = "Test",
                    subtitle = "Check your level",
                    mark = "T",
                    modifier = Modifier.weight(1f),
                    onClick = onOpenTest
                )
                SmallActionCard(
                    title = "Progress",
                    subtitle = "See weak points",
                    mark = "P",
                    modifier = Modifier.weight(1f),
                    onClick = onOpenProgress
                )
            }
        }

        item {
            CoachCard {
                Text("Focus for Today", color = DarkText, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.height(14.dp))
                BulletText("Avoid repeating the same sentence structure.")
                BulletText("Use connectors: then, after that, sometimes, because.")
                BulletText("Write short paragraphs, not long broken sentences.")
            }
        }

        item { Spacer(modifier = Modifier.height(22.dp)) }
    }
}

@Composable
private fun LessonsScreen() {
    SimplePage(title = "Lessons", subtitle = "A2 to B1 communication path") {
        LessonRow("01", "Daily Routine", "Open", PrimaryBlue)
        LessonRow("02", "Morning and Evening Habits", "Next", SoftGreen)
        LessonRow("03", "Talking About Work", "Locked", SoftText)
        LessonRow("04", "Past Simple Stories", "Locked", SoftText)
        LessonRow("05", "Future Plans", "Locked", SoftText)
    }
}

@Composable
private fun PracticeScreen() {
    SimplePage(title = "Practice", subtitle = "Write, get corrected, and improve") {
        CoachCard {
            Text("Mini Challenge", color = DarkText, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "Write five sentences about your day using: usually, after that, sometimes, then, and because.",
                color = SoftText,
                fontSize = 16.sp,
                lineHeight = 25.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("Correction engine: basic rules enabled", color = PrimaryBlue, fontSize = 15.sp, fontWeight = FontWeight.Bold)
        }
        CoachCard {
            Text("Common Mistakes", color = DarkText, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.height(10.dp))
            BulletText("go back to home → go back home")
            BulletText("have a dinner → have dinner")
            BulletText("talk English → speak English")
            BulletText("sometime → sometimes")
        }
    }
}

@Composable
private fun ProgressScreen() {
    SimplePage(title = "Progress", subtitle = "Your current learning profile") {
        CoachCard {
            Text("Current Level", color = SoftText, fontSize = 15.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text("A2+ / Early B1", color = DarkText, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.height(18.dp))
            LinearProgressIndicator(
                progress = { 0.42f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(99.dp)),
                color = PrimaryBlue,
                trackColor = SoftLine
            )
        }
        CoachCard {
            Text("Weak Points", color = DarkText, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.height(10.dp))
            BulletText("Punctuation and sentence breaks")
            BulletText("Natural expressions")
            BulletText("Paragraph building")
        }
        CoachCard {
            Text("Strong Points", color = DarkText, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.height(10.dp))
            BulletText("Basic grammar")
            BulletText("Daily routine vocabulary")
            BulletText("Clear simple sentences")
        }
    }
}

@Composable
private fun ProfileScreen() {
    SimplePage(title = "Profile", subtitle = "Your learning settings") {
        CoachCard {
            Text("Learner Goal", color = DarkText, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.height(10.dp))
            Text("Confident communication", color = SoftText, fontSize = 17.sp)
        }
        CoachCard {
            Text("Daily Time", color = DarkText, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.height(10.dp))
            Text("45 minutes per day", color = SoftText, fontSize = 17.sp)
        }
        CoachCard {
            Text("Path", color = DarkText, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.height(10.dp))
            Text("A2+ → B1 Communication Course", color = SoftText, fontSize = 17.sp)
        }
    }
}

@Composable
private fun SimplePage(
    title: String,
    subtitle: String,
    content: @Composable Column.() -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 22.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(modifier = Modifier.height(10.dp)) }
        item {
            Column {
                Text(title, color = DarkText, fontSize = 34.sp, fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(subtitle, color = SoftText, fontSize = 16.sp)
            }
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                content()
            }
        }
        item { Spacer(modifier = Modifier.height(22.dp)) }
    }
}

@Composable
private fun LessonRow(number: String, title: String, status: String, statusColor: Color) {
    CoachCard {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(PrimaryBlue.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Text(number, color = PrimaryBlue, fontWeight = FontWeight.ExtraBold)
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = DarkText, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(status, color = statusColor, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun CoachCard(content: @Composable Column.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 7.dp)
    ) {
        Column(modifier = Modifier.padding(22.dp)) {
            content()
        }
    }
}

@Composable
private fun SmallActionCard(
    title: String,
    subtitle: String,
    mark: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(150.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            CircleMark(text = mark, color = PrimaryBlue, size = 34)
            Column {
                Text(title, color = DarkText, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(subtitle, color = SoftText, fontSize = 14.sp)
            }
        }
    }
}

@Composable
private fun CircleMark(text: String, color: Color, size: Int = 46) {
    Box(
        modifier = Modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(color.copy(alpha = 0.12f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = color,
            fontSize = if (size >= 46) 18.sp else 14.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun BulletText(text: String) {
    Text(
        text = "• $text",
        color = SoftText,
        fontSize = 16.sp,
        lineHeight = 26.sp
    )
}

@Composable
private fun BottomNavigationBar(
    currentScreen: Screen,
    onScreenSelected: (Screen) -> Unit
) {
    NavigationBar(
        modifier = Modifier.navigationBarsPadding(),
        containerColor = CardWhite,
        tonalElevation = 10.dp
    ) {
        Screen.entries.forEach { screen ->
            NavigationBarItem(
                selected = currentScreen == screen,
                onClick = { onScreenSelected(screen) },
                icon = {
                    Text(
                        text = screen.mark,
                        color = if (currentScreen == screen) PrimaryBlue else SoftText,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp
                    )
                },
                label = { Text(screen.title, fontSize = 10.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = PrimaryBlue,
                    selectedTextColor = PrimaryBlue,
                    indicatorColor = PrimaryBlue.copy(alpha = 0.12f),
                    unselectedIconColor = SoftText,
                    unselectedTextColor = SoftText
                )
            )
        }
    }
}
