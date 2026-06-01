package com.example.domain.network

import com.example.domain.model.TodoDomainModel
import com.example.domain.model.TodoDomainModelItem

interface TodoRepository{
   suspend fun getTodoList(): List<TodoDomainModelItem>
}