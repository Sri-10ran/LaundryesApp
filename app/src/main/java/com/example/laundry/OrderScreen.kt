package com.example.laundry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList

class OrderScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OrderScreen1()
        }
    }
}

@Composable
fun OrderScreen1() {
    var showDialog by remember { mutableStateOf(false) }

    val items = listOf(
        LaundryItem("Towel", 20, R.drawable.towel),
        LaundryItem("Socks", 15, R.drawable.socks),
        LaundryItem("Tie", 50, R.drawable.tie),
        LaundryItem("Bed Sheet", 80, R.drawable.bedsheet),
        LaundryItem("Pillow Cover", 30, R.drawable.pillowcover),
        LaundryItem("Curtain", 90, R.drawable.curtain)
    )

    val quantities = rememberSaveable(saver = listSaver(
        save = { it.toList() },
        restore = { it.toMutableStateList() }
    )) {
        mutableStateListOf(*IntArray(items.size) { 0 }.toTypedArray())
    }

    Scaffold(
        topBar = { TopBar3() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            var selectedTab by remember { mutableStateOf(0) }
            val tabs = listOf("Other", "Men")

            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                itemsIndexed(items) { index, item ->
                    LaundryItemCard(
                        item = item,
                        quantity = quantities[index],
                        onQuantityChange = { newQty ->
                            if (newQty in 0..10) quantities[index] = newQty
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { showDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Place Order")
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Order Summary") },
            text = {
                val totalPrice = items.zip(quantities.toList()) { item, qty -> item.price * qty }.sum()
                Text("Total Price: ₹$totalPrice")
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
fun LaundryItemCard(item: LaundryItem, quantity: Int, onQuantityChange: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = item.imageRes),
            contentDescription = item.name,
            modifier = Modifier.size(60.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.name, fontWeight = FontWeight.Bold)
            Text(text = "₹${item.price}", color = Color.Gray)

            Row {
                Tag(text = "Wash")
                Spacer(modifier = Modifier.width(4.dp))
                Tag(text = "Dry")
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { if (quantity > 0) onQuantityChange(quantity - 1) }) {
                Icon(
                    painter = painterResource(id = R.drawable.minus),
                    contentDescription = "Decrease"
                )
            }

            TextField(
                value = quantity.toString(),
                onValueChange = {
                    val newQty = it.toIntOrNull() ?: 0
                    if (newQty in 0..10) onQuantityChange(newQty)
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.width(50.dp)
            )

            IconButton(onClick = { if (quantity < 10) onQuantityChange(quantity + 1) }) {
                Icon(
                    painter = painterResource(id = R.drawable.plus),
                    contentDescription = "Increase"
                )
            }
        }
    }
}

@Composable
fun Tag(text: String) {
    Box(
        modifier = Modifier
            .background(Color.Blue, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text = text, color = Color.White)
    }
}

@Composable
fun TopBar3() {
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
            painter = painterResource(id = R.drawable.order),
            contentDescription = "Home Icon",
            modifier = Modifier.size(24.dp)
        )
    }
}

data class LaundryItem(val name: String, val price: Int, @DrawableRes val imageRes: Int)
