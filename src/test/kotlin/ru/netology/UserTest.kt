package ru.netology

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


internal class UserTest {
    @Before
    fun start(){
        Service.clearSingleton()
    }

    @Test
    fun newUser() {
        Service.newUser()
        assertTrue(Service.users.isNotEmpty())
    }

    @Test
    fun findById() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val result = Service.findUserById(1)
        assertNotNull(result)
    }

    @Test
    fun findByIdNull() {
        val user1 = Service.newUser()
        val user2 = Service.newUser()
        val result = Service.findUserById(3)
        assertNull(result)
    }



}