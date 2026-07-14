package com.example.lbg

import androidx.compose.foundation.gestures.forEach

fun demo1(): Map<String, Pair<Int, Int>> {
    val logs = arrayOf(
        arrayOf("58523", "user_1", "resource_1"),
        arrayOf("62314", "user_2", "resource_2"),
        arrayOf("54001", "user_1", "resource_3"),
        arrayOf("58522", "user_1", "resource_2"),
        arrayOf("53000", "user_3", "resource_1"),
        arrayOf("88550", "user_2", "resource_1"),
        arrayOf("54060", "user_1", "resource_3")
    )

    val userGroups = logs.groupBy(
        { it[1] },       // UserID
        { it[0].toInt() } // Timestamp as Int
    )

    return userGroups.mapValues { (_, timestamps) ->
        val min = timestamps.minOrNull() ?: 0
        val max = timestamps.maxOrNull() ?: 0
        Pair(min, max)
    }
}

fun findTopResource1(logs: Array<Array<String>>): Map<String, String?> {
    val userGroups = logs.groupBy { it[1] } // UserID

    return userGroups.mapValues { (userId, entries) ->
        val resourceCounts = entries
            .groupingBy { it[2] } // ResourceID
            .eachCount()

        // Log the intermediate calculation
        println("User: $userId | Resource Counts: $resourceCounts")

        resourceCounts.maxByOrNull { it.value }?.key
    }
}

fun isPalindrome1(input: String): Boolean {
    val cleanInput = input.filter { it.isLetterOrDigit() }.lowercase()
    return cleanInput == cleanInput.reversed()
}

/*
fun main() {
    val logs = arrayOf(
        arrayOf("58523", "user_1", "resource_1"),
        arrayOf("62314", "user_2", "resource_2"),
        arrayOf("54001", "user_1", "resource_3"),
        arrayOf("58522", "user_1", "resource_2"),
        arrayOf("53000", "user_3", "resource_1"),
        arrayOf("88550", "user_2", "resource_1"),
        arrayOf("54060", "user_1", "resource_3")
    )

    println("--- Top Resources per User ---")
    val topResources = findTopResource(logs)
    topResources.forEach { (user, resource) ->
        println("Top resource for $user: $resource")
    }

    println("\n--- Palindrome Test ---")
    val testString = "A man, a plan, a canal: Panama"
    println("Is \"$testString\" a palindrome? ${isPalindrome(testString)}")
}*/
