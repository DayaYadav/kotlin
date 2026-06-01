package com.example.data.mapper.post

import com.example.data.model.post.Post as DataPost

import com.example.domain.model.post.Post as DomainPost

fun DataPost.toDomain(): DomainPost {
    return DomainPost(
        id = this.id,
        title = this.title,
        body = this.body,
        userId = this.userId
    )
}