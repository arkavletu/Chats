package ru.netology

data class Chat (
    val users: Pair<User.ID, User.ID>,

){
    val messages: MutableList<Message> = mutableListOf()
}