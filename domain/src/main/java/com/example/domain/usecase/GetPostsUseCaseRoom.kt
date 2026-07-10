package com.example.domain.usecase

import com.example.domain.model.post.Post
import com.example.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetPostsUseCaseRoom(private val repository: PostRepository) {
    operator fun invoke(): Flow<List<Post>> = repository.getPosts()
}