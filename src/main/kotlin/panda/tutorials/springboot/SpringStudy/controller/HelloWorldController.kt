package panda.tutorials.springboot.SpringStudy.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("api/hello")
class HelloWorldController {

    // 그냥 문자열만 반환할거니까 변수형태로 선언함
    @GetMapping()
    fun helloWorld() : String = "Hello, this is a REST endpoint!"

}