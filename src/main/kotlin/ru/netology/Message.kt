package ru.netology

import java.time.LocalDateTime
import java.util.*

data class Message(
    val ownerId: User.ID, // от кого
    val text: String,
    val time: LocalDateTime = LocalDateTime.now(),
    var read: Boolean = false,
    var id: ID = ID(),
) {


    @JvmInline
    value class ID(val value: UUID = UUID.randomUUID())

    override fun toString(): String {
        return "\nMessage id $id from $ownerId\n$text\n$time"
    }
}