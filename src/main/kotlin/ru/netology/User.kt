package ru.netology

import java.util.*
import java.util.UUID.randomUUID as MyId

data class User(
    val id: ID = ID(),


    ) {
    @JvmInline
    value class ID(val value: UUID = MyId())

    override fun toString(): String {
        return "user id $id"
    }

}



