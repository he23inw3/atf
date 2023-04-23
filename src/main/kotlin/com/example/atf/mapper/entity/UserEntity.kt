package com.example.atf.mapper.entity

import java.time.LocalDate
import java.time.LocalDateTime

data class UserEntity(
	val id: String,
	val name: String? = null,
	val birthday: LocalDate? = null,
	val emailAddress: String? = null,
	val createdBy: String? = null,
	val createdAt: LocalDateTime? = null,
	val updatedBy: String? = null,
	val updatedAt: LocalDateTime? = null
)
