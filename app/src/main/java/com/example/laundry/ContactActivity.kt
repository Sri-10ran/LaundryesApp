package com.example.laundry

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource

class ContactActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactScreen {
                val intent = Intent(this, ComplaintActivity::class.java)
                startActivity(intent)
            }
        }
    }
}

@Composable
fun ContactScreen(onIssueClick: () -> Unit) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Add TopBar2 here
        TopBar2()

        Spacer(modifier = Modifier.height(16.dp))

        ContactCard("Address:", "N.R.K.R Road, near Reg.Office, Sivakasi - 626124.")
        ContactCard("Phone:", "+91 12345 67890")

        // Clickable Website Link
        ContactCard("Website:", "https://laundryes.netlify.app") {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://laundryes.netlify.app"))
            context.startActivity(intent)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onIssueClick() },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Any Issues")
        }
    }
}

@Composable
fun ContactCard(title: String, info: String, onClick: (() -> Unit)? = null) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(enabled = onClick != null) { onClick?.invoke() } // Make clickable if onClick exists
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = info, color = if (onClick != null) Color.Blue else Color.Unspecified)
        }
    }
}

@Composable
fun TopBar2() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF29ABE2)) // Blue color #29ABE2
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
            painter = painterResource(id = R.drawable.contact),
            contentDescription = "Home Icon",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewContactScreen() {
    ContactScreen(onIssueClick = {})
}
