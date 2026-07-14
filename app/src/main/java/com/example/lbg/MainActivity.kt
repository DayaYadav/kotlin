package com.example.lbg


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.data.network.PostSyncScheduler
import com.example.lbg.ui.theme.LBGTheme
import com.example.lbg.view.ShowPostList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PostSyncScheduler.schedule(this)
        enableEdgeToEdge()
        setContent {
            LBGTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Use the provided innerPadding so the Scaffold content is inset correctly
                    Box(modifier = Modifier.padding(innerPadding)) {
                        ShowPostList()
                    }
                }
            }
        }
    }

}