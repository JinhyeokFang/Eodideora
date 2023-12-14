package com.example.eodideora.user.applications

import com.example.eodideora.common.exception.GeneralException
import com.example.eodideora.user.applications.dtos.UserDTO
import com.example.eodideora.user.domains.User
import com.example.eodideora.user.domains.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService (
    private val userRepository: UserRepository,
) {
    suspend fun findUser(dto: UserDTO.findUserDTO): User {
        val email = dto.email
        val user: User = this.userRepository.findOneByEmail(email)
            ?: throw GeneralException("유저를 찾을 수 없습니다.", 0, HttpStatus.NOT_FOUND)
        return user
    }

    suspend fun registerNewUser(dto: UserDTO.registerNewUserDTO) {
        val user = this.userRepository.findOneByEmail(dto.email)
        if (user != null) {
            throw GeneralException("유저가 이미 존재합니다.", 0, HttpStatus.CONFLICT)
        }
        val newUser = User(
            UUID.randomUUID().toString(),
            dto.email,
            dto.email
        )
        this.userRepository.save(newUser)
    }
}
