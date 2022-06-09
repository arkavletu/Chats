package ru.netology

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

internal class MessageTest {
    @Before
    fun start() {
        Service.clearSingleton()
    }

    @Test
    fun createMessageAndChat() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val message = Message(user2.id, "Hi")
        Service.createMessage(user1.id, message)
        val chat = Service.chats[Pair(user1.id,user2.id)]
        assertTrue(Service.chats.containsKey(Pair(user1.id,user2.id)))
        assertTrue(chat?.isNotEmpty() == true)
    }

    @Test
    fun createMessageInExistingChat() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        Service.createMessage(user2.id, Message(user1.id, "Hi"))
        Service.createMessage(user2.id, Message(user1.id, "Where R U?"))
        assertTrue(Service.chats[Pair(user2.id,user1.id)]?.size == 2)
    }

    @Test(expected = WrongIdOfUserException::class)
    fun createMessageThrowException() {
        Service.createMessage(User.ID(java.util.UUID.randomUUID()), Message(User.ID(java.util.UUID.randomUUID()), "Hi"))
    }

    @Test
    fun deleteMessage() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val message = Message(user2.id, "Hi")
        Service.createMessage(user1.id, message)
        Service.deleteMessage(user1.id, user2.id, message.id)
        assertFalse(Service.chats.contains(Pair(user1.id, user2.id)))
    }

    @Test(expected = WrongIdOfChatException::class)
    fun deleteMessageChatException() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val message = Message(user2.id, "Hi")
        Service.createMessage(user1.id, message)
        Service.deleteMessage(user1.id,User.ID(java.util.UUID.randomUUID()), message.id)
    }

    @Test(expected = WrongIdOfMessageException::class)
    fun deleteMessageIdException() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val message = Message(user2.id, "Hi")
        Service.createMessage(user1.id, message)
        Service.deleteMessage(user1.id, user2.id, Message.ID(java.util.UUID.randomUUID()))
    }


    @Test
    fun edit() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val message = Message(user2.id, "Hi")
        Service.createMessage(user1.id, message)
        Service.editMessage(user1.id, user2.id , message.id, Message(user2.id, "Bye"))
        assertEquals("Bye", Service.chats[Pair(user1.id, user2.id)]?.get(0)?.text)
    }

    @Test(expected = WrongIdOfChatException::class)
    fun editException() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val message = Message(user2.id, "Hi")
        Service.createMessage(user1.id, message)
        Service.editMessage(user2.id, User.ID(java.util.UUID.randomUUID()), message.id, Message(user1.id, "Bye"))
    }

}