package org.idd.dia.adapter.api

import org.idd.dia.domain.ForbiddenException
import org.idd.dia.domain.UnAuthorizedException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Unauthorized")
    @ExceptionHandler(UnAuthorizedException::class)
    fun handleUnAuthorizedException() {
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Forbidden")
    @ExceptionHandler(ForbiddenException::class)
    fun handleForbiddenException() {
    }
}
