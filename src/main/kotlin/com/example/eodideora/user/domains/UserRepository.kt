package com.example.eodideora.user.domains

import com.example.eodideora.user.domains.User
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface UserRepository: CoroutineCrudRepository<User, Long?> {
    suspend fun findByEmail(email: String): List<User>
}
