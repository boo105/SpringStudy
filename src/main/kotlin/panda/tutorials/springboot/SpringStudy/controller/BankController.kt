package panda.tutorials.springboot.SpringStudy.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import panda.tutorials.springboot.SpringStudy.model.Bank
import panda.tutorials.springboot.SpringStudy.service.BankService

@RestController
@RequestMapping("/api/banks")
class BankController(private val service : BankService) {

    // @ExceptionHandler같은 경우는 @Controller, @RestController가 적용된 Bean내에서 발생하는 예외를 잡아서 하나의 메서드에서 처리해주는 기능을 한다.
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e : NoSuchElementException) : ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @GetMapping
    fun getBanks() : Collection<Bank> = service.getBanks()

    // @PathVariable은 query 변수임.(파라미터)
    @GetMapping("/{accountNumber}")
    fun getBank(@PathVariable accountNumber : String) : Bank = service.getBank(accountNumber)

}