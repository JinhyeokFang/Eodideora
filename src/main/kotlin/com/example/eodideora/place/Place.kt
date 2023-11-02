package com.example.eodideora.place

import com.example.eodideora.item.Item
import com.example.eodideora.user.User
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
) {}