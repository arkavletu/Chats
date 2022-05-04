package ru.netology

data class User(
    var id: Int = 0,


    ) {
    override fun toString(): String {
        return "user id $id"
    }

}


