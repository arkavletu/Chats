package ru.netology

object Service {
    val chats: MutableMap<Int, MutableList<Message>> = mutableMapOf()
    val users: MutableList<User> = mutableListOf()

    fun createId(size: Int): Int {
        return size
    }


    fun newUser(): User {
        val user = User()
        users += user
        user.id = createId(users.size)
        return user
    }

    fun findUserById(id: Int): User? {
        return users.find { it.id == id }
    }


    fun createMessage(recipientId: Int, message: Message) {
        if (users.any { it.id == recipientId } && users.any { it.id == message.ownerId }) {
            chats.getOrPut(recipientId) { mutableListOf() }
            val chat = chats[recipientId] ?: throw WrongIdOfChatException

            chat.add(message)
            message.id = createId(chat.size)
        } else throw WrongIdOfUserException

    }

    fun deleteChat(chatId: Int) {
        chats.remove(chatId)
    }


    fun getAllChats(userId: Int): Map<Int, MutableList<Message>> {
        val user = findUserById(userId) ?: throw WrongIdOfUserException
        val userChats = chats.filter { it.key == userId || it.value.any { it.ownerId == userId } }
        return userChats
    }

    fun printAllChats(userId: Int): StringBuilder {
        val chats = getAllChats(userId)
        val result = StringBuilder()
        for (entry in chats) {
            result.append("Chat with user ${entry.key}\n${entry.value.last()}")
        }// не стала добавлять сообщение, что чат пуст - не вижу возможности создать пустой чат.
        return result
    }

    fun editMessage(chatId: Int, messageId: Int, message: Message) {
        val chat: MutableList<Message> = chats[chatId] ?: throw WrongIdOfChatException
        val messageIndex = chat.indexOf(chat.find { it.id == messageId })
        if (messageIndex >= 0) chat[messageIndex] = message
        chat.size.let { createId(it) }
    }

    fun deleteMessage(chatId: Int, messageId: Int) {
        val chat = chats[chatId] ?: throw WrongIdOfChatException
        val index = chat.indexOfFirst { it.id == messageId }
        if (index >= 0) {
            chat.removeAt(index)
            if (chat.isEmpty()) deleteChat(chatId)
        } else throw WrongIdOfMessageException
    }

    fun getChat(chatId: Int, messageId: Int, count: Int): MutableList<Message> {// падает если count больше size
        val subChat: MutableList<Message>
        if (users.any { it.id == chatId }) {
            val chat = chats[chatId] ?: throw WrongIdOfChatException
            val index = chat.indexOfFirst { it.id == messageId }
            if (index >= 0) {
                if (count > chat.size) {
                    subChat = index.let {
                        chat.subList(it, chat.size).also { it.forEach { it.read = true } }
                    }
                } else {
                    subChat = index.let {
                        chat.subList(it, index + count).also { it.forEach { it.read = true } }
                    }
                }
            } else throw WrongIdOfMessageException
        } else throw WrongIdOfUserException
        return subChat
    }

    fun countUnreadChats(userId: Int): Int {
        val count: Int = if (users.any { it.id == userId })
            getAllChats(userId).count { it.value.any { !it.read && it.ownerId != userId } }
        else throw WrongIdOfUserException
        return count
    }

    fun clearSingleton() {
        chats.clear()
        users.clear()
    }

}