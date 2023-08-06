package com.example.atf.config.holder

import com.example.atf.constant.ApiConstant
import org.slf4j.MDC
import java.util.UUID

object TraceIdHolder {

	fun set() {
		MDC.put(ApiConstant.X_TRACE_ID, UUID.randomUUID().toString())
	}

	@Suppress("SwallowedException")
	fun get(): String {
		return try {
			MDC.get(ApiConstant.X_TRACE_ID)
		} catch (e: NullPointerException) {
			MDC.put(ApiConstant.X_TRACE_ID, UUID.randomUUID().toString())
			MDC.get(ApiConstant.X_TRACE_ID)
		}
	}
}
