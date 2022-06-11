package ru.netology

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

internal class ChatTest {
    @Before
    fun start(){
        Service.clearSingleton()
    }

    @Test
    fun getAllChats() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        Service.createMessage(user1.id,Message(user2.id,"Hi"))
        assertTrue(Service.getAllChats(user1.id).isNotEmpty())
        assertTrue(Service.getAllChats(user2.id).isNotEmpty())

    }

    @Test(expected = WrongIdOfUserException::class)
    fun getAllChatsException() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        Service.createMessage(user1.id,Message(user2.id,"Hi"))
        Service.getAllChats(User.ID(java.util.UUID.randomUUID()))
    }

    @Test
    fun getChat() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val message = Message(user2.id, "Hi")
        val message2 = Message(user2.id, "HRU?")
        Service.createMessage(user1.id,message)
        Service.createMessage(user1.id,message2)
        val result = Service.getChat(user1.id,user2.id,message.id,3).size
        assertEquals(2, result)
    }

    @Test(expected = WrongIdOfUserException::class)
    fun getChatUserException() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val message = Message(user2.id, "Hi")
        Service.createMessage(user1.id,message)
        Service.getChat(User.ID(java.util.UUID.randomUUID()),user2.id,message.id,2)
    }

    @Test(expected = WrongIdOfChatException::class)
    fun getChatException() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val user3 = Service.newUser()
        Service.createMessage(user1.id,Message(user2.id,"Hi"))
        Service.createMessage(user2.id,Message(user1.id ,"Where R U?"))
        Service.getChat(user3.id,user2.id, Message.ID(java.util.UUID.randomUUID()),7)
    }

    @Test(expected = WrongIdOfMessageException::class)
    fun getChatMessageException() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        Service.createMessage(user1.id,Message(user2.id,"Hi"))
        Service.createMessage(user2.id,Message(user1.id ,"Where R U?"))
        Service.getChat(user1.id,user2.id,Message.ID(java.util.UUID.randomUUID()),1)
    }

    @Test
    fun countNew() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        Service.createMessage(user1.id,Message(user2.id,"Hi"))
        val resultForUser1 = Service.countUnreadChats(user1.id)
        val resultForUser2 = Service.countUnreadChats(user2.id)
        assertTrue(resultForUser1 == 1)
        assertTrue(resultForUser2 == 0)
    }

    @Test(expected = WrongIdOfUserException::class)
    fun countNewException(){
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        Service.createMessage(user1.id,Message(user2.id,"Hi"))
        Service.countUnreadChats(User.ID(java.util.UUID.randomUUID()))
    }

}