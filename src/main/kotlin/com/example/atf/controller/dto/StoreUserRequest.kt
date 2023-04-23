package com.example.atf.controller.dto

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class StoreUserRequest(
	@field:NotBlank(message = "userId is blank")
	@field:Pattern(regexp = USER_ID_PATTERN, message = "userId is invalid pattern")
	@field:Size(min = 4, max = 32, message = "userId is invalid size")
	val userId: String?,
	@field:NotBlank(message = "name is blank")
	@field:Size(min = 4, max = 64, message = "name is invalid size")
	val name: String?,
	@field:NotBlank(message = "emailAddress is blank")
	@field:Pattern(regexp = MAIL_ADDRESS_PATTERN, message = "emailAddress is invalid pattern")
	val emailAddress: String?,
	@field:NotBlank(message = "birthday is blank")
	@field:Pattern(regexp = BIRTHDAY_PATTERN, message = "birthday is invalid pattern")
	@field:JsonFormat(pattern = "yyyy/MM/dd")
	val birthday: String?
): RequestBase()
