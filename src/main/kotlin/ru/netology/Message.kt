package ru.netology

import java.time.LocalDateTime

data class Message(
    var ownerId: Int, // от кого
    var text: String,
    var time: LocalDateTime = LocalDateTime.now(),

    ) {
    var read: Boolean = false
    var id: Int = 0
    fun createId(size: Int) {
        this.id = size + 1
    }

    override fun toString(): String {
        return "\nMessage id $id from $ownerId\n$text\n$time"
    }
}