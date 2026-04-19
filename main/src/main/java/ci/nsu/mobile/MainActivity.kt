package ci.nsu.mobile.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ci.nsu.mobile.data.AuthRepository
import ci.nsu.mobile.ui.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализируем зависимости вручную
        val repository = AuthRepository(this)
        val viewModel = AuthViewModel(repository)

        setContent {
            val navController = rememberNavController()

            // Глобальная логика переходов при успехе (Success)
            if (viewModel.isSuccess) {
                LaunchedEffect(Unit) {
                    navController.navigate("main") {
                        // Очищаем стек, чтобы пользователь не вернулся на экран логина кнопкой "Назад"
                        popUpTo("login") { inclusive = true }
                    }
                    viewModel.isSuccess = false
                }
            }

            // ОДИН NavHost, который управляет всеми экранами
            NavHost(
                navController = navController,
                startDestination = "login"
            ) {
                // Экран логина
                composable("login") {
                    LoginScreen(
                        viewModel = viewModel,
                        onNavigateToRegister = { navController.navigate("register") }
                    )
                }

                // Экран регистрации
                composable("register") {
                    RegistrationScreen(
                        viewModel = viewModel,
                        onBackToLogin = { navController.popBackStack() }
                    )
                }

                // Главный экран (список пользователей)
                composable("main") {
                    MainScreen(viewModel)
                }
            }
        }
    }
}