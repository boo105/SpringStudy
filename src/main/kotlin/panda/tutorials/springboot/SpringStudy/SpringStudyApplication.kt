package panda.tutorials.springboot.SpringStudy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@SpringBootApplication
class SpringStudyApplication {

	// Bean 객체인 restTemplate 추가
	@Bean
	fun restTemplate(builder : RestTemplateBuilder) : RestTemplate = builder.build()
}

/*
*  Spring layer
*  Web Layer (Controllers, REST mappings)
*  Service Layer (Servicaes, business logic)
*  Data Source (Data retrieval, storage)  음 repository 같은 거인듯
*  Data Layer (Models, serialization)
* */

fun main(args: Array<String>) {
	runApplication<SpringStudyApplication>(*args)
}
