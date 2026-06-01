package com.example.lib

import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) = runBlocking {
    if (args.isEmpty()) {
        println("Hello from daya!")
    } else {
        println("Hello from daya! Args: ${args.joinToString(", ")}")
    }

    val flow1 = flowOf(1, 2, 3)
    val flow2 = flowOf("A", "B", "C")

    println("--- Combine ---")
    flow1.combine(flow2) { i, s ->
        "$i$s"
    }.collect {
    }


    println("--- MERGE ---")
    merge(flow1, flow2).collect { println(it) }

    println("--- ZIP ---")
    flow1.zip(flow2) { num, let -> "$num$let" }.collect { println(it) }
}
