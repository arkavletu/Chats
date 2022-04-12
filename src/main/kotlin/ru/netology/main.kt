package ru.netology




fun main(){
    val userOnline = Service.newUser(1) // online for service
    val user2 = Service.newUser(2)
    val user3 = Service.newUser(3)

    Service.create(2,Message(1,"Hi"))
    Service.create(1,Message(2,"HAU?"))
    Service.create(3,Message(1,"I'm here"))
    println(Service.getChat(1,2,2,1))
}





