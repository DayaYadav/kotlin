package com.example.domain.repository

import com.example.domain.model.post.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {

   // suspend fun getPost():List<Post>
    suspend fun savePost(post: Post)
    suspend fun deletePost(id: Long)
    fun getPosts(): Flow<List<Post>>
}