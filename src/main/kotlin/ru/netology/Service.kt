package ru.netology

object Service {
    val chats: MutableMap<Pair<User.ID, User.ID>, MutableList<Message>> = mutableMapOf()//сначала адресат, потом автор
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
            val chat =  chats.getOrPut(Pair(recipientId, message.ownerId)) { mutableListOf() }
            val possibleId = chat.size + 1
            message.id = Message.ID(if(chat.any { it.id.value == possibleId }) possibleId + 1 else possibleId)
            chat += message
        } else throw WrongIdOfUserException

    }

    fun deleteChat(chatId: Pair<User.ID, User.ID>) {
        chats.remove(chatId)
    }


    fun getAllChats(userId: User.ID): Map<Pair<User.ID, User.ID>, MutableList<Message>> {
        val user = findUserById(userId) ?: throw WrongIdOfUserException
        val userChats = chats.filter { it.key.first == userId || it.key.second == userId }
        return userChats
    }

    fun editMessage(recipientId: User.ID, authorId: User.ID, messageToEditId: Message.ID, messageNew: Message) {
        //сообщение полностью заменяется на новое, поэтому id старого отдельно
        val key = Pair(recipientId, authorId)
        val chat = chats[key] ?: throw WrongIdOfChatException
        val oldMessage = chat.find { it.id == messageToEditId }
        val messageIndex = chat.indexOf(oldMessage)
        if (messageIndex >= 0) chat[messageIndex] = messageNew
    }

    fun deleteMessage( recipientId: User.ID, authorId: User.ID, messageId: Message.ID) {
        val key = Pair(recipientId, authorId)
        val chat = chats[key] ?: throw WrongIdOfChatException
        val index = chat.indexOfFirst { it.id == messageId }
        if (index < 0) throw WrongIdOfMessageException
        chat.removeAt(index)
        if (chat.isEmpty()) deleteChat(key)

    }

    fun getChat(recipientId: User.ID, authorId: User.ID, startMessageId: Message.ID, count: Int): MutableList<Message> {
        if (findUserById(recipientId) == null || findUserById(authorId) == null) throw WrongIdOfUserException
        val key = Pair(recipientId, authorId)
        val chat = chats[key] ?: throw WrongIdOfChatException
        val startMessage = chat.find { it.id == startMessageId } ?: throw WrongIdOfMessageException
        val startIndex = chat.indexOf(startMessage)
        return chat.filter { chat.indexOf(it) >= startIndex }.take(count).onEach { it.read = true } as MutableList<Message>
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