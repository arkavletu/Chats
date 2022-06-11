package ru.netology




fun main(){
    val user1 = Service.newUser()
    val user2 = Service.newUser()
    val user3 = Service.newUser()
    val msg = Message(user1.id,"Hi")
    val message = Message(user1.id, "nope")
    println(msg.id.value.toString())
    Service.createMessage(user2.id,msg)
    Service.createMessage(user2.id,message)

    PrintService.printChat(user2.id,user1.id,msg.id,2)


}





