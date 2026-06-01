package com.example.lbg.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.PostUseCase
import com.example.domain.model.post.Post
import com.example.lbg.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val postUsecase: PostUseCase): ViewModel() {

    private var _posts = MutableStateFlow<UiState<List<Post>>>(UiState.Loading)
    val posts = _posts.asStateFlow()

    init {
        fetchPost()
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
}