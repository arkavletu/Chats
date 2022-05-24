package ru.netology

object Service {
    val chats: MutableMap<User.ID, MutableList<Message>> = mutableMapOf()
    val users: MutableList<User> = mutableListOf()


    fun newUser(): User {
        val user = User()
        if(users.none{it.id == user.id}) users += user else throw WrongIdOfUserException
        return user
    }

    fun findUserById(UserId: User.ID): User? {
        return users.find { it.id == UserId }
    }


    fun createMessage(recipientId: User.ID, message: Message) {
        if (users.any { it.id == recipientId } && users.any { it.id == message.ownerId }) {
            val chat =  chats.getOrPut(recipientId) { mutableListOf() }
            chat += message
        } else throw WrongIdOfUserException

    }

    fun deleteChat(chatId: User.ID) {
        chats.remove(chatId)
    }


    fun getAllChats(userId: User.ID): Map<User.ID, MutableList<Message>> {
        val user = findUserById(userId) ?: throw WrongIdOfUserException
        val userChats = chats.filter { (senderId, messages) -> senderId == userId ||
                messages.any { it.ownerId == userId } }
        return userChats
    }

    fun editMessage(UserId: User.ID, messageToEditId: Message.ID, messageNew: Message) {
        //сообщение полностью заменяется на новое, поэтому id старого отдельно
        val chat: MutableList<Message> = chats[UserId] ?: throw WrongIdOfChatException
        val messageIndex = chat.indexOf(chat.find { it.id == messageToEditId })
        if (messageIndex >= 0) chat[messageIndex] = messageNew
    }

    fun deleteMessage(chatId: User.ID, messageId: Message.ID) {
        val chat = chats[chatId] ?: throw WrongIdOfChatException
        val index = chat.indexOfFirst { it.id == messageId }
        if (index < 0) throw WrongIdOfMessageException
        chat.removeAt(index)
        if (chat.isEmpty()) deleteChat(chatId)

    }

    fun getChat(chatId: User.ID, startMessageId: Message.ID, count: Int): MutableList<Message> {
        val subChat: MutableList<Message>
        if (users.none { it.id == chatId }) throw WrongIdOfUserException
        val chat = chats[chatId] ?: throw WrongIdOfChatException
        val startMessage = chat.find { it.id == startMessageId } ?: throw WrongIdOfMessageException
        val startIndex = chat.indexOf(startMessage)
        val endIndex = if (count > chat.size) chat.lastIndex else startIndex + count
        subChat = startIndex.let{chat.subList(it, endIndex)}.onEach { it.read = true }
        //если одно сообщение в чате, то count>size=>пустой чат
        return subChat
    }

    fun countUnreadChats(userId: User.ID): Int {
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