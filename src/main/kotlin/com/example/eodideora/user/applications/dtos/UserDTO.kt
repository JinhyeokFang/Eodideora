package com.example.eodideora.user.applications.dtos;

class UserDTO {
    data class findUserDTO (
        val email: String,
    )

    data class registerNewUserDTO (
        val email: String,
    )
}
