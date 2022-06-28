package ru.netology

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

internal class MessageTest {
    @Before
    fun start() {
        Service.clearSingleton()
    }
    @Test
    fun findMsg(){
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val message = Service.createMessage(user2.id, user1.id, "Hi")
        val result = Service.findMessageByText("hi")
        assertTrue(result.contains(message))
    }

    @Test
    fun findMsgEmpty(){
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val message = Service.createMessage(user2.id, user1.id, "Hi")
        val result = Service.findMessageByText("empty")
        assertTrue(result.isEmpty())
    }

    @Test
    fun createMessageAndChat() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val message = Service.createMessage(user2.id, user1.id, "Hi")
        val chat = Service.findChat(user2.id, user1.id)
        chat?.messages?.let { assertTrue(it.contains(message)) }
    }

    @Test
    fun createMessageInExistingChat() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val message = Service.createMessage(user2.id, user1.id, "Hi")
        Service.createMessage(user1.id, user2.id,"Where R U?")
        val chat = Service.findChat(user2.id, user1.id)
        chat?.messages?.let { assertTrue(it.contains(message)) }
        assertTrue(chat?.messages?.size == 2)
    }

    @Test(expected = WrongIdOfUserException::class)
    fun createMessageThrowException() {
        val message = Service.createMessage(User.ID(3), User.ID(7), "Hi")
    }

    @Test
    fun deleteMessage() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val message = Service.createMessage(user2.id, user1.id, "Hi")
        Service.deleteMessage(user1.id, user2.id, message.id)
        val chat = Service.findChat(user2.id, user1.id)
        assertTrue(chat == null)
    }

    @Test(expected = WrongIdOfChatException::class)
    fun deleteMessageChatException() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val message = Service.createMessage(user2.id, user1.id, "Hi")
        Service.deleteMessage(user1.id,User.ID(87), message.id)
    }

    @Test(expected = WrongIdOfMessageException::class)
    fun deleteMessageIdException() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val message = Service.createMessage(user2.id, user1.id, "Hi")
        Service.deleteMessage(user1.id, user2.id, Message.ID(0))
    }


    @Test
    fun edit() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val message = Service.createMessage(user2.id, user1.id, "Hi")
        val chat = Service.findChat(user2.id, user1.id)
        Service.editMessage(user1.id, user2.id , Message(user2.id, "Bye",message.id))
        assertEquals("Bye", chat?.messages?.get(0)?.text)
    }

    @Test(expected = WrongIdOfChatException::class)
    fun editException() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val message = Service.createMessage(user2.id, user1.id, "Hi")
        Service.editMessage(user2.id, User.ID(11), Message(user1.id, "Bye", message.id))
    }

}