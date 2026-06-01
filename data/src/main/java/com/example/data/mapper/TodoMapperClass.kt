package com.example.data.mapper

import com.example.data.model.TodoDataModelItem
import com.example.domain.model.TodoDomainModel
import com.example.domain.model.TodoDomainModelItem

    fun TodoDataModelItem.toDomain(): TodoDomainModelItem {
        return TodoDomainModelItem(
            completed = this.completed,
            id = this.id,
            title = this.title,
            userId = this.userId
        )
    }
