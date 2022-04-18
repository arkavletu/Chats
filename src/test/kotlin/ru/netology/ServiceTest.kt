package ru.netology

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


internal class ServiceTest {
    @Before
    fun start() {
        Service.emptySingleton()
    }

    @Test
    fun newUser() {
        Service.newUser(1)
        assertNotNull(Service.users)
    }

    @Test
    fun findById() {
        val user1 = Service.newUser(1)
        val user2 = Service.newUser(2)
        val result = Service.findById(1)
        assertNotNull(result)
    }

    @Test
    fun findByIdNull() {
        val user1 = Service.newUser(1)
        val user2 = Service.newUser(2)
        val result = Service.findById(3)
        assertNull(result)
    }

    @Test
    fun create() {
        val user1 = Service.newUser(1)
        val user2 = Service.newUser(2)
        Service.create(2, Message(1, "Hi"))
        assertTrue(Service.chats[Pair(1, 2)]?.isNotEmpty() == true && Service.chats[Pair(2, 1)]?.isNotEmpty() == true)
    }

    @Test
    fun createIfContains() {
        val user1 = Service.newUser(1)
        val user2 = Service.newUser(2)
        Service.create(2, Message(1, "Hi"))
        Service.create(2, Message(1, "Where R U?"))
        assertTrue(Service.chats[Pair(1, 2)]?.size == 2 && Service.chats[Pair(2, 1)]?.size == 2)
    }

    @Test(expected = WrongIdException::class)
    fun createThrowException() {
        Service.create(2, Message(1, "Hi"))
    }

    @Test
    fun delete() {
        val user1 = Service.newUser(1)
        val user2 = Service.newUser(2)
        Service.create(2, Message(1, "Hi"))
        Service.delete(1, 2)
        assertTrue(!Service.chats.containsKey(Pair(1, 2)) && !Service.chats.containsKey(Pair(2, 1)))
    }

    @Test
    fun getAllChats() {
        val user1 = Service.newUser(1)
        val user2 = Service.newUser(2)
        Service.create(2, Message(1, "Hi"))
        assertTrue(Service.getAllChats(1).isNotEmpty())
    }

    @Test(expected = WrongIdException::class)
    fun getAllChatsException() {
        val user1 = Service.newUser(1)
        val user2 = Service.newUser(2)
        Service.create(2, Message(1, "Hi"))
        Service.getAllChats(3)
    }

    @Test
    fun edit() {
        val user1 = Service.newUser(1)
        val user2 = Service.newUser(2)
        Service.create(2, Message(1, "Hi"))
        Service.edit(2, 1, Message(1, "Bye"))
        assertEquals("Bye", Service.chats[Pair(1, 2)]?.get(0)?.text)
        assertEquals("Bye", Service.chats[Pair(2, 1)]?.get(0)?.text)
    }

    @Test(expected = WrongIdException::class)
    fun editException() {
        val user1 = Service.newUser(1)
        val user2 = Service.newUser(2)
        Service.create(2, Message(1, "Hi"))
        Service.edit(5, 1, Message(1, "Bye"))
    }

    @Test
    fun deleteMessage() {
        val user1 = Service.newUser(1)
        val user2 = Service.newUser(2)
        Service.create(2, Message(1, "Hi"))
        Service.deleteMessage(1, 2, 1)
        assertTrue(Service.chats.isEmpty())
    }

    @Test(expected = WrongIdException::class)
    fun deleteMessageException() {
        val user1 = Service.newUser(1)
        val user2 = Service.newUser(2)
        Service.create(2, Message(1, "Hi"))
        Service.deleteMessage(1, 2, 4)
    }

    @Test
    fun getChat() {
        val user1 = Service.newUser(1)
        val user2 = Service.newUser(2)
        Service.create(2, Message(1, "Hi"))
        Service.create(2, Message(1, "Where R U?"))
        val result = Service.getChat(2, 1, 1, 2)
        assertNotNull(result)
    }

    @Test(expected = WrongIdException::class)
    fun getChatException() {
        val user1 = Service.newUser(1)
        val user2 = Service.newUser(2)
        Service.create(2, Message(1, "Hi"))
        Service.create(2, Message(1, "Where R U?"))
        Service.getChat(3, 2, 1, 2)
    }

    @Test
    fun countNew() {
        val user1 = Service.newUser(1)
        val user2 = Service.newUser(2)
        Service.create(2, Message(1, "Hi"))
        val resultForUser1 = Service.countNew(1)
        val resultForUser2 = Service.countNew(2)
        assertTrue(resultForUser1 == 0 && resultForUser2 == 1)
    }

    @Test(expected = WrongIdException::class)
    fun countNewException() {
        val user1 = Service.newUser(1)
        val user2 = Service.newUser(2)
        Service.create(2, Message(1, "Hi"))
        Service.countNew(3)
    }
}