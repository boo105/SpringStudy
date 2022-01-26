package panda.tutorials.springboot.SpringStudy.datasource

import panda.tutorials.springboot.SpringStudy.model.Bank

interface BankDataSource {

    fun retrieveBanks() : Collection<Bank>

    fun retrieveBank(accountNumber: String): Bank

    fun createBank(bank: Bank): Bank

    fun updateBank(bank: Bank): Bank

    fun deleteBank(accountNumber: String)
}