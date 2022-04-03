package ru.netology

data class User(
    val id: Int,


    ) {
    val userChats: MutableMap<Int, MutableList<Message>>
        get() {
            return Service.chats.filter { it.key == this.id || it.value.any { it.ownerId == this.id } }
                    as MutableMap<Int, MutableList<Message>>
        }

    override fun toString(): String {
        return "user id $id"
    }

}


