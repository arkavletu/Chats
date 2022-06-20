package ru.netology

data class Chat (
    val authorId: User.ID,
    val recipientId: User.ID,
    val id: ID,
    val messages: MutableList<Message> = mutableListOf()
   ){


    @JvmInline
    value class ID(val value: Int)

    override fun toString(): String {
        return messages.toString()
    }
}