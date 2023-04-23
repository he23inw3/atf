package com.example.atf.util

import org.springframework.context.MessageSource
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import java.util.Locale

@Component
class ConfigUtil(
	private val environment: Environment,
	private val messageSource: MessageSource
) {

	fun getProperty(key: String): String {
		return environment.getProperty(key) ?: throw IllegalArgumentException("設定値を取得できませんでした。 key=$key")
	}

	fun getLogMessage(key: String, vararg args: String?): String {
		return messageSource.getMessage(key, args, Locale.JAPAN)
	}
}
