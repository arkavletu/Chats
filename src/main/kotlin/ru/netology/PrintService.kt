package ru.netology
//для печати специально
object PrintService {
    fun printAllChats(userId: User.ID){
        val chats = Service.getAllChats(userId)
        val result = StringBuilder()
        for ((chatId, listOfMessages) in chats) {
            result.append("$chatId, ${listOfMessages.last()}")
        }
        println(result)
    }
    fun printChat(userId: User.ID, messageID: Message.ID,count: Int){
        val chat = Service.getChat(userId, messageID, count)
        println(chat.toString())
    }
}