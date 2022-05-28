package ru.netology

import java.time.LocalDateTime

data class Message(
    val ownerId: User.ID, // от кого
    val text: String,
    val time: LocalDateTime = LocalDateTime.now(),
    var read: Boolean = false,
    val id: ID = ID()
    ) {
    @JvmInline
    value class ID(val value: Int = if(Service.users.none { it.id.value == Service.chats.values.size+1})
        Service.chats.values.size+1
    else Service.chats.values.size+2)

    override fun toString(): String {
        return "\nMessage id $id from $ownerId\n$text\n$time"
    }
}