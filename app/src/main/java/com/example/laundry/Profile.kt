package com.example.laundry

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.firebase.auth.FirebaseAuth

class Profile : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProfileScreen(this)
        }
    }

    @Composable
    fun ProfileScreen(context: Context) {
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("USER_NAME", "User") // Fixed key
        val email = sharedPreferences.getString("USER_EMAIL", "Not Available") // Fixed key

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Profile Name: $userName")
            Text("Email: $email")

            Button(onClick = { logout() }) {
                Text("Logout")
            }
        }
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        getSharedPreferences("UserPrefs", Context.MODE_PRIVATE).edit().clear().apply()
        startActivity(Intent(this, SignInActivity::class.java))
        finish()
    }
}
