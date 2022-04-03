package ru.netology




fun main(){
    val user1 = Service.newUser(1)
    val user2 = Service.newUser(2)
    val user3 = Service.newUser(3)

    Service.create(2,Message(1,"Hi"))
    Service.create(2,Message(1,"HAU?"))
    Service.create(3,Message(2,"I'm here"))
}





