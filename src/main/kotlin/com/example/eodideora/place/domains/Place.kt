package com.example.eodideora.place.domains

import com.example.eodideora.item.domains.Item
import com.example.eodideora.user.domains.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.annotation.Id

data class Place (
    @Id
    val id: String? = null,

    val name: String,

    val description: String,

    @Transient
    @Value("null")
    val user: User? = null,

    @Transient
    @Value("null")
    val items: List<Item>? = null
)
