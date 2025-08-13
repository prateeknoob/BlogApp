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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
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
            AppTest()
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


//RememberCouroutineScope is used to run the coroutine scope without any problem which we faced
//while using LaunchedEffect
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


//RememberUpdatedState used for when we are running a heavy task and
//we dont want that task to run again on recomposition
//and my requirement is to get the updated state/value
//without recomposition of overall task then i'll use
//rememberupdatedstate composable- it'll always remember the updated state

@Composable
fun Apps() {
    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            var counter = remember { mutableStateOf(0) }
            LaunchedEffect(key1 = Unit) {
                delay(2000)
                counter.value = 10
            }
            Counterrr(counter.value)
        }
    }
}

@Composable
fun Counterrr(value: Int) {
    val state = rememberUpdatedState(newValue = value)
    LaunchedEffect(key1 = Unit) {
        delay(5000)
        Log.d("PRATEEK", state.value.toString())
    }
    Text(text = value.toString())
}


fun a() {
    Log.d("PRATEEK", "THIS IS APP A")
}

fun b() {
    Log.d("PRATEEK", "THIS IS APP B")
}

@Composable
fun AppTest() {
    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            var state = remember { mutableStateOf(::a) }
            Button(onClick = { state.value = ::b }) {
                Text(text = "Change App")
            }
            LoadingScreen(state.value)
        }
    }
}

@Composable
fun LoadingScreen(onTimeout: () -> Unit) {
    val currentOnTimeout by rememberUpdatedState(onTimeout)
    LaunchedEffect(true) {
        delay(5000)
        currentOnTimeout()
    }
}
