package ci.nsu.mobile.ui

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ci.nsu.mobile.data.AuthRepository
import ci.nsu.mobile.models.*
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    // Общие поля
    var loginText by mutableStateOf("")
    var passwordText by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var isSuccess by mutableStateOf(false)

    // Поля для регистрации (те, которых не хватало)
    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var birthDate by mutableStateOf("")
    var gender by mutableStateOf("MALE")
    var email by mutableStateOf("")
    var phone by mutableStateOf("")
    var selectedGroupId by mutableStateOf(0)
    var groups by mutableStateOf<List<GroupDto>>(emptyList())

    // Список пользователей
    var users by mutableStateOf<List<UserDto>>(emptyList())
        private set

    fun onLoginClick() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            repository.login(LoginRequest(loginText, passwordText))
                .onSuccess { isSuccess = true }
                .onFailure { errorMessage = it.message }
            isLoading = false
        }
    }

    fun onRegisterClick() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            val person = PersonDto(firstName, lastName, null, birthDate, gender, selectedGroupId)
            val request = RegisterRequest(loginText, passwordText, email, phone, person = person)

            repository.register(request)
                .onSuccess { isSuccess = true }
                .onFailure { errorMessage = it.message }
            isLoading = false
        }
    }

    fun loadGroups() {
        android.util.Log.d("API_TEST", "Метод loadGroups во ViewModel запущен")
        viewModelScope.launch {
            repository.getGroups()
                .onSuccess {
                    android.util.Log.d("API_TEST", "Успех! Групп пришло: ${it.size}")
                    groups = it
                }
                .onFailure {
                    android.util.Log.e("API_TEST", "Ошибка в репозитории: ${it.message}")
                    errorMessage = it.message
                }
        }
    }

    fun loadUsers() {
        viewModelScope.launch {
            isLoading = true
            repository.getUsers()
                .onSuccess { users = it }
                .onFailure { errorMessage = it.message }
            isLoading = false
        }
    }
}