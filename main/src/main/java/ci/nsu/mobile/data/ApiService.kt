package ci.nsu.mobile.data

import ci.nsu.mobile.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<UserDto>

    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<Unit>

    @GET("users")
    suspend fun getUsers(): Response<List<UserDto>>

    @GET("groups")
    suspend fun getGroups(): Response<List<GroupDto>>
}

data class LoginRequest(val login: String, val password: String)