package com.example.domain

import com.example.domain.repository.PostRepository
import jakarta.inject.Inject

class PostFlowUsecase@Inject constructor(private val postRepo: PostRepository)
{
    fun invokePostList() = postRepo.getPostListFLow()
}