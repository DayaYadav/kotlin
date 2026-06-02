package com.example.lbg.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.model.post.Post
import com.example.lbg.UiState
import com.example.lbg.viewmodel.PostViewModel
import kotlin.toString

@Composable
fun PostListScreen(
    modifier : Modifier= Modifier,
    viewModel: PostViewModel = hiltViewModel()
) {
    // Hoist the state from the ViewModel
    val uiState by viewModel.posts.collectAsStateWithLifecycle()

    PostListContent(uiState = uiState)
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
                    //  EmptyState()
                } else {
                    ShowList(uiState.data)
                }
            }

            is UiState.Error -> {
                // Never leave an Error state empty
                // ErrorState(message = uiState.message ?: "Unknown Error")
            }
        }
    }
}

@Preview
@Composable
fun PreviewPostList() {
    val mockData = listOf(Post(id = 1, body = "Hello World", title = "fdfdfd", userId = 2))
    PostListContent(uiState = UiState.Success(mockData))
}

@Composable
fun ShowList(postList: List<Post>) {
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
                PostCard(post)
            }
        }
    }
}

@Composable
fun PostCard(
    post: Post,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
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