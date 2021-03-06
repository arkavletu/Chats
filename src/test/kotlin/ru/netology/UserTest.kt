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
        val user = Service.newUser()
        assertNotNull(Service.findUserById(user.id))
    }

    @Test
    fun findById() {
        val user1 = Service.newUser()
        val result = Service.findUserById(user1.id)
        assertTrue(result?.id == user1.id)
    }

    @Test
    fun findByIdNull() {
        val user1 = Service.newUser()
        val result = Service.findUserById(User.ID(0))
        assertNull(result)
    }



}