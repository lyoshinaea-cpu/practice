package ci.nsu.mobile.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ci.nsu.mobile.main.data.local.AppDatabase
import ci.nsu.mobile.main.data.local.DepositRepository
import ci.nsu.mobile.main.ui.theme.PracticeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(this)
        val repository = DepositRepository(database.depositDao())

        val viewModel: DepositViewModel by viewModels { DepositViewModel.Factory(repository) }

        setContent {
            PracticeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DepositApp(viewModel)
                }
            }
        }
    }
}

@Composable
fun DepositApp(viewModel: DepositViewModel) {
    Text(text = "Интерфейс калькулятора скоро будет здесь:)))))))", modifier = Modifier.padding(16.dp))
}