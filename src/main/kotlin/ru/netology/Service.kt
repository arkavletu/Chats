package ru.netology


object Service {
    val chats: MutableMap<Int, MutableList<Message>> = mutableMapOf()
    val users: MutableList<User> = mutableListOf()


    fun newUser(id: Int): User {
        val user = User(id)
        users += user
        return user
    }

    fun findById(id: Int): User? {
        return users.firstOrNull { it.id == id }
    }


    fun create(id: Int, message: Message) {
        if (users.contains(findById(id)) && users.contains(findById(message.ownerId))) {
            if(chats.containsKey(message.ownerId) && chats.containsKey(id)) {
                chats[id]?.size?.let { message.createId(it) }.also { chats[id]?.add(message) }
                chats[message.ownerId]?.size?.let { message.createId(it) }.also { chats[message.ownerId]?.add(message) }
            } else {
                chats[id] = mutableListOf()
                chats[message.ownerId] = mutableListOf()
                chats[id]?.size?.let { message.createId(it) }.also { chats[id]?.add(message) }
                chats[message.ownerId]?.size?.let { message.createId(it) }.also { chats[message.ownerId]?.add(message) }
            }

        } else throw WrongIdException()

    }

    fun delete(id: Int) {
        chats.remove(id)
    }

    fun getAllChats(userId: Int): String {
        val result: String = findById(userId)?.userChats?.
        map { it.key to it.value.last() }?.
        let {it.joinToString {
            "\nChat with id ${it.first}\n${it.second}\n"
        } } ?: throw WrongIdException()
        return result
    }

    fun edit(id: Int, messageId: Int, message: Message) {
        val index: Int? = chats[id]?.indexOfFirst { it.id == messageId }
        if (index != null && index >= 0) {
            val initialMessage: Message = chats[id]!!.get(index) // до проверки не дойдет)
            message.id = messageId
            message.ownerId = initialMessage.ownerId
            chats[id]?.set(index, message)
        } else throw WrongIdException()
    }

    fun deleteMessage(id: Int, messageId: Int) {
        val index: Int? = chats[id]?.indexOfFirst { it.id == messageId }
        if (index != null && index >= 0) {
            chats[id]?.removeAt(index)
            if (chats[id]?.isEmpty() == true) delete(id)
        } else throw WrongIdException()
    }

    fun getChat(ownerId: Int, chatId: Int, messageId: Int, count: Int): String? { // sequence
       val owner = findById(ownerId)?: throw WrongIdException()


//        if (users.contains(findById(id))) {
//
//                val index = chats[id]?.indexOfFirst { it.id == messageId }
//                if (index != null && index >= 0) {
//                    if (chats[id]!!.size >= count) {
//                      val subChat = index.let {
//                        chats[id]?.subList(it, index + count). }
//                     }
//                      return subChat
//                   } else throw Error()
//                } else throw WrongIdException()
//        } else throw WrongIdException()
      return null
    }

    fun countNew(userId: Int): Int {
        return findById(userId)?.userChats?.asSequence()?.count {
            it.value.any { !it.read && it.ownerId != userId }
        }?: throw WrongIdException()
    }

    fun emptySingleton() {
        chats.clear()
        users.clear()
    }

}