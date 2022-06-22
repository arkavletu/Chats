package ru.netology




fun main(){
    val user1 = Service.newUser()
    val user2 = Service.newUser()
    val user3 = Service.newUser()
    val msg = Message(user2.id,"Hi",id = Message.ID(Service.nextId++))
    val message2 = Message(user3.id, "Who R U?",id = Message.ID(Service.nextId++))
    val message3 = Message(user1.id, "HRU?",id = Message.ID(Service.nextId++))

    Service.createMessage(user1.id,msg)
    Service.createMessage(user2.id,message2)
    Service.createMessage(user2.id,message3)

    Service.editMessage(user2.id,user3.id,Message(user3.id,"I know U", id = Message.ID(5)))

    println(Service.chats.values)


}





