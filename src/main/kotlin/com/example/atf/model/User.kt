package com.example.atf.model

import java.time.LocalDate

data class User(
	val id: String,
	val name: String?,
	val birthday: LocalDate?,
	val emailAddress: String?
) {

	fun getAge(): Int {
		if (birthday == null) return 0

		val now = LocalDate.now()
		var age = now.year - birthday.year
		if (now.month < birthday.month) {
			age -= 1
		} else if (now.month == birthday.month &&
			now.dayOfMonth < birthday.dayOfMonth) {
			age -= 1
		}
		return age
	}
}
