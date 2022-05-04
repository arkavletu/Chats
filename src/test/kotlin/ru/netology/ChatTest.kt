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
        Service.createMessage(2,Message(1,"Hi"))
        assertTrue(Service.getAllChats(1).isNotEmpty())
    }

    @Test(expected = WrongIdOfUserException::class)
    fun getAllChatsException() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        Service.createMessage(2,Message(1,"Hi"))
        Service.getAllChats(3)
    }

    @Test
    fun getChat() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        Service.createMessage(2,Message(1,"Hi"))
        Service.createMessage(2,Message(1,"Where R U?"))
        val result = Service.getChat(2,1,2).size
        assertEquals(2, result)
    }

    @Test(expected = WrongIdOfUserException::class)
    fun getChatUserException() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        Service.getChat(5,1,2)
    }

    @Test(expected = WrongIdOfChatException::class)
    fun getChatException() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val user3 = Service.newUser()
        Service.createMessage(2,Message(1,"Hi"))
        Service.createMessage(2,Message(1,"Where R U?"))
        Service.getChat(3,1,7)
    }

    @Test(expected = WrongIdOfMessageException::class)
    fun getChatMessageException() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        Service.createMessage(2,Message(1,"Hi"))
        Service.createMessage(2,Message(1,"Where R U?"))
        Service.getChat(2,4,1)
    }

    @Test
    fun countNew() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        Service.createMessage(2,Message(1,"Hi"))
        val resultForUser1 = Service.countUnreadChats(1)
        val resultForUser2 = Service.countUnreadChats(2)
        assertTrue(resultForUser1 == 0)
        assertTrue(resultForUser2 == 1)
    }

    @Test(expected = WrongIdOfUserException::class)
    fun countNewException(){
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        Service.createMessage(2,Message(1,"Hi"))
        Service.countUnreadChats(3)
    }

}