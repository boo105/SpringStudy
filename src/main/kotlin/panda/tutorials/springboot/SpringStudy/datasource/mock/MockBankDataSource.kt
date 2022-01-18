package panda.tutorials.springboot.SpringStudy.datasource.mock

import org.springframework.stereotype.Repository
import panda.tutorials.springboot.SpringStudy.datasource.BankDataSource
import panda.tutorials.springboot.SpringStudy.model.Bank

@Repository
class MockBankDataSource : BankDataSource {
    val banks = listOf(
        Bank("123456", 3.14, 1),
        Bank("1010", 17.0, 0),
        Bank("5678", 0.0, 100)
    )

    override fun retrieveBanks(): Collection<Bank> = banks
}