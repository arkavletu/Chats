package ru.netology
//для печати специально
object PrintService {
    fun printAllChats(userId: User.ID){
        val chats = Service.getAllChats(userId)
        val result = StringBuilder()
        for (chat in chats) {
            result.append("${chat.id}, ${chat.messages.last()}")
        }
        println(result)
    }
    fun printChat(authorId: User.ID, recipientId: User.ID, startMessageId: Message.ID, count: Int){
        println(Service.getChat(authorId, recipientId, startMessageId, count).toString())
    }
}