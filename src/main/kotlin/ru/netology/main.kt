package ru.netology




fun main(){
    val user1 = Service.newUser()
    val user2 = Service.newUser()
    val user3 = Service.newUser()
    val msg = Message(user2.id,"Hi",id = Message.ID(Service.nextId++))
    val message2 = Message(user2.id, "HRU?",id = Message.ID(Service.nextId++))
    Service.createMessage(user3.id,msg)
    Service.createMessage(user3.id,message2)
    PrintService.printChat(user3.id,user2.id,msg.id,5)


}





