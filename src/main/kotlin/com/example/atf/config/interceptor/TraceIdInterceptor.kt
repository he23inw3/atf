package com.example.atf.config.interceptor

import com.example.atf.config.holder.TraceIdHolder
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.weaver.tools.Trace
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Aspect
@Component
class TraceIdInterceptor {

	@Before("@annotation(com.example.atf.config.annotation.TraceId)")
	fun setTraceId(jp: JoinPoint) {
		TraceIdHolder.set()
	}
}
