package com.example.testioasys2.data.rest

import com.example.testioasys2.domain.exception.GenericErrorException
import com.example.testioasys2.domain.exception.NetworkErrorException
import com.example.testioasys2.domain.result.Result
import com.example.testioasys2.domain.exception.ServerErrorException
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> retrofitWrapper(call: suspend ()-> T): Result<T> {
    return try {
        Result.Success(call.invoke())
    } catch (ioException: IOException) {
        Result.Error(NetworkErrorException())
    } catch (httpException: HttpException){
        Result.Error(ServerErrorException())
    } catch (throwable: Throwable){
        Result.Error(GenericErrorException())
    }
}