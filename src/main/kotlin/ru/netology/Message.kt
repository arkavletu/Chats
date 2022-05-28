package ru.netology

import java.time.LocalDateTime

data class Message(
    val ownerId: User.ID, // от кого
    val text: String,
    val time: LocalDateTime = LocalDateTime.now(),
    var read: Boolean = false,

    ) {
    var id: ID = ID(0)

    @JvmInline
    value class ID(val value: Int)

    override fun toString(): String {
        return "\nMessage id $id from $ownerId\n$text\n$time"
    }
}