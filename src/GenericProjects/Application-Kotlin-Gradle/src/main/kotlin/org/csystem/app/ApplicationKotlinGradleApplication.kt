package org.csystem.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ApplicationKotlinGradleApplication

fun main(args: Array<String>) {
	runApplication<ApplicationKotlinGradleApplication>(*args)
}
