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
            if (!chats.containsKey(id)) {
                chats[id] = mutableListOf()
                chats[id]?.size?.let { message.createId(it) }
                chats[id]?.add(message)
            } else {
                chats[id]?.size?.let { message.createId(it) }
                chats[id]?.add(message)
            }
        } else throw WrongIdException()

    }

    fun delete(id: Int) {
        chats.remove(id)
    }

    fun getAllChats(userId: Int): StringBuilder {
        if (users.contains(findById(userId))) {
            val index: Int = users.indexOf(findById(userId))
            val result = StringBuilder()
            if (users[index].userChats.isNotEmpty()) {
                for (entry in users[index].userChats) {
                    result.append("Chat with user ${entry.key}\nLast message ${entry.value.last()}")
                    if (entry.value.isEmpty()) result.append("Chat with user ${entry.key}\nNo messages")
                }
            }

            return result
        } else throw WrongIdException()
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

    fun getChat(id: Int, messageId: Int, count: Int): MutableList<Message>? {
        if (users.contains(findById(id))) {

                val index = chats[id]?.indexOfFirst { it.id == messageId }
                if (index != null && index >= 0) {
                    if (chats[id]!!.size >= count) {
                      val subChat = index?.let {
                        chats[id]?.subList(it, index + count).also { it?.forEach { it.read = true } }
                     }
                      return subChat
                   } else throw Error()
                } else throw WrongIdException()
        } else throw WrongIdException()
        return null
    }

    fun countNew(userId: Int): Int? {
        if (users.contains(findById(userId))) {
            return findById(userId)?.userChats?.count { it.value.any { !it.read && it.ownerId != userId } }
        } else throw WrongIdException()
    }

    fun emptySingleton() {
        chats.clear()
        users.clear()
    }

}