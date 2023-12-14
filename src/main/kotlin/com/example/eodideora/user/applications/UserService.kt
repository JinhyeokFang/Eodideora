package com.example.eodideora.user.applications

import com.example.eodideora.common.exception.GeneralException
import com.example.eodideora.user.applications.dtos.UserDTO
import com.example.eodideora.user.domains.User
import com.example.eodideora.user.domains.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class UserService (
    private val userRepository: UserRepository,
) {
    suspend fun findUser(dto: UserDTO.findUserDTO): User {
        val email = dto.email
        val user: User = this.userRepository.findOneByEmail(email)
            ?: throw GeneralException("User Not Found", 0, HttpStatus.NOT_FOUND)
        return user
    }
}
