package ci.nsu.mobile.data

import android.content.Context
import ci.nsu.mobile.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(private val context: Context) {


    private val apiService = RetrofitClient.getApiService(context)

    suspend fun login(loginRequest: LoginRequest): Result<UserDto> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.login(loginRequest)
            if (response.isSuccessful) {
                val user = response.body()
                if (user?.token != null) {
                    TokenManager.saveToken(context, user.token)
                    Result.success(user)
                } else {
                    Result.failure(Exception("Токен не получен"))
                }
            } else {
                Result.failure(Exception("Ошибка: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Метод для регистрации
    suspend fun register(registerRequest: RegisterRequest): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.register(registerRequest)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Ошибка регистрации: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getGroups(): Result<List<GroupDto>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getGroups()
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Не удалось загрузить группы"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUsers(): Result<List<UserDto>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getUsers()
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Ошибка доступа к списку пользователей"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}