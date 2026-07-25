package com.example.lbg.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
// removed unused Arrangement import
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
// removed unused Color import
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.model.post.Post
import com.example.lbg.UiState
import com.example.lbg.viewmodel.PostViewModel
// removed unused kotlin.toString import

@Composable
fun PostListScreen(
    modifier : Modifier= Modifier,
    viewModel: PostViewModel = hiltViewModel()
) {
    // Hoist the state from the ViewModel
    val uiState by viewModel.posts.collectAsStateWithLifecycle()

    PostListContent(uiState = uiState, modifier = modifier)
}

@Composable
fun PostListContent(
    uiState: UiState<List<Post>>,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            // safeDrawing handles status bars, navigation bars, and cutouts
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        when (uiState) {
            is UiState.Loading -> {
                // Center the loader
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Success -> {
                if (uiState.data.isEmpty()) {
                    EmptyState()
                } else {
                    ShowList(uiState.data)
                }
            }

            is UiState.Error -> {
                // Show a minimal error UI so the caller has feedback
                ErrorState(message = uiState.message)
            }
        }
    }
}

@Composable
fun EmptyState(message: String = "No posts available") {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ErrorState(message: String = "Something went wrong") {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Preview
@Composable
fun PreviewPostList() {
    val mockData = listOf(Post(id = 1, body = "Hello World", title = "fdfdfd", userId = 2))
    PostListContent(uiState = UiState.Success(mockData))
}

@Composable
fun ShowList(postList: List<Post>, onPostClick: ((Post) -> Unit)? = null) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(
            items = postList,
            key = { it.id },
            contentType = { "item" }     // Group similar items
        ) { post ->
            PostCard(post = post, onClick = { onPostClick?.invoke(post) })
        }
    }
}

@Composable
fun PostCard(
    post: Post,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    val cardModifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .let { if (onClick != null) it.clickable { onClick() } else it }

    Card(
        modifier = cardModifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = post.userId.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = post.body,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}




@Preview
@Composable
fun PreviewPostCard() {
    val mockData = Post(id = 1, body = "Hello World", title = "fdfdfd", userId = 2)
    PostCard(post = mockData)
}