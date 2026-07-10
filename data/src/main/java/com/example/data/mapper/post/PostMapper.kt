package com.example.data.mapper.post

import com.example.data.local.entity.PostEntity
import com.example.data.model.post.Post as DataPost

import com.example.domain.model.post.Post as DomainPost
// data/mapper/PostMapper.kt

// 1. DTO (API) -> Entity (Room) — used during network sync
fun DataPost.toEntity(): PostEntity = PostEntity(
    id = id,
    title = title,
    body = body,
    userId = userId,
    createdAt = System.currentTimeMillis()
)

// 2. Entity (Room) -> Domain — used when reading from DB
fun PostEntity.toDomain(): DomainPost = DomainPost(
    id = id,
    title = title,
    body = body,
    userId = userId,
    createdAt = createdAt
)

// 3. Domain -> Entity — used for savePost() / local writes from UI
fun DomainPost.toEntity(): PostEntity = PostEntity(
    id = id,
    title = title,
    body = body,
    userId = userId,
    createdAt = createdAt
)

// list mapper — this is what's missing
fun List<PostEntity>.toDomainList(): List<DomainPost> = map { it.toDomain() }
