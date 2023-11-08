package com.example.eodideora.util

enum class JwtType(private val label: String) {
    ACCESS_TOKEN("ACCESS_TOKEN"),
    REFRESH_TOKEN("REFRESH_TOKEN");

    override fun toString(): String {
        return label
    }
}