package com.example.lbg

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test

data class User(val id: String, val name: String, val email: String)

class FlowDemo {

    @Test
    fun runFlowDemo() = runBlocking {

        val logs = arrayOf(
            arrayOf("58523", "user_1", "resource_1"),
            arrayOf("62314", "user_2", "resource_2"),
            arrayOf("54001", "user_3", "dffgdg")
        )

        // Collect and print the users produced by the flow
        produce(logs).collect { users ->
            println("Received ${'$'}{users.size} users:")
            users.forEach { println(it) }
        }
    }

    //create function that converts logs into a flow of users
    fun produce(logs: Array<Array<String>>): Flow<List<User>> = flow {
        val userList = logs.mapNotNull { row ->
            // Expect at least id and name; third column might be an email or a resource identifier
            if (row.size < 2) return@mapNotNull null
            val id = row[0]
            val name = row[1]
            val third = row.getOrNull(2) ?: ""
            val email = if (third.contains("@")) third else "${name}@example.com"
            User(id, name, email)
        }
        emit(userList)
    }
}
