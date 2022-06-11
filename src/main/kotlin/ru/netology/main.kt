package ru.netology




fun main(){
    val user1 = Service.newUser()
    val user2 = Service.newUser()
    val user3 = Service.newUser()
    val msg = Message(user1.id,"Hi")
    val message = Message(user2.id, "nope")
    //println(msg.id.value.toString())
    Service.createMessage(user2.id,msg)
    Service.createMessage(user1.id,message)

    println(Service.getAllChats(user1.id))


}





