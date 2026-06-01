package com.example.lbg.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.model.post.Post
import com.example.lbg.UiState
import com.example.lbg.viewmodel.PostViewModel

@Composable
fun PostList(
    modifier: Modifier = Modifier,
    viewModel: PostViewModel = hiltViewModel()
) {
    val post by viewModel.posts.collectAsState()

    /*val post = UiState.Success(listOf(
        Post("sad", 1, "title1", 1),
        Post("body2", 2, "title2", 2)
    ))*/

    // ✅ OPTIMIZATION 1: Smart pagination
    /*LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleIndex ->
                val itemCount = paginationState.items.size
                if (lastVisibleIndex != null && lastVisibleIndex >= itemCount - 5) {
                    viewModel.loadMoreItems() // Load before reaching end
                }
            }
    }*/

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)


    ) {
        when (post) {
            is UiState.Loading -> {
                CircularProgressIndicator()
            }

            is UiState.Success -> {
                val postList = (post as UiState.Success<List<Post>>).data

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    LazyColumn {
                        items(
                            items = postList,
                            key = { it.id },
                            contentType = { "item" }     // Group similar items
                        ) { post ->
                            postCard(post)
                        }
                    }
                }
            }

            is UiState.Error -> {

            }
        }
    }
}

@Composable
fun postCard(post: Post) {

    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = post.title)
            Text(text = post.body)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun postCardPre() {
    val viewModel: PostViewModel = hiltViewModel()
    val postData = listOf(
        Post("sad", 1, "title1", 1),
        Post("body2", 2, "title2", 2)
    )

    PostList(modifier = Modifier.fillMaxSize(), viewModel)
}