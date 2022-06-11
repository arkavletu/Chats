package ru.netology

import java.util.*

data class Chat (
    val users: Pair<User.ID, User.ID>,
    var id: ID = ID()
){
    val messages: MutableList<Message> = mutableListOf()

    @JvmInline
    value class ID(val value: UUID = UUID.randomUUID())

    override fun toString(): String {
        return "Chat $users\n$messages"
    }
}