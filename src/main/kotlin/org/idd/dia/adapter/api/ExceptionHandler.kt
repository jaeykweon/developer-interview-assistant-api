package org.idd.dia.adapter.api

import org.idd.dia.domain.BadRequestException
import org.idd.dia.domain.ForbiddenException
import org.idd.dia.domain.InternalServerErrorException
import org.idd.dia.domain.NotFoundException
import org.idd.dia.domain.UnAuthorizedException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * 일부 익셉션 응답이 BAD_REQUEST, Not Found 인 이유는 악의적 사용자에게 정보를 주지 않기 위함.
 */
@RestControllerAdvice
class ExceptionHandler {
    private val logger = LoggerFactory.getLogger(ExceptionHandler::class.java)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(e: Exception): ApiResponse<Nothing> {
        logger.error("Bad Request", e)
        return ApiResponse.badRequest("Bad Request")
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnAuthorizedException::class)
    fun handleUnAuthorizedException(e: Exception): ApiResponse<Nothing> {
        logger.error("Unauthorized", e)
        return ApiResponse.notFound("Unauthorized")
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e: Exception): ApiResponse<Nothing> {
        logger.error("Not Found")
        return ApiResponse.notFound("Not Found")
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ForbiddenException::class)
    fun handleForbiddenException(e: Exception): ApiResponse<Nothing> {
        logger.error("Forbidden Exception Occurred", e)
        return ApiResponse.notFound("Forbidden")
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalServerErrorException::class)
    fun handleInternalServerErrorException(e: Exception): ApiResponse<Nothing> {
        logger.error("Internal Server Error", e)
        return ApiResponse.internalServerError("Internal Server Error")
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ApiResponse<Nothing> {
        logger.error("Unhandled Internal Server Error", e)
        return ApiResponse.internalServerError("Unhandled Internal Server Error")
    }
}
