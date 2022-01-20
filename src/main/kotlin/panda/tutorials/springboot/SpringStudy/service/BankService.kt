package panda.tutorials.springboot.SpringStudy.service

import org.springframework.stereotype.Service
import panda.tutorials.springboot.SpringStudy.datasource.BankDataSource
import panda.tutorials.springboot.SpringStudy.model.Bank

@Service
class BankService(private val dataSource : BankDataSource) {
    fun getBanks(): Collection<Bank> = dataSource.retrieveBanks()
    fun getBank(accountNumber : String) : Bank = dataSource.retrieveBank(accountNumber)
}