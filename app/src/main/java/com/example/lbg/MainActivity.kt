package com.example.lbg

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.lbg.ui.theme.LBGTheme
import com.example.lbg.view.DashboardScreen

// Local maximum for the counter (the original MAX_COUNT in view is private/unreachable)

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val MAX_COUNT = 20
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LBGTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    //PostListScreen(modifier = Modifier.padding(innerPadding))
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .windowInsetsPadding(WindowInsets.safeDrawing)
                    ) {

                        // Keep the count as a saved Compose state so callbacks can modify it
                        var count by rememberSaveable { mutableStateOf(0) }
                        val derivedCount by remember { derivedStateOf { count } }

                        DashboardScreen(
                            count = count,
                            derivedCount = derivedCount,
                            onIncrement = { if (count < MAX_COUNT) count++ },
                            onDecrement = { if (count > 0) count-- }
                        )
                    }
                }
            }

        }


    }
}
