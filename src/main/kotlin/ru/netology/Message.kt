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
    value class ID(val value: Int = Service.chats.values.size+1)// не поняла как сделать уникальный id

    override fun toString(): String {
        return "\nMessage id $id from $ownerId\n$text\n$time"
    }
}