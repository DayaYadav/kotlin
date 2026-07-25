package com.example.lbg.view

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.* // ✅ wildcard covers everything
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DashboardScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule() // ✅ lightweight, no Activity

    // ✅ Compose-observable state — UI recomposes when this changes
    private var count by mutableStateOf(0)

    @Before
    fun setUp() {
        count = 0 // ✅ reset before every test

        composeTestRule.setContent {
            // ✅ derivedCount recomputes automatically
            val derivedCount by remember { derivedStateOf { count * 2 } }

            DashboardScreen(
                count = count,
                derivedCount = derivedCount,
                onIncrement = { count++ }, // ✅ updates Compose state
                onDecrement = { if (count > 0) count-- }
            )
        }
    }

    @Test
    fun initialState_showsZero() {
        composeTestRule.onNodeWithText("COUNT 0").assertExists()
        composeTestRule.onNodeWithText("× 2 = 0").assertExists()
    }

    @Test
    fun clickIncrement_updatesCount() {
        // Act
        composeTestRule
            .onNodeWithContentDescription("Increment count")
            .performClick()

        // Assert — ✅ text matches exactly what composable renders
        composeTestRule.onNodeWithText("COUNT 1").assertExists()
        composeTestRule.onNodeWithText("× 2 = 2").assertExists()
    }

    @Test
    fun clickDecrement_atZero_doesNotGoNegative() {
        composeTestRule
            .onNodeWithContentDescription("Decrement count")
            .assertIsNotEnabled() // ✅ disabled at 0
    }

    @Test
    fun clickIncrement_thenDecrement_returnsToZero() {
        composeTestRule
            .onNodeWithContentDescription("Increment count")
            .performClick()
        composeTestRule.onNodeWithText("COUNT 1").assertExists()

        composeTestRule
            .onNodeWithContentDescription("Decrement count")
            .performClick()
        composeTestRule.onNodeWithText("COUNT 0").assertExists()
    }
}