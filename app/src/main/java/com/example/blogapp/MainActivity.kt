package com.example.blogapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.blogapp.ui.theme.BlogAppTheme
import com.google.firebase.annotations.concurrent.Background

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App()
        }
    }
}

@Composable
fun App() {
    val theme = remember { mutableStateOf(false) }
    BlogAppTheme(theme.value, dynamicColor = false) {
        Scaffold { paddingValues ->
            Column(Modifier.background(MaterialTheme.colorScheme.background)) {
                Text(
                    text = "Hello World",
                    modifier = Modifier.padding(paddingValues),
                    style = MaterialTheme.typography.displayLarge
                )
                Button(onClick = {
                    theme.value = !theme.value
                }) {
                    Text(text = "Change Theme")
                }
            }
        }
    }
}