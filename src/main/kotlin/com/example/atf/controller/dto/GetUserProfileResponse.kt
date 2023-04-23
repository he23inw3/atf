package com.example.atf.controller.dto

data class GetUserProfileResponse(
	val userId: String,
	val name: String,
	val emailAddress: String,
	val age: Int,
	val birthday: String
)
