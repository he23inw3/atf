package com.example.atf.config.interceptor

import com.example.atf.config.annotation.DbAccess
import com.example.atf.exception.DbAccessException
import com.example.atf.util.ConfigUtil
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException
import org.springframework.stereotype.Component

@Aspect
@Component
class DbAccessInterceptor(
	private val configUtil: ConfigUtil
) {

	companion object {
		private val logger = LoggerFactory.getLogger(this::class.java)
	}

	@AfterThrowing(pointcut = "@annotation(com.example.atf.config.annotation.DbAccess)", throwing = "e")
	fun afterThrowing(joinPoint: JoinPoint, e: DataAccessException) {
		val signature = joinPoint.signature as MethodSignature
		val annotation = signature.method.getAnnotation(DbAccess::class.java)
		logger.error(configUtil.getLogMessage("BE9995", annotation.name))
		throw DbAccessException(e.message ?: "", e)
	}
}
