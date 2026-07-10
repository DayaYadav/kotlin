package com.example.domain

import com.example.domain.repository.PostRepository
import jakarta.inject.Inject

class PostUseCase @Inject constructor(private val postRepo: PostRepository) {

    suspend fun invoke() = postRepo.getPosts()
}