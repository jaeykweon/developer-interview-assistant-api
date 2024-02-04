package org.idd.dia.adapter.api

import org.idd.dia.domain.BadRequestException
import org.idd.dia.domain.ForbiddenException
import org.idd.dia.domain.InternalServerErrorException
import org.idd.dia.domain.NotFoundException
import org.idd.dia.domain.UnAuthorizedException
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {
    private val logger = LoggerFactory.getLogger(ExceptionHandler::class.java)

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(e: Exception): ApiResponse<Nothing> {
        logger.info("Bad Request", e)
        return ApiResponse.badRequest("Bad Request")
    }

    @ExceptionHandler(UnAuthorizedException::class)
    fun handleUnAuthorizedException(e: Exception): ApiResponse<Nothing> {
        logger.info("Unauthorized", e)
        return ApiResponse.unauthorized("Unauthorized")
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e: Exception): ApiResponse<Nothing> {
        logger.info("Not Found")
        return ApiResponse.notFound("Not Found")
    }

    @ExceptionHandler(ForbiddenException::class)
    fun handleForbiddenException(e: Exception): ApiResponse<Nothing> {
        logger.info("Forbidden Exception Occurred", e)
        return ApiResponse.unauthorized("Forbidden")
    }

    @ExceptionHandler(InternalServerErrorException::class)
    fun handleInternalServerErrorException(e: Exception): ApiResponse<Nothing> {
        logger.error("Internal Server Error", e)
        return ApiResponse.internalServerError("Internal Server Error")
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ApiResponse<Nothing> {
        logger.error("Unhandled Internal Server Error", e)
        return ApiResponse.internalServerError("Unhandled Internal Server Error")
    }
}
