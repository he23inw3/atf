package com.example.atf.exception

class InvalidApiKeyException: ApiException {
	constructor(message: String) : super(message)

	constructor(message: String, throwable: Throwable): super(message, throwable)
}
