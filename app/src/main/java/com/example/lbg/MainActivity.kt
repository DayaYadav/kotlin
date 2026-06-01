package com.example.lbg

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lbg.ui.theme.LBGTheme
import com.example.lbg.view.PostList
import com.example.lbg.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import kotlin.properties.Delegates

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Lazy inject the ViewModel
    private val viewModel: UserViewModel by viewModels()
    private lateinit var nameTextView: TextView

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        setContent {
            LBGTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PostList(modifier = Modifier.padding(innerPadding))
                }
            }

            // 1. Launch a coroutine in the lifecycle scope
            /* lifecycleScope.launch {
                 // 2. Repeat block whenever lifecycle is STARTED, cancel on STOPPED
                 repeatOnLifecycle(Lifecycle.State.STARTED) {
                     viewModel.username.collect { currentName ->
                         // Safely update UI elements on the main thread
                         nameTextView.text = currentName
                     }
                 }
         }*/
        }
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        // Call the function to see its output in Logcat
        /* deligateDemo()

         CoroutineScope(Dispatchers.IO).launch {
             producer().collect {it->
                 println(it)
             }
         }*/

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LBGTheme {
        Greeting("Android")
    }
}

//Predicate Demo
private fun predicateDemo() {
    val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    var res1 = list.all { it > 2 } // true
    var res2 = list.any { it > 2 } // true
    var res3 = list.count { it > 2 } // 8
    val res4 = list.find { it > 8 } //9

    print("CHK==" + res1 + " " + res2 + " " + res3 + " " + res4)
}

private fun deligateDemo() {
    val lazyValue: String by lazy {
        println("Computing the value...")
        "Hello, World!" // The value is returned and cached
    }


    var name: String by Delegates.observable("<no name>") { prop, old, new ->
        println("Property '${prop.name}' changed from $old to $new")
    }

    var age: Int by Delegates.vetoable(0) { _, _, newValue ->
        newValue >= 0 // Vetoes negative values
    }

}

//

private fun deligateDemo1() {
    // 1. lazy delegate
    val lazyValue: String by lazy {
        Log.d("DelegateDemo", "Computing the lazy value...")
        "Hello, World!" // This value is returned and cached
    }

    // Accessing lazyValue for the first time will execute the lambda
    Log.d("DelegateDemo", "Accessing lazyValue for the first time.")
    Log.d("DelegateDemo", "lazyValue = $lazyValue")

    // Accessing it again will not re-execute the lambda
    Log.d("DelegateDemo", "Accessing lazyValue for the second time.")
    Log.d("DelegateDemo", "lazyValue = $lazyValue")

    Log.d("DelegateDemo", "---")

    // 2. observable delegate
    var name: String by Delegates.observable("<no name>") { prop, old, new ->
        Log.d("DelegateDemo", "Property '${prop.name}' changed from '$old' to '$new'")
    }

    Log.d("DelegateDemo", "Changing name to 'Alex'")
    name = "Alex"
    Log.d("DelegateDemo", "Changing name to 'Bob'")
    name = "Bob"

    Log.d("DelegateDemo", "---")

    // 3. vetoable delegate
    var age: Int by Delegates.vetoable(0) { _, old, newValue ->
        Log.d("DelegateDemo", "Attempting to change age from $old to $newValue")
        newValue >= 0 // Vetoes (rejects) negative values
    }

    Log.d("DelegateDemo", "Current age: $age")
    Log.d("DelegateDemo", "Setting age to 30 (should be accepted)")
    age = 30
    Log.d("DelegateDemo", "Current age: $age")

    Log.d("DelegateDemo", "Setting age to -5 (should be vetoed)")
    age = -5
    Log.d("DelegateDemo", "Current age after veto attempt: $age")
}

suspend fun producer(): Flow<String> = flow {

    val fruits = listOf("Apple", "Banan", "Cherry", "WaterMalen")
    for (i in fruits) {
        emit(i)
    }

}