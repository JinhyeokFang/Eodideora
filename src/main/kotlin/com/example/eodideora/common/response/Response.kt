package com.example.eodideora.common.response;

data class Response<T> (
    val code: Int,
    val message: String,
    val result: T,
)
