package ru.netology

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

internal class ChatTest {
    @Before
    fun start(){
        Service.clearSingleton()
    }

    @Test
    fun findChat(){
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val message = Service.createMessage(user2.id, user1.id, "Hi")
        val chat = Service.findChat(user1.id, user2.id)
        assertNotNull(chat)
    }

    @Test
    fun findChatNull(){
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val message = Service.createMessage(user2.id, user1.id, "Hi")
        val chat = Service.findChat(User.ID(7), user1.id)
        assertNull(chat)
    }

    @Test
    fun getAllChats() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val message = Service.createMessage(user2.id, user1.id, "Hi")
        assertTrue(Service.getAllChats(user1.id).isNotEmpty())
        assertTrue(Service.getAllChats(user2.id).isNotEmpty())

    }

    @Test(expected = WrongIdOfUserException::class)
    fun getAllChatsException() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val message = Service.createMessage(user2.id, user1.id, "Hi")
        Service.getAllChats(User.ID(6))
    }

    @Test
    fun getChat() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val message = Service.createMessage(user2.id, user1.id, "Hi")
        val message2 = Service.createMessage(user2.id, user1.id,"HRU?")
        val result = Service.getChat(user1.id,user2.id,message.id,3).messages.size
        assertEquals(2, result)
    }

    @Test(expected = WrongIdOfUserException::class)
    fun getChatUserException() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val message = Service.createMessage(user2.id, user1.id, "Hi")
        Service.getChat(User.ID(9),user2.id,message.id,2)
    }

    @Test(expected = WrongIdOfChatException::class)
    fun getChatException() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val user3 = Service.newUser()
        val message = Service.createMessage(user2.id, user1.id, "Hi")
        val message2 = Service.createMessage(user2.id, user1.id,"HRU?")
        Service.getChat(user3.id,user2.id, Message.ID(4),7)
    }

    @Test(expected = WrongIdOfMessageException::class)
    fun getChatMessageException() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val message = Service.createMessage(user2.id, user1.id, "Hi")
        val message2 = Service.createMessage(user2.id, user1.id,"HRU?")
        Service.getChat(user1.id,user2.id,Message.ID(22),1)
    }

    @Test
    fun countNew() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val message = Service.createMessage(user2.id, user1.id, "Hi")
        val resultForUser1 = Service.countUnreadChats(user1.id)
        val resultForUser2 = Service.countUnreadChats(user2.id)
        assertTrue(resultForUser1 == 1)
        assertTrue(resultForUser2 == 0)
    }

    @Test(expected = WrongIdOfUserException::class)
    fun countNewException(){
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val message = Service.createMessage(user2.id, user1.id, "Hi")
        Service.countUnreadChats(User.ID(56))
    }

}