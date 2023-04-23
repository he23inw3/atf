package com.example.atf.exception

class DbAccessException: RuntimeException {

	constructor(message: String) : super(message)

	constructor(message: String, throwable: Throwable): super(message, throwable)
}
