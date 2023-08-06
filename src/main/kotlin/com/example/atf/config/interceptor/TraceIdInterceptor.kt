package com.example.atf.config.interceptor

import com.example.atf.config.holder.TraceIdHolder
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component

@Aspect
@Component
class TraceIdInterceptor {

	@Before("@annotation(com.example.atf.config.annotation.TraceId)")
	fun setTraceId() {
		TraceIdHolder.set()
	}
}
