package com.example.eodideora.common.exception

import com.example.eodideora.common.response.Response
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
internal class RestExceptionHandler {
    @ExceptionHandler(GeneralException::class)
    fun exceptionHandler(ex: GeneralException): ResponseEntity<*> {
        val response = Response(0, ex.message, null)
        return ResponseEntity(response, ex.httpStatus)
    }
}