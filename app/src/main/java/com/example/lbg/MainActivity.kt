package com.example.lbg

import RealtimeDataScreen
import RealtimeDataViewModel
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.lbg.ui.theme.LBGTheme
import com.example.lbg.view.PostListScreen
import com.example.lbg.view.ShowPostList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // 1. Instantiates the ViewModel using standard KTX extension delegation
    private val viewModel: RealtimeDataViewModel by viewModels()

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LBGTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   // PostListScreen(modifier = Modifier.padding(innerPadding))
                    // 3. Calls the Composable Screen and passes the initialized ViewModel
                   // RealtimeDataScreen(viewModel = viewModel)
                    ShowPostList()
                }
            }


        enableEdgeToEdge()
      /*  demo()
        checkFlow()*/


        }
    }

    fun demo() {
        println("Hello World")

        val temp = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        lifecycleScope.launch {
            //while (true) {
                temp.forEach {
                    delay(5000)
                    println("Hello=>$it")
                }
            }
        //}
    }
    fun checkFlow()
    {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flowDemo().collect { value ->
                    println("Flow emitted: $value")
                }
            }
        }
    }

    fun flowDemo()= flow{
            val listIs = listOf(1,2,3,5,6,4)
            listIs.forEach {
                delay(5000.milliseconds)
                emit(it)
            }
        }

    // A mock data source generating fake updates
    private fun fetchRealtimeData(): Flow<String> = flow {
        var count = 1
        while (true) {
            emit("Update count: $count")
            delay(1000)
            count++
        }
    }

}

