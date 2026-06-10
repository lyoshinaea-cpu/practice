package ci.nsu.data.remote

import ci.nsu.data.remote.dto.AuthResponse
import ci.nsu.data.remote.dto.LoginRequest
import ci.nsu.data.remote.dto.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("api/auth/login") // Укажите ваш точный эндпоинт
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("api/auth/register") // Укажите ваш точный эндпоинт
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>
}
