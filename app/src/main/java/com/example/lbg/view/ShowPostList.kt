package com.example.lbg.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import android.widget.Toast
import com.example.domain.model.post.Post
import com.example.lbg.UiState
import com.example.lbg.viewmodel.PostViewModel

@Composable
fun ShowPostList(
    modifier: Modifier = Modifier,
    viewModel: PostViewModel = hiltViewModel(),
) {
    val post by viewModel.postList.collectAsStateWithLifecycle()
    val displayList = remember(post) {
        val initialList = if (post is UiState.Success) {
            (post as UiState.Success).data
        } else {
            emptyList()
        }
        mutableStateListOf(*initialList.toTypedArray())
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (post) {
            is UiState.Success -> {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    itemsIndexed(
                        items = displayList,
                        key = { _, item -> item.id },
                        contentType = { _, _ -> "item" }
                    ) { index, item ->
                        PostCardLayout(item, index) { deletedIndex ->
                            if (deletedIndex < displayList.size) {
                                displayList.removeAt(deletedIndex)
                            }
                        }
                    }
                }
            }

            is UiState.Loading -> {
                CircularProgressIndicator()
            }

            is UiState.Error -> {
                Text(text = (post as UiState.Error).message)
            }
        }
    }

}

@Composable
fun PostCardLayout(item: Post, index: Int, onDelete: (Int) -> Unit) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp))

    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.title,
                    modifier = Modifier.weight(0.8f)
                )
                IconButton(
                    onClick = {
                        Toast.makeText(context, "Delete Post at Index: $index", Toast.LENGTH_SHORT).show()
                        onDelete(index)
                    },
                    modifier = Modifier.weight(0.2f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete post"
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = item.body)
        }
    }


}