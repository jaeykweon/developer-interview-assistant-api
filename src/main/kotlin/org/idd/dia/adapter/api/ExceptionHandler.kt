package org.idd.dia.adapter.api

import org.idd.dia.domain.BadRequestException
import org.idd.dia.domain.ForbiddenException
import org.idd.dia.domain.NotFoundException
import org.idd.dia.domain.UnAuthorizedException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler() {
    private val logger = LoggerFactory.getLogger(ExceptionHandler::class.java)

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad Request")
    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(e: Exception): ApiResponse<Nothing> {
        logger.info("Bad Request", e)
        return ApiResponse.badRequest("Bad Request")
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Unauthorized")
    @ExceptionHandler(UnAuthorizedException::class)
    fun handleUnAuthorizedException(e: Exception): ApiResponse<Nothing> {
        logger.info("Unauthorized", e)
        return ApiResponse.unauthorized("Unauthorized")
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not Found")
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e: Exception): ApiResponse<Nothing> {
        logger.info("Not Found")
        return ApiResponse.notFound("Not Found")
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Forbidden")
    @ExceptionHandler(ForbiddenException::class)
    fun handleForbiddenException(e: Exception): ApiResponse<Nothing> {
        logger.info("Forbidden Exception Occurred", e)
        return ApiResponse.unauthorized("Forbidden")
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Server Error")
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ApiResponse<Nothing> {
        logger.error("Internal Server Error", e)
        return ApiResponse.internalServerError("Internal Server Error")
    }
}
