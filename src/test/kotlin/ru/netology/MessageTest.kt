package ru.netology

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

internal class MessageTest {
    @Before
    fun start(){
        Service.clearSingleton()
    }

    @Test
    fun create() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        Service.createMessage(2,Message(1,"Hi"))
        assertTrue(Service.chats.isNotEmpty())
    }

    @Test
    fun createIfContains() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        Service.createMessage(2,Message(1,"Hi"))
        Service.createMessage(2,Message(1,"Where R U?"))
        assertTrue(Service.chats[2]?.size == 2)
    }

    @Test(expected = WrongIdOfUserException::class)
    fun createThrowException() {
        Service.createMessage(2,Message(1,"Hi"))
    }

    @Test
    fun deleteMessage() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        Service.createMessage(2,Message(1,"Hi"))
        Service.deleteMessage(2, 1)
        assertFalse(Service.chats.contains(2))
    }

    @Test(expected = WrongIdOfChatException::class)
    fun deleteMessageChatException(){
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        Service.deleteMessage(4, 1)
    }

    @Test(expected = WrongIdOfMessageException::class)
    fun deleteMessageIdException(){
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        Service.createMessage(2,Message(1,"Hi"))
        Service.deleteMessage(2, 2)
    }


    @Test
    fun edit() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        Service.createMessage(2,Message(1,"Hi"))
        Service.editMessage(2,1,Message(1,"Bye"))
        assertEquals("Bye", Service.chats[2]?.get(0)?.text)
    }

    @Test(expected = WrongIdOfChatException::class)
    fun editException() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        Service.createMessage(2,Message(1,"Hi"))
        Service.editMessage(5,1,Message(1,"Bye"))
    }

}