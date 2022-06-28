package ru.netology

object Service {
    private val chats: MutableMap<Chat.ID, Chat> = mutableMapOf()
    private val users: MutableList<User> = mutableListOf()
    private var nextIdUser: Int = 1
    private var nextIdChat: Int = 1
    private var nextIdMessage: Int = 1// нехай будет сквозная, мало ли попадет в другой чат, хоть найдется

    fun newUser(): User {
        val user = User(User.ID(nextIdUser++))
        users += user
        return user
    }

    fun findUserById(userId: User.ID): User? {// надо и на остальные сучности?
        return users.find { it.id == userId }
    }

    fun findMessageByText(text: String): List<Message> {//сообщения юзеру проще искать по тексту
        return chats.values.asSequence().filter { it.messages.any { it.text.contains(text, ignoreCase = true) } }
            .map { it.messages.filter { it.text.contains(text, ignoreCase = true) } }.flatten().toList()
    }

    fun findChat(authorId: User.ID, recipientId: User.ID): Chat? {// чат тоже я как юзер не буду искать по id
        return chats.values.find {
            it.authorId == authorId && it.recipientId == recipientId ||
                    it.recipientId == authorId && it.authorId == recipientId
        }
    }

    fun createMessage(authorId: User.ID, recipientId: User.ID, text: String): Message {
        val message = Message(authorId,text,Message.ID(nextIdMessage++))
        if (users.any { it.id == recipientId } && users.any { it.id == authorId }) {
            val chat = Chat(authorId, recipientId, Chat.ID(nextIdChat++))
            val idOfChat = findChat(authorId, recipientId)?.id ?: chat.id
            val chatCreated = chats.getOrPut(idOfChat) { chat }
            chatCreated.messages += message
        } else throw WrongIdOfUserException
        return message
    }


    fun deleteChat(chatId: Chat.ID) {
        chats.remove(chatId)
    }


    fun getAllChats(userId: User.ID): List<Chat> {
        val user = findUserById(userId) ?: throw WrongIdOfUserException
        val userChats = chats.values.filter { it.recipientId == userId || it.authorId == userId }
        return userChats
    }

    fun editMessage(recipientId: User.ID, authorId: User.ID, messageNew: Message) {
        val chat = findChat(authorId, recipientId) ?: throw WrongIdOfChatException
        val oldMessage = chat.messages.find { it.id == messageNew.id }?: throw WrongIdOfMessageException
        val messageIndex = chat.messages.indexOf(oldMessage)
        if (messageIndex >= 0) chat.messages[messageIndex] = oldMessage.copy(text = messageNew.text)
    }

    fun deleteMessage( recipientId: User.ID, authorId: User.ID, messageId: Message.ID) {
        val chat = findChat(authorId, recipientId) ?: throw WrongIdOfChatException
        val key = chat.id
        val index = chat.messages.indexOfFirst { it.id == messageId }
        if (index < 0) throw WrongIdOfMessageException
        chat.messages.removeAt(index)
        if (chat.messages.isEmpty()) deleteChat(key)

    }

    // у меня тут сначала sublist и стоял вместо фильтра. Не включает последний индекс, если сообщение в чате 1,
    // саблист превратится в сабзиро и выкинет исключение. Лишние проверки.
   // slice зашел)
    fun getChat(recipientId: User.ID, authorId: User.ID, startMessageId: Message.ID, count: Int): Chat {
        if (findUserById(recipientId) == null || findUserById(authorId) == null) throw WrongIdOfUserException
        val chat = findChat(authorId, recipientId) ?: throw WrongIdOfChatException
        val startMessage = chat.messages.find { it.id == startMessageId } ?: throw WrongIdOfMessageException
        val startIndex = chat.messages.indexOf(startMessage)
        return chat.copy(messages = chat.messages.slice ( startIndex..chat.messages.lastIndex ).take(count)
            .onEach { it.read = true } as MutableList<Message>)
    }

    fun countUnreadChats(userId: User.ID): Int {//а там условие в лямбде не кривое, часом?
        val count: Int = getAllChats(userId).count { it.messages.any { !it.read && it.ownerId != userId } }
        return count
    }

    fun clearSingleton() {
        chats.clear()
        users.clear()
    }

}
