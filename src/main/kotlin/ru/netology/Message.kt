package ru.netology

import java.time.LocalDateTime

data class Message(
    val ownerId: Int, // от кого
    val text: String,
    val time: LocalDateTime = LocalDateTime.now(),

    ) {
    var read: Boolean = false
    var id: Int = 0


    override fun toString(): String {
        return "\nMessage id $id from $ownerId\n$text\n$time"
    }
}