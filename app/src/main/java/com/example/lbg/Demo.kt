package com.example.lbg

fun demo(): Map<String, Pair<Int, Int>> {
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

fun findTopResource(logs: Array<Array<String>>): Map<String, String?> {
    val userGroups = logs.groupBy { it[1] } // UserID

    return userGroups.mapValues { (_, entries) ->
        val resourceCounts = entries
            .groupingBy { it[2] } // ResourceID
            .eachCount()

        resourceCounts.maxByOrNull { it.value }?.key
    }
}

fun isPalindrome(input: String): Boolean {
    val cleanInput = input.filter { it.isLetterOrDigit() }.lowercase()
    return cleanInput == cleanInput.reversed()
}

fun main() {
    val testString = "A man, a plan, a canal: Panama"
    println("Is \"$testString\" a palindrome? ${isPalindrome(testString)}")
}
