package com.example.lbg.view




data class Task(val name: Int, val value: String, val done: String)


fun longestCommonPrefix(strs: List<String>): String {
    if (strs.isEmpty()) return ""

    // Approach 1 — fold: start with first string, trim to match each next
    return strs.fold(strs.first()) { prefix, str ->
        var p = prefix
        while (!str.startsWith(p)) p = p.dropLast(1)
        p
    }
}

// Approach 2 — sort trick (elegant): common prefix of sorted first + last
fun longestCommonPrefix2(strs: List<String>): String {
    if (strs.isEmpty()) return ""
    val sorted = strs.sorted()
    val first = sorted.first()
    val last = sorted.last()
    return first.commonPrefixWith(last)  // Kotlin stdlib ✅
}

// Approach 3 — zip columns
fun longestCommonPrefix3(strs: List<String>): String {
    if (strs.isEmpty()) return ""
    return strs.reduce { acc, s ->
        acc.zip(s)
            .takeWhile { (a, b) -> a == b }
            .map { it.first }
            .joinToString("")
    }
}

// All three are O(S) where S = sum of all characters
// Approach 2 uses Kotlin stdlib commonPrefixWith — cleanest for interviews


