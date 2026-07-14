package com.example.data.network

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.domain.repository.PostRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.io.IOException

@HiltWorker
class PostSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val repository: PostRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val result = repository.syncPosts()
        return result.fold(
            onSuccess = { Result.success() },
            onFailure = { e ->
                if (e is IOException) Result.retry() else Result.failure()
            }
        )
    }

    companion object {
        const val WORK_NAME = "post_sync_periodic_work"
    }
}