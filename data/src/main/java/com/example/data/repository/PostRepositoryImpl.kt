package com.example.data.repository


import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.data.local.dao.PostDao
import com.example.data.mapper.post.toDomainList
import com.example.data.mapper.post.toEntity
import com.example.data.network.ApiService
import com.example.domain.model.post.Post
import com.example.domain.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val dao: PostDao,
    private val api: ApiService
) : PostRepository {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun getPosts(): Flow<List<Post>> =
        dao.getPosts()
            .map { it.toDomainList() }
            //.onStart { refreshPosts() }   // runs once when collection starts, doesn't block emissions
            .onStart { runCatching { syncPosts() } }
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    private suspend fun refreshPosts() {
        try {
            val response = api.getPost()
            if (response.isSuccessful) {
                val posts = response.body()?.posts.orEmpty()
                dao.upsertPosts(posts.map { it.toEntity() })
                // Room's Flow re-emits automatically after this — no manual emit needed
            }
        } catch (e: IOException) {
            // offline — cached Flow keeps emitting from Room, UI doesn't break
        } catch (e: HttpException) {
            // server error — same, cache still shown
        }
    }

    override suspend fun savePost(post: Post) =
        dao.upsertPost(post.toEntity())

    override suspend fun deletePost(id: Long) =
        dao.deleteById(id)

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun syncPosts(): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val response = api.getPost()
            if (response.isSuccessful) {
                val posts = response.body()?.posts.orEmpty()
                dao.upsertPosts(posts.map { it.toEntity() })
                Result.success(Unit)
            } else {
                //Result.failure(HttpException(response))
                Result.failure(Exception("Failed to sync posts: ${response.code()} ${response.message()}"))
            }
        } catch (e: IOException) {
            Result.failure(e)   // offline — cache still shown by Room Flow
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }
}