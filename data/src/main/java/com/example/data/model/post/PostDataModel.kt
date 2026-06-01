package com.example.data.model.post

data class PostDataModel(
    val limit: Int,
    val posts: List<Post>,
    val skip: Int,
    val total: Int
)