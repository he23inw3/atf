package com.example.atf.config.holder

import com.example.atf.constant.ApiConstant
import com.example.atf.exception.ApiException
import org.slf4j.MDC
import java.util.UUID

object TraceIdHolder {

	fun set() {
		MDC.put(ApiConstant.X_TRACE_ID, UUID.randomUUID().toString())
	}

	fun get(): String {
		return try {
			MDC.get(ApiConstant.X_TRACE_ID)
		} catch (e: NullPointerException) {
			MDC.put(ApiConstant.X_TRACE_ID, UUID.randomUUID().toString())
			MDC.get(ApiConstant.X_TRACE_ID)
		}
	}
}
