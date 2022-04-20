package org.example.main.models

interface UserRepository {
    fun findByNickname(nickname: String): User?
    fun findById(id: Long): User?
    fun contains(user: User): Boolean
    fun appendUser(user: User)
}