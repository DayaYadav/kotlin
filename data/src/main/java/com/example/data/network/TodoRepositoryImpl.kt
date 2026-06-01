package com.example.data.network

import com.example.data.mapper.toDomain
import com.example.domain.model.TodoDomainModelItem
import com.example.domain.network.TodoRepository
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(private val apiService: ApiService): TodoRepository {
    override suspend fun getTodoList():List<TodoDomainModelItem> {
        return apiService.getTodos().map { it.toDomain() }
    }
}