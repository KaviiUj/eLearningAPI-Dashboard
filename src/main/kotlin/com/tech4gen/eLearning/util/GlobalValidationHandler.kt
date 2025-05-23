package com.tech4gen.eLearning.util

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.validation.FieldError
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.NoHandlerFoundException
import javax.naming.AuthenticationException

@RestControllerAdvice
class GlobalValidationHandler {

    @ExceptionHandler(HttpClientErrorException::class)
    fun handleHttpClientErrorException(ex: HttpClientErrorException): ResponseEntity<Map<String, String>> {
        val response = mapOf("message" to ex.message?.substring(4))
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response) as ResponseEntity<Map<String, String>>
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(ex: AuthenticationException): ResponseEntity<Map<String, String>> {
        val response = mapOf("message" to "Invalid or token expired!")
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String>> {
        val error = ex.bindingResult.allErrors.firstOrNull()
        val fieldName = (error as? FieldError)?.field ?: "unknown"
        val errorMessage = error?.defaultMessage ?: "Validation failed"
        val response = mapOf(
            "field" to fieldName,
            "message" to errorMessage
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentialsException(ex: BadCredentialsException): ResponseEntity<Map<String, String>> {
        val response = mapOf("message" to ex.message)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response) as ResponseEntity<Map<String, String>>
    }

    @ExceptionHandler(ResponseStatusException::class)
    fun handleBadCredentialsException(ex: ResponseStatusException): ResponseEntity<Map<String, String>> {
        val response = mapOf("message" to (ex.reason ?: "An error occurred"))
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response) as ResponseEntity<Map<String, String>>
    }

    @ExceptionHandler(UsernameNotFoundException::class)
    fun handleUsernameNotFoundException(ex: UsernameNotFoundException): ResponseEntity<Map<String, String>> {
        val response = mapOf("message" to ex.message)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response) as ResponseEntity<Map<String, String>>
    }

   /* @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(ex: AccessDeniedException): ResponseEntity<Map<String, String>> {
        val response = mapOf("message" to "Access denied: ${ex.message}")
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(ex: HttpMessageNotReadableException): ResponseEntity<Map<String, String>> {
        val response = mapOf("message" to "Invalid request body: ${ex.message}")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingServletRequestParameterException(ex: MissingServletRequestParameterException): ResponseEntity<Map<String, String>> {
        val response = mapOf("message" to "Missing parameter: ${ex.parameterName}")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }

    @ExceptionHandler(TypeMismatchException::class)
    fun handleTypeMismatchException(ex: TypeMismatchException): ResponseEntity<Map<String, String>> {
        val response = mapOf("message" to "Invalid parameter type: ${ex.message}")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupportedException(ex: HttpRequestMethodNotSupportedException): ResponseEntity<Map<String, String>> {
        val response = mapOf("message" to "Method not allowed: ${ex.method}")
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response)
    }

    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNoHandlerFoundException(ex: NoHandlerFoundException): ResponseEntity<Map<String, String>> {
        val response = mapOf("message" to "Resource not found: ${ex.requestURL}")
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response)
    }


    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolationException(ex: DataIntegrityViolationException): ResponseEntity<Map<String, String>> {
        val response = mapOf("message" to "Database constraint violation: ${ex.message}")
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(ex: ConstraintViolationException): ResponseEntity<Map<String, String>> {
        val message = ex.constraintViolations.firstOrNull()?.let { "${it.propertyPath}: ${it.message}" } ?: "Validation failed"
        val response = mapOf("message" to message)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
    fun handleHttpMediaTypeNotSupportedException(ex: HttpMediaTypeNotSupportedException): ResponseEntity<Map<String, String>> {
        val response = mapOf("message" to "Unsupported media type: ${ex.contentType}")
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(response)
    }



    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<Map<String, String>> {
        val response = mapOf("message" to "Internal server error: ${ex.message}")
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response)
    }*/
}
