package ru.netology
import java.util.*
import java.util.UUID.randomUUID as UserId

data class User(
    val id: ID = ID(),


    ) {
    @JvmInline
    value class ID(val value: UUID = UserId())

    override fun toString(): String {
        return "user id $id"
    }

}



