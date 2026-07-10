package com.example.data.network

import com.example.data.model.TodoDataModelItem
import com.example.data.model.post.PostDataModel
import retrofit2.Response

import retrofit2.http.GET

interface ApiService {

    @GET("todos")
    suspend fun
             getTodos(): List<TodoDataModelItem>

    @GET("posts")
    suspend fun  getPost(): Response<PostDataModel>


}




