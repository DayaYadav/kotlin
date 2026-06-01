package com.example.lbg

import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*

class DemoTest {

    @Test
    fun runDemo() = runBlocking {
        val result = demo()
        println("User stats: $result")
        
        val topResources = findTopResource(getLogs())
        println("Top resources: $topResources")
    }

    private fun getLogs() = arrayOf(
        arrayOf("58523", "user_1", "resource_1"),
        arrayOf("62314", "user_2", "resource_2"),
        arrayOf("54001", "user_1", "resource_3"),
        arrayOf("58522", "user_1", "resource_2"),
        arrayOf("53000", "user_3", "resource_1"),
        arrayOf("88550", "user_2", "resource_1"),
        arrayOf("54060", "user_1", "resource_3")
    )

    private fun demo(): Map<String, Pair<Int, Int>> {
        val logs = getLogs()

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

    private fun findTopResource(logs: Array<Array<String>>): Map<String, String?> {
        val userGroups = logs.groupBy { it[1] } // UserID

        return userGroups.mapValues { (_, entries) ->
            val resourceCounts = entries
                .groupingBy { it[2] } // ResourceID
                .eachCount()

            resourceCounts.maxByOrNull { it.value }?.key
        }
    }
}
