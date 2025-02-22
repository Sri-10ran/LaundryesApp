package com.example.laundry

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.compose.ui.text.font.FontWeight
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppEntryPoint(this)
        }
    }
}

@Composable
fun AppEntryPoint(activity: ComponentActivity) {
    var showSplashScreen by remember { mutableStateOf(true) }
    val auth = FirebaseAuth.getInstance()

    LaunchedEffect(Unit) {
        delay(3000)  // Show splash for 3 seconds
        showSplashScreen = false

        // Check authentication after splash
        if (auth.currentUser == null) {
            activity.startActivity(Intent(activity, SignInActivity::class.java))
            activity.finish()
        }
    }

    if (showSplashScreen) {
        SplashScreen()
    } else {
        MainScreen(activity)
    }
}

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.logo),  // Ensure logo exists
                contentDescription = "App Logo",
                modifier = Modifier.size(150.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("Crafting yourselves", fontSize = 24.sp, color = Color.Black)
        }
    }
}

@Composable
fun MainScreen(activity: ComponentActivity) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopBar()
        CarouselSection()
        Spacer(modifier = Modifier.height(16.dp))
        ButtonsSection(activity)
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF29ABE2))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Laundryes",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Image(
            painter = painterResource(id = R.drawable.home),
            contentDescription = "Home Icon",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun CarouselSection() {
    val images = listOf(
        R.drawable.img1,
        R.drawable.img2,
        R.drawable.img3,
        R.drawable.img4
    )
    var currentIndex by remember { mutableStateOf(0) }

    LaunchedEffect(currentIndex) {
        delay(2000)
        currentIndex = (currentIndex + 1) % images.size
    }

    Image(
        painter = painterResource(id = images[currentIndex]),
        contentDescription = "Carousel Image",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}

@Composable
fun ButtonsSection(activity: ComponentActivity) {
    val buttons = listOf(
        "Account" to R.drawable.profile,
        "Services" to R.drawable.list,
        "FAQ's" to R.drawable.faq,
        "History" to R.drawable.history,
        "Contact" to R.drawable.contact,
        "Make order" to R.drawable.order
    )

    Column(modifier = Modifier.padding(16.dp)) {
        buttons.chunked(2).forEach { rowButtons ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                rowButtons.forEach { (text, iconRes) ->
                    CustomButton(text, painterResource(id = iconRes)) {
                        when (text) {
                            "FAQ's" -> activity.startActivity(Intent(activity, FAQActivity::class.java))
                            "Contact" -> activity.startActivity(Intent(activity, ContactActivity::class.java))
                            "Make order" -> activity.startActivity(Intent(activity, OrderScreen::class.java))
                            "Account" -> activity.startActivity(Intent(activity,
                                Profile::class.java))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun CustomButton(text: String, icon: Painter, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(Color(0xFF29ABE2), shape = RoundedCornerShape(16.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = icon,
                contentDescription = text,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}
