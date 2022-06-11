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
        val userChats = chats.filterValues { it.users.first == userId || it.users.second == userId }
        return userChats
    }

    fun editMessage(recipientId: User.ID, authorId: User.ID, messageToEditId: Message.ID, messageNew: Message) {
        //сообщение полностью заменяется на новое, поэтому id старого отдельно
        val users = Pair(recipientId, authorId)
        val chat = chats.values.find { it.users == users } ?: throw WrongIdOfChatException
        val oldMessage = chat.messages.find { it.id == messageToEditId }
        val messageIndex = chat.messages.indexOf(oldMessage)
        if (messageIndex >= 0) chat.messages[messageIndex] = messageNew
    }

    fun deleteMessage( recipientId: User.ID, authorId: User.ID, messageId: Message.ID) {
        val users = Pair(recipientId, authorId)
        val chat = chats.values.find { it.users == users } ?: throw WrongIdOfChatException
        val index = chat.messages.indexOfFirst { it.id == messageId }
        if (index < 0) throw WrongIdOfMessageException
        chat.messages.removeAt(index)
        if (chat.messages.isEmpty()) deleteChat(chat.id)

    }

    fun getChat(recipientId: User.ID, authorId: User.ID, startMessageId: Message.ID, count: Int): MutableList<Message> {
        if (findUserById(recipientId) == null || findUserById(authorId) == null) throw WrongIdOfUserException
        val users = Pair(recipientId, authorId)
        val chat = chats.values.find { it.users == users } ?: throw WrongIdOfChatException
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




//    fun create(id: Int, message: Message) { //создаю 2 одинаковых чата, иначе у меня никак не пишется фильтрация..
//        val created: Boolean
//        if (users.contains(findById(id)) && users.contains(findById(message.ownerId))) {
//            chats.putIfAbsent(Pair(id, message.ownerId), mutableListOf())
//            chats.putIfAbsent(Pair(message.ownerId, id), mutableListOf())
//            chats[Pair(id, message.ownerId)]?.plusAssign(message)
//                .also { message.createId(chats[Pair(id, message.ownerId)]!!.size) }
//            chats[Pair(message.ownerId, id)]?.plusAssign(message)
//                .also { message.createId(chats[Pair(id, message.ownerId)]!!.size) }
//            created = true
//        } else created = false
//
//        if (!created) throw WrongIdException()
//    }

//    fun delete(ownerId: Int, id: Int) {
//        chats.remove(Pair(ownerId, id))
//        chats.remove(Pair(id, ownerId))
//    }
//
//    fun getAllChats(userId: Int): String {
//        val oops = "No messages"
//        val result: String = findById(userId)?.userChats?.map { it.key to it.value.last() }?.let {
//            it.joinToString {
//                "\nChat with id ${it.first.second}\n${it.second}\n"
//            }.ifEmpty { oops }
//        } ?: throw WrongIdException()
//        return result
//    }
//
//    fun edit(id: Int, messageId: Int, message: Message) {
//        val index: Int? = chats[Pair(id, message.ownerId)]?.indexOfFirst { it.id == messageId }
//        if (index != null && index >= 0) {
//            val initialMessage: Message = chats[Pair(id, message.ownerId)]!!.get(index) // до проверки не дойдет)
//            message.id = messageId
//            message.ownerId = initialMessage.ownerId
//            chats[Pair(id, message.ownerId)]?.set(index, message)
//            chats[Pair(message.ownerId, id)]?.set(index, message)
//        } else throw WrongIdException()
//    }
//
//    fun deleteMessage(ownerId: Int, id: Int, messageId: Int) {
//        val index: Int? = chats[Pair(id, ownerId)]?.indexOfFirst { it.id == messageId }
//        if (index != null && index >= 0) {
//            chats[Pair(id, messageId)]?.removeAt(index)
//            chats[Pair(messageId, id)]?.removeAt(index)
//            if (chats[Pair(id, ownerId)]?.isEmpty() == true && chats[Pair(ownerId, id)]?.isEmpty() == true) delete(
//                ownerId,
//                id)
//        } else throw WrongIdException()
//    }
//
//    fun getChat(ownerId: Int, id: Int, messageId: Int, count: Int): String {
//        val owner = findById(ownerId) ?: throw WrongIdException()
//        var x = sequenceOf<Message>()
//        val empty = "No messages"
//        owner.userChats.filter { it.key.first == id }.forEach { x += it.value }
//        return x.filter { it.id >= messageId }.take(count).onEach { it.read = true }.joinToString { "$it" }
//            .ifEmpty { empty }
//    }
//
//    fun countNew(userId: Int): Int {
//        return findById(userId)?.userChats?.asSequence()?.count {
//            it.value.any { !it.read && it.ownerId != userId }
//        } ?: throw WrongIdException()
//    }
//
//    fun clearSingleton() {
//        chats.clear()
//        users.clear()
//    }

}