package ru.netology

object Service {
    val chats: MutableMap<Chat.ID, Chat> = mutableMapOf()//сначала адресат, потом автор
    val users: MutableList<User> = mutableListOf()
    var nextId: Int = 0

    fun newUser(): User {
        val user = User(User.ID(nextId++))
        users += user
        return user
    }

    fun findUserById(UserId: User.ID): User? {
        return users.find { it.id == UserId }
    }

    fun createMessage(recipientId: User.ID, message: Message) {
        if (users.any { it.id == recipientId } && users.any { it.id == message.ownerId }) {
            val chat = Chat(message.ownerId, recipientId, Chat.ID(nextId++))
            //иначе второе сообщение в этот же чат не добавляется
            val x = chats.values.find {
                it.authorId == message.ownerId ||
                        it.recipientId == message.ownerId
            }?.id ?: chat.id
            val chatCreated = chats.getOrPut(x) { chat }
            chatCreated.messages += message
        } else throw WrongIdOfUserException
    }

//    fun createMessage(recipientId: User.ID, message: Message) {
//
//        if (users.any { it.id == recipientId } && users.any { it.id == message.ownerId }) {
//            val chat = Chat(message.ownerId,recipientId,Chat.ID(nextId++))
//            val chat1 =  chats.getOrPut(chat.id) { chat }
//            chat1.messages += message
//        } else throw WrongIdOfUserException
//
//    }

    fun deleteChat(chatId: Chat.ID) {
        chats.remove(chatId)
    }


    fun getAllChats(userId: User.ID): Map<Chat.ID, Chat> {
        val user = findUserById(userId) ?: throw WrongIdOfUserException
        val userChats = chats.filter { it.value.recipientId == userId || it.value.authorId == userId }
        return userChats
    }

    fun editMessage(recipientId: User.ID, authorId: User.ID, messageToEditId: Message.ID, messageNew: Message) {
        //сообщение полностью заменяется на новое, поэтому id старого отдельно
        val chat = chats.values.find { it.recipientId == recipientId && it.authorId == authorId } ?: throw WrongIdOfChatException
        //val key = chat.id
        val oldMessage = chat.messages.find { it.id == messageToEditId }
        val messageIndex = chat.messages.indexOf(oldMessage)
        if (messageIndex >= 0) chat.messages[messageIndex] = messageNew
    }

    fun deleteMessage( recipientId: User.ID, authorId: User.ID, messageId: Message.ID) {
        val chat = chats.values.find { it.recipientId == recipientId && it.authorId == authorId } ?: throw WrongIdOfChatException
        val key = chat.id
        val index = chat.messages.indexOfFirst { it.id == messageId }
        if (index < 0) throw WrongIdOfMessageException
        chat.messages.removeAt(index)
        if (chat.messages.isEmpty()) deleteChat(key)

    }

    fun getChat(recipientId: User.ID, authorId: User.ID, startMessageId: Message.ID, count: Int): MutableList<Message> {
        if (findUserById(recipientId) == null || findUserById(authorId) == null) throw WrongIdOfUserException
        val chat = chats.values.find { it.recipientId == recipientId && it.authorId == authorId } ?: throw WrongIdOfChatException
        //val key = chat.id
        val startMessage = chat.messages.find { it.id == startMessageId } ?: throw WrongIdOfMessageException
        val startIndex = chat.messages.indexOf(startMessage)
        return chat.messages.filter { chat.messages.indexOf(it) >= startIndex }.take(count).onEach { it.read = true } as MutableList<Message>
    }

    fun countUnreadChats(userId: User.ID): Int {
        val count: Int = if (users.any { it.id == userId })
            getAllChats(userId).count { it.value.messages.any { !it.read && it.ownerId != userId } }
        else throw WrongIdOfUserException
        return count
    }

    fun clearSingleton() {
        chats.clear()
        users.clear()
    }

}
