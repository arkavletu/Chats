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
        val message = Message(user2.id, "Hi",Message.ID(Service.nextId++))
        Service.createMessage(user1.id, message)
        val chat = Service.chats.values.find{it.messages.contains(message)}
        assertTrue(Service.chats.containsKey(chat?.id))
        chat?.messages?.isNotEmpty()?.let { assertTrue(it) }
    }

    @Test
    fun createMessageInExistingChat() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val message = Message(user2.id, "Hi",Message.ID(Service.nextId++))
        Service.createMessage(user1.id,message)
        Service.createMessage(user2.id, Message(user1.id, "Where R U?",Message.ID(Service.nextId++)))
        val chat = Service.chats.values.find{it.messages.contains(message)}
        assertTrue(Service.chats[chat?.id]?.messages?.size == 2)
    }

    @Test(expected = WrongIdOfUserException::class)
    fun createMessageThrowException() {
        Service.createMessage(User.ID(3), Message(User.ID(4), "Hi",Message.ID(Service.nextId++)))
    }

    @Test
    fun deleteMessage() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val message = Message(user2.id, "Hi",Message.ID(Service.nextId++))
        Service.createMessage(user1.id, message)
        val chat = Service.chats.values.find{it.messages.contains(message)}
        Service.deleteMessage(user1.id, user2.id, message.id)
        assertFalse(Service.chats.contains(chat?.id))
    }

    @Test(expected = WrongIdOfChatException::class)
    fun deleteMessageChatException() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val message = Message(user2.id, "Hi",Message.ID(Service.nextId++))
        Service.createMessage(user1.id, message)
        Service.deleteMessage(user1.id,User.ID(87), message.id)
    }

    @Test(expected = WrongIdOfMessageException::class)
    fun deleteMessageIdException() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val message = Message(user2.id, "Hi",Message.ID(Service.nextId++))
        Service.createMessage(user1.id, message)
        Service.deleteMessage(user1.id, user2.id, Message.ID(0))
    }


    @Test
    fun edit() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val message = Message(user2.id, "Hi",Message.ID(Service.nextId++))
        Service.createMessage(user1.id, message)
        val chat = Service.chats.values.find{it.messages.contains(message)}
        Service.editMessage(user1.id, user2.id , Message(user2.id, "Bye",message.id))
        assertEquals("Bye", Service.chats[chat?.id]?.messages?.get(0)?.text)
    }

    @Test(expected = WrongIdOfChatException::class)
    fun editException() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val message = Message(user2.id, "Hi",Message.ID(Service.nextId++))
        Service.createMessage(user1.id, message)
        Service.editMessage(user2.id, User.ID(11), Message(user1.id, "Bye", message.id))
    }

}