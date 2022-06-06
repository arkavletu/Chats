package ru.netology

object PrintService {
    fun printAllChats(userId: User.ID){
        val chats = Service.getAllChats(userId)
        val result = StringBuilder()
        for ((chatId, listOfMessages) in chats) {
            result.append("$chatId, ${listOfMessages.last()}")
        }
        println(result)
    }
    fun printChat(authorId: User.ID, recipientId: User.ID, startMessageId: Message.ID, count: Int){
        println(Service.getChat(authorId, recipientId, startMessageId, count).toString())
    }
}
