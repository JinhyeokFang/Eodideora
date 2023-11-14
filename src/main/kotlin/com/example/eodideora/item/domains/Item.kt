package com.example.eodideora.item.domains

import com.example.eodideora.place.domains.Place
import com.example.eodideora.user.domains.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.annotation.Id

data class Item (
    @Id
    val id: String? = null,

    val name: String,

    val description: String,

    @Transient
    @Value("null")
    val user: User? = null,

    @Transient
    @Value("null")
    val place: Place? = null
)
