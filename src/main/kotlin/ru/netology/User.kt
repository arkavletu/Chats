package ru.netology

data class User(
    val id: ID = ID(),


    ) {
    // не дали создать переменную possibleId = Service.users.size + 1, ID ее снаружи не видит, а внутри создавать низзя
    @JvmInline
    value class ID(val value: Int = if(Service.users.none { it.id.value == Service.users.size + 1}) Service.users.size + 1
    else Service.users.size + 2)

    override fun toString(): String {
        return "user id $id"
    }

}


