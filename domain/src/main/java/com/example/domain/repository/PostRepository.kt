package com.example.domain.repository

import com.example.domain.model.post.Post

interface PostRepository {

    suspend fun getPost():List<Post>
}