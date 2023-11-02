package com.example.eodideora.user

import com.example.eodideora.item.Item
import com.example.eodideora.place.Place
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.annotation.Id

data class User(
    @Id
    val id: String? = null,

    val email: String,

    val name: String,

    @Transient
    @Value("null")
    val items: List<Item>? = null,

    @Transient
    @Value("null")
    val places: List<Place>? = null
)
