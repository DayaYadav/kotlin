import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

// 1. VIEWMODEL: Holds and exposes the data stream safely
class RealtimeDataViewModel : ViewModel() {

    // Converts a cold Flow into a hot StateFlow optimized for UI consumption
    val  uiState: StateFlow<String> = flow {
        var count = 1
        while (true) {
            emit("Update count: $count")
            delay(1000)
            count++
        }
    }.stateIn(
        scope = viewModelScope,
        // Keeps the stream alive for 5 seconds during configuration changes (like rotation)
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = "Waiting for data..."
    )
}

// 2. COMPOSE SCREEN: Collects state safely according to the Android Lifecycle
@Composable
fun RealtimeDataScreen(viewModel: RealtimeDataViewModel = RealtimeDataViewModel()) {

    // Automatically listens when UI is visible (STARTED) and drops connection when backgrounded (STOPPED)
    val currentStatus by viewModel.uiState.collectAsStateWithLifecycle()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = currentStatus,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}
