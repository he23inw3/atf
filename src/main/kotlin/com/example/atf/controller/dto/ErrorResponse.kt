package com.example.atf.controller.dto

data class ErrorResponse(
	val traceId: String,
	val errorMessages: List<String>
)
