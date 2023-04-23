package com.example.atf.exception

import com.example.atf.config.holder.TraceIdHolder
import com.example.atf.controller.dto.ErrorResponse
import com.example.atf.util.ConfigUtil
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@Component
@RestControllerAdvice
class ExceptionHandler(
	private val configUtil: ConfigUtil
) {

	companion object {
		private val logger = LoggerFactory.getLogger(this::class.java)
	}

	@ExceptionHandler(InvalidApiKeyException::class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	fun handleInvalidApiKeyException(e: InvalidApiKeyException): ErrorResponse {
		val traceId = TraceIdHolder.get()
		logger.error(configUtil.getLogMessage("BE9996", traceId))
		return ErrorResponse(
			traceId = traceId,
			errorMessages = listOf("API Key Invalid")
		)
	}

	@ExceptionHandler(MethodArgumentNotValidException::class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	fun handleRequestError(e: MethodArgumentNotValidException): ErrorResponse {
		val traceId = TraceIdHolder.get()
		logger.error(configUtil.getLogMessage("BE9997", traceId))
		return ErrorResponse(
			traceId = traceId,
			errorMessages = e.fieldErrors.map { it.defaultMessage ?: "" }
		)
	}

	@ExceptionHandler(Exception::class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	fun handleException(e: Exception): ErrorResponse {
		val traceId = TraceIdHolder.get()
		logger.error(configUtil.getLogMessage("BE9999", traceId, e.stackTraceToString()))
		return ErrorResponse(
			traceId = traceId,
			errorMessages = listOf("Internal Error")
		)
	}
}
