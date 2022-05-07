package ru.netology

data class User(
    var id: Int = 0


    ) {
    val userChats: Sequence<Map.Entry<Pair<Int,Int>, MutableList<Message>>>
        get() {
            return Service.chats.asSequence().filter { it.key.second == id }
        }


    override fun toString(): String {
        return "user id $id"
    }

}



