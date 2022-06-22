package ru.netology

import java.time.LocalDateTime
data class Message(
    val ownerId: User.ID, // от кого
    val text: String,
    val id: ID,
    val time: LocalDateTime = LocalDateTime.now(),
    var read: Boolean = false

    ) {


    @JvmInline
    value class ID(val value: Int)

    override fun toString(): String {
        return "\nMessage id $id from $ownerId\n$text\n$time"
    }
}