package com.example.domain.model

data class TodoDomainModelItem(
    val completed: Boolean,
    val id: Int,
    val title: String,
    val userId: Int
)