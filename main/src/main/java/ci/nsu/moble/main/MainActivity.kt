package ci.nsu.moble.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ci.nsu.moble.main.ui.theme.PracticeTheme
import androidx.compose.ui.*



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                SimpleInputExample(modifier = Modifier.padding(innerPadding))

                }
            }
        }
    }
}

@Composable
fun SimpleInputExample(modifier: Modifier = Modifier) {
    var textState by remember { mutableStateOf("") }
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = textState,
            onValueChange = { textState = it },
            label = { Text("Введите имя") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Твое действие */ },
        ) {
            Text("Применить цвет")
        }
    }
}