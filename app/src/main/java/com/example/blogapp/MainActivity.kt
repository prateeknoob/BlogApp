package com.example.blogapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.blogapp.ui.theme.BlogAppTheme
import com.google.firebase.annotations.concurrent.Background
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CouroutineScopeComposable()
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


//LaunchedEffect is used to run for 1 time or when the key is changed
@Composable
fun Counter() {
    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            var count = remember { mutableStateOf(0) }
            var key = count.value % 3 == 0
            LaunchedEffect(key1 = key) {
                Log.d("Counter", "Counter count: ${count.value}")
            }
            Button(onClick = { count.value++ }) {
                Text(text = "Increment Value")
            }
        }
    }
}


@Composable
fun CouroutineScopeComposable() {
    Scaffold { padding ->
        Column(modifier = Modifier.padding(padding)) {

            val counter = remember { mutableStateOf(0) }
            var scope = rememberCoroutineScope()

            var text = "Counter is running ${counter.value}"
            if (counter.value == 10) {
                text = "Counter Stopped"
            }
            Column {
                Text(text = text)
                Button(onClick = {
                    scope.launch {
                        Log.d("CouroutineScopeComposable", "Counter started...")
                        try {
                            for (i in 1..10) {
                                counter.value++
                                delay(1000)
                            }
                        } catch (e: Exception) {
                            Log.d("CouroutineScopeComposable", "Exception-${e.message.toString()}")
                        }
                    }
                }) {
                    Text(text = "Start Counter")
                }
            }
        }
    }
}