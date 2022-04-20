package org.example.main.models

object UserRepositoryImpl: UserRepository {
    override fun findByNickname(nickname: String) : User? {
        return userMap[nickname]
    }

    override fun findById(id: Long) : User = TODO("Method findById unrealised")

    override fun contains(user: User): Boolean {
        return userMap.containsKey(user.nickname)
    }

    override fun appendUser(user: User) {
        userMap[user.nickname!!] = user
    }

    private val userMap : MutableMap<String, User> = HashMap()
}