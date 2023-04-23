package com.example.atf.exception

abstract class ApiException: RuntimeException {
	constructor(message: String)

	constructor(message: String, throwable: Throwable)
}
