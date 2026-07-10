package com.example.lbg.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.PostUseCase
import com.example.domain.model.post.Post
import com.example.domain.usecase.PostListUsecase
import com.example.lbg.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val postUsecase: PostUseCase,
    private val postListUsecase: PostListUsecase
): ViewModel() {

    private val _posts = MutableStateFlow<UiState<Flow<List<Post>>>>(UiState.Loading)
    val posts = _posts.asStateFlow()

    private val _postList = MutableStateFlow<UiState<List<Post>>>(UiState.Loading)
    val postList = _postList.asStateFlow()

    init {
        //fetchPost()
        fetchListPost()
    }

    private fun fetchPost() {
        viewModelScope.launch(Dispatchers.IO) {
            _posts.value = UiState.Loading
            try {
                val postList = postUsecase.invoke()
                _posts.value = UiState.Success(postList)
            } catch (e: Exception) {
                _posts.value = UiState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    fun fetchListPost() {
        viewModelScope.launch(Dispatchers.IO) {
            _postList.value = UiState.Loading
            try {
                postListUsecase.getPostList().collect { item ->
                    _postList.value = UiState.Success(item)
                    println("POst==>$item")
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _postList.value = UiState.Error(e.message ?: "Unknown Error")
            }
        }
    }

}