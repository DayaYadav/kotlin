package com.example.data.repository

import android.util.Log
import com.example.data.mapper.post.toDomain
import com.example.data.network.ApiService
import com.example.domain.model.post.Post
import com.example.domain.repository.PostRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retryWhen
import kotlin.time.Duration.Companion.milliseconds
import java.io.IOException
import javax.inject.Inject
import kotlin.math.pow

class PostRepositoryImpl @Inject constructor(private val apiService: ApiService) : PostRepository {
    override suspend fun getPost(): List<Post> {
        val response = apiService.getPost()
        return response.body()?.posts?.map { it.toDomain() } ?: emptyList()
    }

    override fun getPostListFLow(): Flow<List<Post>> {
        return flow {
            val response = apiService.getPost()
            emit(response.body()?.posts?.map { it.toDomain() } ?: emptyList())
        }
        .retryWhen { cause, attempt ->
            if (cause is IOException && attempt < 3) {
                delay((1000L * (2.0.pow(attempt.toInt())).toLong()).milliseconds)
                true // retry
            } else false // don't retry other exceptions or max retries reached
        }
        .catch { e ->
            Log.e("PostRepository", "Failed to fetch posts", e)
            emit(emptyList()) // Emit empty list on error
        }

    }
}