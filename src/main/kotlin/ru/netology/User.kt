package ru.netology

data class User(
    val id: ID,


    ) {
    @JvmInline
    value class ID(val value: Int)

    override fun toString(): String {
        return "user id $id"
    }

}



