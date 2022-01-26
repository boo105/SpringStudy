package panda.tutorials.springboot.SpringStudy.service

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import panda.tutorials.springboot.SpringStudy.datasource.BankDataSource
import panda.tutorials.springboot.SpringStudy.model.Bank

// 동일한 타입을 가진 bean 객체가 2개 이상 존재하는 경우 @Qualifier 정의해줘야함.
@Service
class BankService(@Qualifier("mock") private val dataSource : BankDataSource) {
    fun getBanks(): Collection<Bank> = dataSource.retrieveBanks()

    fun getBank(accountNumber : String) : Bank = dataSource.retrieveBank(accountNumber)

    fun addBank(bank: Bank): Bank = dataSource.createBank(bank)

    fun updateBank(bank: Bank): Bank = dataSource.updateBank(bank)

    fun deleteBank(accountNumber: String) = dataSource.deleteBank(accountNumber)
}