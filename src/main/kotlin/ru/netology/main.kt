package ru.netology




fun main(){
    val user1 = Service.newUser()
    val user2 = Service.newUser()
    val user3 = Service.newUser()

    Service.createMessage(user2.id, user1.id,"Who R U?")
    Service.createMessage(user3.id, user2.id,"HRU?")
    Service.createMessage(user1.id, user2.id,"I don't know U")

    Service.editMessage(user2.id,user3.id,Message(user3.id,"I know U", id = Message.ID(5)))



}





