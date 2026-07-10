package com.example.domain.usecase

import com.example.domain.repository.PostRepository
import jakarta.inject.Inject

class PostListUsecase @Inject constructor(private val postRepo: PostRepository) {

    suspend fun getPostList() = postRepo.getPosts()

}
