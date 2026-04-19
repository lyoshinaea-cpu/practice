package ci.nsu.mobile.data

import ci.nsu.mobile.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    // Авторизация
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<UserDto>

    // Регистрация
    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<Unit>

    // Получение списка пользователей (для MainScreen)
    @GET("users")
    suspend fun getUsers(): Response<List<UserDto>>

    // Получение списка групп (для выпадающего списка в регистрации)
    @GET("/groups") // Слэш в начале заставит Retrofit игнорировать путь "/api/" из BaseURL
    suspend fun getGroups(): Response<List<GroupDto>>
}