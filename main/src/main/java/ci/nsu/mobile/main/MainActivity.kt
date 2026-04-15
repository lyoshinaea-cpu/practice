package ci.nsu.mobile.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
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
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    DepositApp(viewModel)
                }
            }
        }
    }
}

@Composable
fun DepositApp(viewModel: DepositViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(
                onCalculateClick = { /* Переход будет прописан позже */ },
                onHistoryClick = { /* Переход будет прописан позже */ },
                onCloseApp = { (navController.context as? ComponentActivity)?.finish() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onCalculateClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onCloseApp: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Расчёт вкладов") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onCalculateClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Рассчитать")
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = onHistoryClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("История расчётов")
            }

            Spacer(Modifier.height(12.dp))

            OutlinedButton(
                onClick = onCloseApp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Закрыть приложение")
            }
        }
    }
}