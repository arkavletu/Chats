package ru.netology




fun main(){
    val user1 = Service.newUser()
    val user2 = Service.newUser()
    val user3 = Service.newUser()

    Service.createMessage(2,Message(1,"Hi"))
    Service.editMessage(2,1, Message(1,"text"))
    println(Service.getChat(2,1,6).toString())

}





