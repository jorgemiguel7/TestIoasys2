package com.example.testioasys2.data.remote.login.dataSource

import com.example.testioasys2.domain.model.UserSession
import com.example.testioasys2.data.remote.login.model.UserRequest
import com.example.testioasys2.data.rest.LoginService
import com.example.testioasys2.data.rest.retrofitWrapper
import com.example.testioasys2.domain.exception.ServerErrorException
import com.example.testioasys2.domain.exception.UnauthorizedException
import com.example.testioasys2.domain.model.User
import com.example.testioasys2.domain.result.Result
import com.example.testioasys2.utils.Constants.ACCESS_TOKEN
import com.example.testioasys2.utils.Constants.CLIENT
import com.example.testioasys2.utils.Constants.UID
import okhttp3.ResponseBody
import retrofit2.Response
import java.net.HttpURLConnection

class LoginApiDataSourceImpl(private val service: LoginService): LoginApiDataSource {
    override suspend fun doLogin(user: User): Result<UserSession> {
        val userRequest = UserRequest(user.email, user.password)
        return when(val result = retrofitWrapper { service.doLogin(userRequest) }){
            is Result.Success -> handleSuccessServerResponse(result.data)
            is Result.Error -> result
        }
    }

    private fun handleSuccessServerResponse(response: Response<ResponseBody>) =
        when{
            response.isSuccessful -> {
                Result.Success(
                    UserSession(
                        accessToken = response.headers()[ACCESS_TOKEN].orEmpty(),
                        client = response.headers()[CLIENT].orEmpty(),
                        uid = response.headers()[UID].orEmpty()
                    )
                )
            }
            response.code() == HttpURLConnection.HTTP_UNAUTHORIZED -> {
                Result.Error(UnauthorizedException())
            }
            else -> Result.Error(ServerErrorException())
        }
}