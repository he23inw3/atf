package com.example.atf.controller.dto

abstract class RequestBase {
	companion object {
		const val USER_ID_PATTERN = "^[0-9a-zA-Z]*$"
		const val MAIL_ADDRESS_PATTERN = "^[a-zA-Z0-9_.+-]+@([a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9]*\\.)+[a-zA-Z]{2,}$"
		const val BIRTHDAY_PATTERN = "^[0-9]{4}\\/[0-9]{2}\\/[0-9]{2}$"
	}
}
