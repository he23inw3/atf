package com.example.atf.controller

import com.example.atf.constant.RequestProperty
import com.example.atf.exception.InvalidApiKeyException
import com.example.atf.util.ConfigUtil
import org.slf4j.LoggerFactory
import java.time.format.DateTimeFormatter

abstract class ControllerBase(
	private val configUtil: ConfigUtil,
	private val requestProperty: RequestProperty
) {

	companion object {
		private val logger = LoggerFactory.getLogger(this::class.java)
		const val API_KEY = "Api-Key"

		val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
	}

	fun checkApiKey(apiKey: String?) {
		if (logger.isDebugEnabled) {
			logger.debug("request : {}", apiKey)
			logger.debug("properties : {}", requestProperty.apiKey)
		}
		if (requestProperty.apiKey != apiKey) {
			throw InvalidApiKeyException("APIキーに誤りがあります。")
		}
	}
}
