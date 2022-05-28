package ru.netology




fun main(){
    val user1 = Service.newUser()
    val user2 = Service.newUser()
    val user3 = Service.newUser()
    val msg = Message(user2.id,"Hi")
    Service.createMessage(user3.id,msg)
    //PrintService.printAllChats(user2.id)
    PrintService.printChat(user3.id, msg.id, 1)

}





