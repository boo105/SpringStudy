package panda.tutorials.springboot.SpringStudy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringStudyApplication

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
