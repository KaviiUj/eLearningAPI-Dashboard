package com.tech4gen.eLearning.security

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class HashEncoder {

    private val bCrypt = BCryptPasswordEncoder()

    fun encode(password: String): String = bCrypt.encode(password)

    fun matches(rawPassword: String, encodedPassword: String): Boolean = bCrypt.matches(rawPassword, encodedPassword)
}