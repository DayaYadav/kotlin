package com.example.data.repository

import com.example.data.mapper.post.toDomain
import com.example.data.network.ApiService
import com.example.domain.model.post.Post
import com.example.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(private val apiService: ApiService) : PostRepository {
    override suspend fun getPost(): List<Post> {
        val response = apiService.getPost()
        return response.body()?.posts?.map { it.toDomain() } ?: emptyList()
    }
}