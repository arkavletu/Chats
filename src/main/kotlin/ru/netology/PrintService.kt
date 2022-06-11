package ru.netology

object PrintService {

    fun printAllChats(userId: User.ID){
        println(Service.getAllChats(userId).entries.asSequence().map { it.value.users to it.value.messages.last() }.
        joinToString { "${it.first}\n${it.second}" })
    }
    fun printChat(authorId: User.ID, recipientId: User.ID, startMessageId: Message.ID, count: Int){
        println(Service.getChat(authorId, recipientId, startMessageId, count).toString())
    }
}
