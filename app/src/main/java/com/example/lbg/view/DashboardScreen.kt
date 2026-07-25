package com.example.lbg.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// ✅ Stateless — testable, reusable, previewable
@Composable
fun DashboardScreen(
    count: Int,
    derivedCount: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onDecrement,
                //enabled = count > MIN_COUNT,
                modifier = Modifier.semantics {
                    contentDescription = "Decrement count"
                }
            ) {
                Text("−")
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "COUNT $count",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "× 2 = $derivedCount",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Button(
                onClick = onIncrement,
                //enabled = count < MAX_COUNT,
                modifier = Modifier.semantics {
                    contentDescription = "Increment count"
                }
            ) {
                Text("+")
            }
        }
    }
}

// Preview — only possible because state is hoisted
@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen(
        count = 5,
        derivedCount = 10,
        onIncrement = {},
        onDecrement = {}
    )
}