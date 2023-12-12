package com.example.eodideora.common.exception

import org.springframework.http.HttpStatus

class GeneralException (
    override val message: String,
    val code: Int,
    val httpStatus: HttpStatus,
): Exception(
    message,
)
