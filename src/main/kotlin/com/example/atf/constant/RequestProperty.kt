package com.example.atf.constant

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class RequestProperty {

	@Value("\${request.http-headers.api-key}")
	val apiKey = ""
}
