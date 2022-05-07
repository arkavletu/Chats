package ru.netology


object Service {
    val chats: MutableMap<Pair<Int, Int>, MutableList<Message>> = mutableMapOf()
    var users: MutableList<User> = mutableListOf()


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

    fun getAllChats(userId: Int): String {
        val oops = "No messages"
        val result: String = findById(userId)?.userChats?.map { it.key to it.value.last() }?.let {
            it.joinToString {
                "\nChat with id ${it.first.second}\n${it.second}\n"
            }.ifEmpty { oops }
        } ?: throw WrongIdException()
        return result
    }

    fun edit(id: Int, messageId: Int, message: Message) {
        val index: Int? = chats[Pair(id, message.ownerId)]?.indexOfFirst { it.id == messageId }
        if (index != null && index >= 0) {
            val initialMessage: Message = chats[Pair(id, message.ownerId)]!!.get(index) // до проверки не дойдет)
            message.id = messageId
            message.ownerId = initialMessage.ownerId
            chats[Pair(id, message.ownerId)]?.set(index, message)
            chats[Pair(message.ownerId, id)]?.set(index, message)
        } else throw WrongIdException()
    }

    fun deleteMessage(ownerId: Int, id: Int, messageId: Int) {
        val index: Int? = chats[Pair(id, ownerId)]?.indexOfFirst { it.id == messageId }
        if (index != null && index >= 0) {
            chats[Pair(id, messageId)]?.removeAt(index)
            chats[Pair(messageId, id)]?.removeAt(index)
            if (chats[Pair(id, ownerId)]?.isEmpty() == true && chats[Pair(ownerId, id)]?.isEmpty() == true) delete(
                ownerId,
                id)
        } else throw WrongIdException()
    }

    fun getChat(ownerId: Int, id: Int, messageId: Int, count: Int): String {
        val owner = findById(ownerId) ?: throw WrongIdException()
        var x = sequenceOf<Message>()
        val empty = "No messages"
        owner.userChats.filter { it.key.first == id }.forEach { x += it.value }
        return x.filter { it.id >= messageId }.take(count).onEach { it.read = true }.joinToString { "$it" }
            .ifEmpty { empty }
    }

    fun countNew(userId: Int): Int {
        return findById(userId)?.userChats?.asSequence()?.count {
            it.value.any { !it.read && it.ownerId != userId }
        } ?: throw WrongIdException()
    }

    fun clearSingleton() {
        chats.clear()
        users.clear()
    }

}