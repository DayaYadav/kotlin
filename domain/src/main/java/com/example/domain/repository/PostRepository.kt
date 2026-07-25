package com.example.domain.repository

import com.example.domain.model.post.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    suspend fun getPost():List<Post>

    fun getPostListFLow(): Flow<List<Post>>
}