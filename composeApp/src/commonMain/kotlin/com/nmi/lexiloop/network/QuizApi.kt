package com.nmi.lexiloop.network

import com.nmi.lexiloop.BuildKonfig
import com.nmi.lexiloop.entity.CompleteQuizEntity
import com.nmi.lexiloop.util.Result
import com.nmi.lexiloop.util.NetworkError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

class QuizApi(
    private val httpClient: HttpClient
) {

    suspend fun getAllQuizzes(): Result<List<CompleteQuizEntity>, NetworkError> {
        val response = try {
            httpClient.get{
                url{
                    protocol = URLProtocol.HTTP
                    host = BuildKonfig.API_URL
                    path("api/quizzes/simple")
                }
            }
        } catch (e: UnresolvedAddressException){
            return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            return Result.Error(NetworkError.SERVER_ERROR)
        } catch (e: Exception) {
            return Result.Error(NetworkError.UNKNOWN)
        }

        return when(response.status.value){
            in 200..299 -> {
                val quizzes = response.body<List<CompleteQuizEntity>>()
                Result.Success(quizzes)
            }
            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }
}