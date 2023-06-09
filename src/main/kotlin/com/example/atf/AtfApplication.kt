package com.example.atf

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AtfApplication

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
	runApplication<AtfApplication>(*args)
}
