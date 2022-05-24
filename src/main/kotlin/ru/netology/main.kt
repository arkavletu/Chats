package ru.netology




fun main(){
    val user1 = Service.newUser()
    val user2 = Service.newUser()
    val user3 = Service.newUser()
    Service.createMessage(user3.id,Message(user2.id,"Hi"))
    PrintService.printAllChats(user2.id)
    PrintService.printChat(user3.id, Message.ID(1), 3)

}





