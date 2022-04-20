package org.example.main.models

import kotlinx.serialization.Serializable
import kotlin.reflect.full.memberProperties

@Serializable
data class User(
    val nickname: String? = null,
    val password: String? = null,
    val mail:     String? = null,
    val firstName:  String? = null,
    val secondName: String? = null,
)

fun User.haveNull() : Boolean {
    for (member in User::class.memberProperties){
        if (member.getValue(this, member) == null) return true
    }

    return false
}