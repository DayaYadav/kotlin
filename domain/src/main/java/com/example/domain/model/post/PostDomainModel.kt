package com.example.domain.model.post

import androidx.compose.runtime.Immutable

@Immutable
data class PostDomainModel(
    val limit: Int,
    val posts: List<Post>,
    val skip: Int,
    val total: Int
)