package org.example.main.models

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Users : UserRepository {
    init {
        transaction(DbConnection.database) {
            SchemaUtils.create(UsersTable)
        }
    }

    override fun findByNickname(nickname: String) : User? {
        val user = transaction(DbConnection.database) {
            UsersTable.select{
                UsersTable.nickname eq nickname
            }.limit(1).firstOrNull()
        }

        return user?.let {
            User(
                it[UsersTable.nickname],
                it[UsersTable.pass],
                it[UsersTable.mail],
                it[UsersTable.firstName],
                it[UsersTable.lastName],
            )
        }
    }

    override fun findById(id: Long) : User? {
        val user = transaction(DbConnection.database){
            UsersTable.select {
                UsersTable.id eq id.toInt()
            }.limit(1).firstOrNull()
        }

        return user?.let{
            User(
                it[UsersTable.nickname],
                it[UsersTable.pass],
                it[UsersTable.mail],
                it[UsersTable.firstName],
                it[UsersTable.lastName],
            )
        }
    }

    override fun contains(user: User): Boolean {
        return !transaction(DbConnection.database) {
            UsersTable.select {
                UsersTable.nickname eq user.nickname!!
            }.limit(1).empty()
        }
    }

    override fun appendUser(user: User) {
        transaction(DbConnection.database){
            UsersTable.insert{
                it[nickname] = user.nickname!!
                it[pass] = user.password!!
                it[mail] = user.mail!!
                it[firstName] = user.firstName!!
                it[lastName] = user.lastName!!
            }
        }
    }
}

internal object UsersTable : IntIdTable(name = "users") {
    val nickname = varchar("nickname", 255).uniqueIndex()
    val pass = varchar("pass", 255)
    val mail = varchar("mail", 255).uniqueIndex()
    val firstName = varchar("firstName", 255)
    val lastName = varchar("lastName", 255)
}