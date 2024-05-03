package org.idd.dia.adapter.api

import org.idd.dia.adapter.external.SlackHandler
import org.idd.dia.domain.BadRequestException
import org.idd.dia.domain.ConflictException
import org.idd.dia.domain.ForbiddenException
import org.idd.dia.domain.InternalServerErrorException
import org.idd.dia.domain.NotFoundException
import org.idd.dia.domain.UnAuthorizedException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest

@RestControllerAdvice
class ExceptionHandler(
    private val slackHandler: SlackHandler,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(
        webRequest: WebRequest,
        e: BadRequestException,
    ): ApiResponse<Nothing> {
        logger.info("Bad Request", e)
        return ApiResponse.badRequest("Bad Request")
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnAuthorizedException::class)
    fun handleUnAuthorizedException(
        webRequest: WebRequest,
        e: UnAuthorizedException,
    ): ApiResponse<Nothing> {
        logger.info("Unauthorized", e)
        return ApiResponse.notFound("Unauthorized")
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(
        webRequest: WebRequest,
        e: NotFoundException,
    ): ApiResponse<Nothing> {
        logger.info("Not Found")
        return ApiResponse.notFound("Not Found")
    }

    /**
     * 악의적 사용자에게 정보를 주지 않기 위함 (ex. 깃허브)
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ForbiddenException::class)
    fun handleForbiddenException(
        webRequest: WebRequest,
        e: ForbiddenException,
    ): ApiResponse<Nothing> {
        logger.info("Forbidden Exception Occurred", e)
        return ApiResponse.notFound("Forbidden")
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException::class)
    fun handleConflictException(
        webRequest: WebRequest,
        e: ConflictException,
    ): ApiResponse<Nothing> {
        logger.info("Conflict Exception Occurred", e)
        return ApiResponse.conflict("Conflict")
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalServerErrorException::class)
    fun handleInternalServerErrorException(
        webRequest: WebRequest,
        e: InternalServerErrorException,
    ): ApiResponse<Nothing> {
        slackHandler.sendErrorMessage(webRequest, e)
        logger.error("Internal Server Error", e)
        return ApiResponse.internalServerError("Internal Server Error")
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleException(
        webRequest: WebRequest,
        e: Exception,
    ): ApiResponse<Nothing> {
        slackHandler.sendErrorMessage(webRequest, e)
        logger.error("Unhandled Internal Server Error", e)
        return ApiResponse.internalServerError("Unhandled Internal Server Error")
    }
}
