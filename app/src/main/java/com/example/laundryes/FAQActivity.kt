package com.example.laundryes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape


import androidx.compose.material3.HorizontalDivider

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class FAQActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FAQScreen()
        }
    }
}

@Composable
fun FAQScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        FAQTopBar()
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            items(faqList.size) { index ->
                FAQItem(faqList[index].first, faqList[index].second)
            }
        }
    }
}

@Composable
fun FAQTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF29ABE2))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Laundryes",
            fontSize = 22.sp,
            color = Color.White,
            modifier = Modifier.weight(1f)
        )
        Image(
            painter = painterResource(id = R.drawable.faq), // Replace with your icon
            contentDescription = "FAQ Icon",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun FAQItem(question: String, answer: String) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .clickable { expanded = !expanded }
            .padding(12.dp)
    ) {
        // Question
        Text(
            text = question,
            color = Color(0xFF29ABE2),
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = if (expanded) 8.dp else 0.dp)
        )

        // Answer with background color
        if (expanded) {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF8683CE), shape = RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = answer,
                    color = Color.White, // White text for contrast
                    fontSize = 16.sp
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}


// Sample FAQ data
val faqList = listOf(
    "Do you offer pickup service?" to "Yes, Laundryes provides free pickup and delivery service.",
    "What is the turnaround time?" to "Standard turnaround time is 24 hours. We offer express service too.",
    "Do you clean delicate fabrics?" to "Yes, we have special treatments for delicate fabrics.",
    "How can I contact Laundryes?" to "You can reach us via phone or WhatsApp from 9 AM - 9 PM.",
    "Do you use eco-friendly detergents?" to "Yes, we prioritize eco-friendly and skin-safe detergents.",
    "Can I track my order?" to "Yes, you can track your order status in the app.",
    "What payment methods do you accept?" to "We accept cash, UPI, and all major credit/debit cards.",
    "Do you provide ironing services?" to "Yes, we offer ironing and steaming services for clothes."
)
