package com.company.common.exception

import com.company.common.dto.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleResourceNotFoundException(ex: ResourceNotFoundException, request: WebRequest): ErrorResponse =
        ErrorResponse(
            status = HttpStatus.NOT_FOUND.value(),
            message = ex.message ?: "Resource not found",
            path = request.getDescription(false).removePrefix("uri=")
        )

    @ExceptionHandler(DuplicateResourceException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleDuplicateResourceException(ex: DuplicateResourceException, request: WebRequest): ErrorResponse =
        ErrorResponse(
            status = HttpStatus.CONFLICT.value(),
            message = ex.message ?: "Resource already exists",
            path = request.getDescription(false).removePrefix("uri=")
        )

    @ExceptionHandler(BusinessException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBusinessException(ex: BusinessException, request: WebRequest): ErrorResponse =
        ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            message = ex.message ?: "Bad request",
            path = request.getDescription(false).removePrefix("uri=")
        )

    @ExceptionHandler(UnauthorizedException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleUnauthorizedException(ex: UnauthorizedException, request: WebRequest): ErrorResponse =
        ErrorResponse(
            status = HttpStatus.UNAUTHORIZED.value(),
            message = ex.message ?: "Unauthorized",
            path = request.getDescription(false).removePrefix("uri=")
        )

    @ExceptionHandler(AccessDeniedException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleAccessDeniedException(ex: AccessDeniedException, request: WebRequest): ErrorResponse =
        ErrorResponse(
            status = HttpStatus.FORBIDDEN.value(),
            message = ex.message ?: "Access denied",
            path = request.getDescription(false).removePrefix("uri=")
        )

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationException(ex: MethodArgumentNotValidException, request: WebRequest): ErrorResponse {
        val fieldErrors = ex.bindingResult.fieldErrors.map { "${it.field}: ${it.defaultMessage}" }
        return ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            message = "Validation failed",
            errors = fieldErrors,
            path = request.getDescription(false).removePrefix("uri=")
        )
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGenericException(ex: Exception, request: WebRequest): ErrorResponse =
        ErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = ex.message ?: "An unexpected error occurred",
            path = request.getDescription(false).removePrefix("uri=")
        )
}
