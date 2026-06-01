import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {
    val names = flowOf("Alice", "Bob").onEach { delay(1000) }
    val statuses = flowOf("Online", "Away", "Busy").onEach { delay(600) }

    // combine(o1, o2) { ... }
    names.combine(statuses) { name, status ->
        "$name is $status" // This is the transformation result
    }.collect { value ->
        println(value)
    }


    val numbers = listOf(1, 2, 2, 3, 3, 3, 4, 4, 4, 4)

    val secondHighest = numbers.groupingBy { it }
        .eachCount()                     // Step 1: Get frequencies map
        .entries                         // Step 2: Convert to a list of entries
        .sortedByDescending { it.value } // Step 3: Sort by count (highest first)
        .getOrNull(1)                    // Step 4: Safely grab the second item (index 1)

    println("Number: ${secondHighest?.key}, Frequency: ${secondHighest?.value}")
// Output: Number: 3, Frequency: 3

}

fun findMaxWindowLoad(load: IntArray, windowSize: Int): Int {
    if (load.isEmpty() || windowSize <= 0 || windowSize > load.size) return 0

    var currentWindowLoad = 0

    // 1. Calculate the load of the first window
    for (i in 0 until windowSize) {
        currentWindowLoad += load[i]
    }

    var maxLoad = currentWindowLoad

    // 2. Slide the window across the array
    for (i in windowSize until load.size) {
        // Add the next element, remove the oldest element
        currentWindowLoad += load[i] - load[i - windowSize]

        if (currentWindowLoad > maxLoad) {
            maxLoad = currentWindowLoad
        }
    }

    return maxLoad
}



