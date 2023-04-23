package com.example.atf.controller.dto

data class UpdateUserRequest(
	val name: String,
	val emailAddress: String?,
	val birthday: String?
)
