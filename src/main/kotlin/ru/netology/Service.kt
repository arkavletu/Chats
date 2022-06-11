package ru.netology


object Service {
    val chats: MutableMap<Chat.ID, Chat> = mutableMapOf()
    var users: MutableList<User> = mutableListOf()


    fun createId(size: Int): Int {
        return size
    }


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
            val chat = Chat(Pair(recipientId, message.ownerId))
            //иначе второе сообщение в этот же чат не добавляется
            val x = chats.values.find { it.users == Pair(recipientId, message.ownerId) ||
                    it.users == Pair(message.ownerId,recipientId)}?.id ?: chat.id
            val chatCreated = chats.getOrPut(x){chat}
            chatCreated.messages += message
        } else throw WrongIdOfUserException

    }

    fun deleteChat(chatId: Chat.ID) {
        chats.remove(chatId)
    }


    fun getAllChats(userId: User.ID): Map<Chat.ID, Chat> {
        val user = findUserById(userId) ?: throw WrongIdOfUserException
        return chats.filterValues { it.users.first == userId || it.users.second == userId }
    }

    fun editMessage(recipientId: User.ID, authorId: User.ID, messageToEditId: Message.ID, messageNew: Message) {
        //сообщение полностью заменяется на новое, поэтому id старого отдельно
        val users = Pair(recipientId, authorId)
        val chat = chats.values.find { it.users == users || it.users == Pair(authorId,recipientId)} ?: throw WrongIdOfChatException
        val oldMessage = chat.messages.find { it.id == messageToEditId }
        val messageIndex = chat.messages.indexOf(oldMessage)
        if (messageIndex >= 0) chat.messages[messageIndex] = messageNew
    }

    fun deleteMessage( recipientId: User.ID, authorId: User.ID, messageId: Message.ID) {
        val users = Pair(recipientId, authorId)
        val chat = chats.values.find { it.users == users || it.users == Pair(authorId,recipientId)} ?: throw WrongIdOfChatException
        val index = chat.messages.indexOfFirst { it.id == messageId }
        if (index < 0) throw WrongIdOfMessageException
        chat.messages.removeAt(index)
        if (chat.messages.isEmpty()) deleteChat(chat.id)

    }

    fun getChat(recipientId: User.ID, authorId: User.ID, startMessageId: Message.ID, count: Int): MutableList<Message> {
        if (findUserById(recipientId) == null || findUserById(authorId) == null) throw WrongIdOfUserException
        val chat = chats.values.find { it.users == Pair(recipientId, authorId) || it.users == Pair(authorId,recipientId)}
            ?: throw WrongIdOfChatException
        val startIndex = chat.messages.indexOf(
            chat.messages.find { it.id == startMessageId } ?: throw WrongIdOfMessageException
        )
        return chat.messages.asSequence().
        filter { chat.messages.indexOf(it) >= startIndex }.
        take(count).
        onEach { it.read = true }.toMutableList()
    }

    fun countUnreadChats(userId: User.ID): Int {
        return if (users.any { it.id == userId })
            getAllChats(userId).asSequence().count { it.value.messages.any { !it.read && it.ownerId != userId } }
            else throw WrongIdOfUserException

    }

    fun clearSingleton() {
        chats.clear()
        users.clear()
    }
}