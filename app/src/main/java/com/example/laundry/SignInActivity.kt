package com.example.laundry

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class SignInActivity : ComponentActivity() {
    private lateinit var viewModel: GoogleSignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GoogleSignInViewModel::class.java)

        setContent {
            SignInScreen()
        }
    }

    @Composable
    fun SignInScreen() {
        val context = LocalContext.current
        var showDialog by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to Laundryes", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { signInWithGoogle(context, onSignInFailed = { showDialog = true }) }) {
                Text("Sign In")
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Sign-In Failed") },
                text = { Text("An error occurred while signing in. Please try again.") },
                confirmButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("OK")
                    }
                }
            )
        }
    }

    private fun signInWithGoogle(context: Context, onSignInFailed: () -> Unit) {
        lifecycleScope.launch {
            viewModel.handleGoogleSignIn(this@SignInActivity) { success ->
                if (success) {
                    saveUserDetailsToPreferences()
                    navigateToHome()
                } else {
                    Log.e("SignInActivity", "Google Sign-In failed")
                    onSignInFailed()
                }
            }
        }
    }

    private fun saveUserDetailsToPreferences() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("USER_NAME", user.displayName)
                putString("USER_EMAIL", user.email)
                apply()
            }
        }
    }

    private fun navigateToHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
